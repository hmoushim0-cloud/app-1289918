package sa.factory.app1289918

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import sa.factory.app1289918.domain.sync.*
import sa.factory.app1289918.hardware.storage.*

/**
 * Unit Tests for Google Drive Differential Sync & External Storage Driver
 * Strictly Zero Emojis per factory invariant.
 */
class DriveBackupSyncFeatureTest {

    private lateinit var syncEngine: DriveDeltaSyncEngine
    private lateinit var storageDriver: ExternalStorageDriveDriver

    @Before
    fun setUp() {
        syncEngine = DriveDeltaSyncEngine()
        storageDriver = ExternalStorageDriveDriver()
    }

    @Test
    fun testChecksumCalculationAdler32AndCrc32() {
        val sampleBytes = "Saudi Vision 2030 Data Backup Stream".toByteArray(Charsets.UTF_8)

        val crcHex = syncEngine.computeCrc32Hex(sampleBytes)
        assertNotNull(crcHex)
        assertEquals("CRC32 hex string must be exactly 8 characters", 8, crcHex.length)

        val adlerHex = syncEngine.computeAdler32Hex(sampleBytes)
        assertNotNull(adlerHex)
        assertEquals("Adler32 hex string must be exactly 8 characters", 8, adlerHex.length)

        // Ensure distinct content generates different checksum
        val modifiedBytes = "Saudi Vision 2030 Data Backup Stream 2".toByteArray(Charsets.UTF_8)
        val modifiedCrc = syncEngine.computeCrc32Hex(modifiedBytes)
        assertNotEquals("Different content must produce different CRC32", crcHex, modifiedCrc)
    }

    @Test
    fun testMultipartChunkPartitioning() {
        val tenMbFileSizeBytes = 10L * 1024L * 1024L // 10 MiB
        val fourMbChunkSize = 4L * 1024L * 1024L // 4 MiB

        val slices = syncEngine.partitionMultipartChunks(tenMbFileSizeBytes, fourMbChunkSize)

        // 10 MB / 4 MB = 2 full chunks (4MB each) + 1 partial chunk (2MB) = 3 chunks
        assertEquals("10MB partitioned by 4MB must produce 3 chunks", 3, slices.size)

        // Chunk 0: 0 to (4MB - 1)
        assertEquals(0, slices[0].chunkIndex)
        assertEquals(0L, slices[0].startByte)
        assertEquals(fourMbChunkSize - 1L, slices[0].endByte)
        assertEquals(fourMbChunkSize, slices[0].chunkSizeBytes)
        assertFalse(slices[0].isFinalChunk)

        // Chunk 1: 4MB to (8MB - 1)
        assertEquals(1, slices[1].chunkIndex)
        assertEquals(fourMbChunkSize, slices[1].startByte)
        assertEquals((fourMbChunkSize * 2L) - 1L, slices[1].endByte)
        assertEquals(fourMbChunkSize, slices[1].chunkSizeBytes)
        assertFalse(slices[1].isFinalChunk)

        // Chunk 2: 8MB to (10MB - 1)
        assertEquals(2, slices[2].chunkIndex)
        assertEquals(fourMbChunkSize * 2L, slices[2].startByte)
        assertEquals(tenMbFileSizeBytes - 1L, slices[2].endByte)
        assertEquals(2L * 1024L * 1024L, slices[2].chunkSizeBytes)
        assertTrue(slices[2].isFinalChunk)
    }

