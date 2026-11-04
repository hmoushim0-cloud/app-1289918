package sa.factory.app1289918.hardware

import sa.factory.app1289918.domain.sync.*
import sa.factory.app1289918.hardware.storage.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Locale
import kotlin.random.Random

data class HardwareTelemetryFrame(
    val peripheralName: String,
    val connectionState: String,
    val livePayload: String,
    val timestamp: Long = System.currentTimeMillis()
)

class HardwareSensorDriver {
    private val peripheral = "External NVMe/USB-OTG Hardware Drive + Rclone Engine"
    private val syncEngine = DriveDeltaSyncEngine()
    private val storageDriver = ExternalStorageDriveDriver()

    fun getSyncEngine(): DriveDeltaSyncEngine = syncEngine
    fun getStorageDriver(): ExternalStorageDriveDriver = storageDriver

    fun observePeripheralStream(): Flow<HardwareTelemetryFrame> = flow {
        emit(HardwareTelemetryFrame(
            peripheral,
            "CONNECTING",
            "Storage Bus: Probing USB-C OTG 3.2 Gen 2 / NVMe PCIe Bridge..."
        ))
        delay(300)

        val sampleDrive = ExternalDriveDescriptor(
            driveId = "EXT-NVME-SAMSUNG-T7-01",
            vendorModel = "Samsung Portable SSD T7 Shield (2000 GB)",
            busInterface = StorageBusInterface.USB_3_2_GEN2_OTG,
            filesystem = FilesystemFormat.EXFAT,
            mountPath = "/mnt/media_rw/T7_SHIELD_EXFAT",
            totalCapacityBytes = 2000398934016L, // ~2.0 TB
            freeSpaceBytes = 1428581892096L, // ~1.42 TB
            mountState = DriveMountState.MOUNTED_RW,
            smartMetrics = DiskHealthSmartMetrics(
                temperatureCelsius = 38,
                wearLevelingPercent = 98,
                powerOnHours = 412L,
                unsafeShutdowns = 1,
                mediaErrors = 0,
                isHealthy = true
            )
        )

        val frame = storageDriver.serializeTelemetryFrame(sampleDrive)
        val frameHex = frame.joinToString("") { String.format("%02X", it) }

        emit(HardwareTelemetryFrame(
            peripheral,
            "ONLINE",
            String.format(
                Locale.US,
                "Drive Mounted: %s | Bus: %s | FS: %s | Free: %s / %s (%.1f%% Used) | S.M.A.R.T. %d C | Frame: [%s]",
                sampleDrive.vendorModel,
                sampleDrive.busInterface.label,
                sampleDrive.filesystem.fsName,
                storageDriver.formatBytes(sampleDrive.freeSpaceBytes),
                storageDriver.formatBytes(sampleDrive.totalCapacityBytes),
                sampleDrive.usagePercentage,
                sampleDrive.smartMetrics.temperatureCelsius,
                frameHex
            )
        ))

        var cycleCounter = 1
        var transferredBytes = 0L
        val totalSyncTargetBytes = 5242880000L // ~5.0 GB total batch

        while (true) {
            delay(1500)
            val jitterBytes = Random.nextLong(120000000L, 250000000L) // 120 - 250 MB per 1.5s (~100MB/s)
            transferredBytes = Math.min(totalSyncTargetBytes, transferredBytes + jitterBytes)

            val (speedMb, etaSec) = syncEngine.calculateTransferMetrics(
                bytesTransferred = transferredBytes,
                totalBytes = totalSyncTargetBytes,
                elapsedMillis = cycleCounter * 1500L
            )

            val tempJitter = Random.nextInt(37, 43)
            val payload = String.format(
                Locale.US,
                "Sync Cycle #%d: [%s / %s (%.1f%%)] | Throughput: %.2f MB/s | ETA: %ds | Target: %s | Temp: %d C",
                cycleCounter,
                storageDriver.formatBytes(transferredBytes),
                storageDriver.formatBytes(totalSyncTargetBytes),
                (transferredBytes.toDouble() / totalSyncTargetBytes.toDouble()) * 100.0,
                speedMb,
                etaSec,
                sampleDrive.mountPath,
                tempJitter
            )

            emit(HardwareTelemetryFrame(peripheral, "ACTIVE_STREAMING", payload))
            cycleCounter++
        }
    }
}