    @Test
    fun testDeltaReconciliationPlanActions() {
        val now = System.currentTimeMillis()

        val remoteFiles = listOf(
            RemoteFileMetadata("docs/contract.pdf", 1024L, now - 10000L, "CRC001"),
            RemoteFileMetadata("photos/header.png", 2048L, now - 5000L, "CRC002"),
            RemoteFileMetadata("archive/db_dump.sql", 8192L, now - 1000L, "CRC003"),
            RemoteFileMetadata("notes/draft.txt", 512L, now - 20000L, "CRC004")
        )

        val localFiles = listOf(
            // Identical
            LocalFileMetadata("docs/contract.pdf", 1024L, now - 10000L, "CRC001"),
            // Modified remotely (local has older timestamp and different CRC)
            LocalFileMetadata("photos/header.png", 1800L, now - 15000L, "CRC002_OLD"),
            // Modified locally after remote timestamp (conflict)
            LocalFileMetadata("notes/draft.txt", 600L, now - 2000L, "CRC004_MOD"),
            // Extra local file not in cloud
            LocalFileMetadata("temp/cache.tmp", 4096L, now - 5000L, "CRCTMP")
        )

        // Test with mirror mode enabled
        val plan = syncEngine.buildReconciliationPlan(
            remoteFiles = remoteFiles,
            localFiles = localFiles,
            isTargetFat32 = false,
            mirrorDeleteUnmatchedLocal = true
        )

        assertEquals("Total actions count", 5, plan.actions.size)

        val newFileAction = plan.actions.firstOrNull { it.relativePath == "archive/db_dump.sql" }
        assertNotNull(newFileAction)
        assertEquals(SyncActionType.DOWNLOAD_NEW, newFileAction?.actionType)
        assertEquals(8192L, newFileAction?.bytesToTransfer)

        val identicalAction = plan.actions.firstOrNull { it.relativePath == "docs/contract.pdf" }
        assertNotNull(identicalAction)
        assertEquals(SyncActionType.SKIP_IDENTICAL, identicalAction?.actionType)
        assertEquals(0L, identicalAction?.bytesToTransfer)

        val modifiedAction = plan.actions.firstOrNull { it.relativePath == "photos/header.png" }
        assertNotNull(modifiedAction)
        assertEquals(SyncActionType.UPDATE_MODIFIED, modifiedAction?.actionType)
        assertEquals(2048L, modifiedAction?.bytesToTransfer)

        val conflictAction = plan.actions.firstOrNull { it.relativePath == "notes/draft.txt" }
        assertNotNull(conflictAction)
        assertEquals(SyncActionType.CONFLICT_LOCAL_NEWER, conflictAction?.actionType)
        assertTrue(plan.hasConflicts)

        val deleteAction = plan.actions.firstOrNull { it.relativePath == "temp/cache.tmp" }
        assertNotNull(deleteAction)
        assertEquals(SyncActionType.DELETE_LOCAL, deleteAction?.actionType)
        assertEquals(4096L, plan.totalBytesToDelete)
    }

    @Test
    fun testFat32FourGigabyteFileLimitGuard() {
        val hugeFileSize = 4831838208L // 4.5 GiB
        val remoteFiles = listOf(
            RemoteFileMetadata("iso/system_image.iso", hugeFileSize, System.currentTimeMillis(), "CRC_HUGE")
        )

        // 1. Target FAT32 filesystem
        val fat32Plan = syncEngine.buildReconciliationPlan(
            remoteFiles = remoteFiles,
            localFiles = emptyList(),
            isTargetFat32 = true
        )

        assertEquals(1, fat32Plan.actions.size)
        assertEquals(SyncActionType.ERROR_FILESYSTEM_SIZE_EXCEEDED, fat32Plan.actions[0].actionType)
        assertEquals(0L, fat32Plan.totalBytesToDownload)

        // 2. Target exFAT filesystem (supports files > 4GiB)
        val exFatPlan = syncEngine.buildReconciliationPlan(
            remoteFiles = remoteFiles,
            localFiles = emptyList(),
            isTargetFat32 = false
        )

        assertEquals(1, exFatPlan.actions.size)
        assertEquals(SyncActionType.DOWNLOAD_NEW, exFatPlan.actions[0].actionType)
        assertEquals(hugeFileSize, exFatPlan.totalBytesToDownload)
    }

    @Test
    fun testTransferThroughputAndEtaCalculation() {
        // Transferred 200MB in 2000ms = 100 MB/s
        val twoHundredMb = 200L * 1024L * 1024L
        val oneGbTotal = 1024L * 1024L * 1024L

        val (speedMb, etaSec) = syncEngine.calculateTransferMetrics(
            bytesTransferred = twoHundredMb,
            totalBytes = oneGbTotal,
            elapsedMillis = 2000L
        )

        assertEquals("Throughput should be ~100 MB/s", 100.0, speedMb, 0.5)
        // Remaining 824 MB @ 100 MB/s = ~8.24s -> 8s
        assertEquals("ETA should be ~8 seconds", 8L, etaSec)
    }

    @Test
    fun testExternalDriveCapacityHeadroomAndFormatting() {
        val formatOneKb = storageDriver.formatBytes(1024L)
        assertEquals("1.00 KB", formatOneKb)

        val formatOneGb = storageDriver.formatBytes(1073741824L)
        assertEquals("1.00 GB", formatOneGb)

        val drive = ExternalDriveDescriptor(
            driveId = "USB-001",
            vendorModel = "SanDisk Extreme 1TB",
            busInterface = StorageBusInterface.USB_3_2_GEN2_OTG,
            filesystem = FilesystemFormat.EXFAT,
            mountPath = "/mnt/media_rw/USB1",
            totalCapacityBytes = 1000000000000L,
            freeSpaceBytes = 2000000000L, // 2 GB free
            mountState = DriveMountState.MOUNTED_RW,
            smartMetrics = DiskHealthSmartMetrics(35, 95, 200L, 0, 0, true)
        )

        // 1. Download requirement 1.8 GB with 500MB reserve buffer -> 2GB - 1.8GB = 0.2GB < 0.5GB buffer
        val canFitHuge = storageDriver.evaluateCapacityHeadroom(
            descriptor = drive,
            requiredDownloadBytes = 1800000000L,
            reserveBufferBytes = 500000000L
        )
        assertFalse("Insufficient headroom under safety reserve must return false", canFitHuge)

        // 2. Download requirement 500 MB with 500MB reserve buffer -> 2GB - 0.5GB = 1.5GB >= 0.5GB buffer
        val canFitSmall = storageDriver.evaluateCapacityHeadroom(
            descriptor = drive,
            requiredDownloadBytes = 500000000L,
            reserveBufferBytes = 500000000L
        )
        assertTrue("Sufficient headroom must return true", canFitSmall)
    }

    @Test
    fun testStorageHardwareTelemetryFrameSerializationAndVerification() {
        val drive = ExternalDriveDescriptor(
            driveId = "NVME-002",
            vendorModel = "Crucial X9 Pro 4TB",
            busInterface = StorageBusInterface.NVME_PCIE,
            filesystem = FilesystemFormat.EXFAT,
            mountPath = "/mnt/media_rw/NVME",
            totalCapacityBytes = 4000000000000L,
            freeSpaceBytes = 3000000000000L,
            mountState = DriveMountState.MOUNTED_RW,
            smartMetrics = DiskHealthSmartMetrics(
                temperatureCelsius = 42,
                wearLevelingPercent = 99,
                powerOnHours = 120L,
                unsafeShutdowns = 0,
                mediaErrors = 0,
                isHealthy = true
            )
        )

        val frame = storageDriver.serializeTelemetryFrame(drive)
        assertEquals(8, frame.size)
        assertEquals(0x53.toByte(), frame[0]) // 'S' sync header

        val isValid = storageDriver.verifyTelemetryFrame(frame)
        assertTrue("Valid serialized telemetry frame must pass checksum verification", isValid)

        // Corrupt frame byte
        val corruptedFrame = frame.clone()
        corruptedFrame[4] = (corruptedFrame[4] + 1).toByte()
        assertFalse("Corrupted frame must fail checksum verification", storageDriver.verifyTelemetryFrame(corruptedFrame))

        // Diagnostic safety test
        val (isSafe, _) = storageDriver.diagnoseDriveSafety(drive)
        assertTrue("Healthy drive must pass safety diagnostics", isSafe)

        // Overheated drive diagnostic test (temperature >= 68 C)
        val hotDrive = drive.copy(smartMetrics = drive.smartMetrics.copy(temperatureCelsius = 72))
        val (isHotSafe, warningReport) = storageDriver.diagnoseDriveSafety(hotDrive)
        assertFalse("Overheated drive must fail safety diagnostics", isHotSafe)
        assertTrue(warningReport.contains("Thermal throttling warning"))
    }
}
