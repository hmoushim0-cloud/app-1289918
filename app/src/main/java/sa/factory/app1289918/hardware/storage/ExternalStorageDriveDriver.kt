package sa.factory.app1289918.hardware.storage

import java.io.Serializable
import java.util.Locale

/**
 * External Hardware Storage (OTG / USB 3.2 / NVMe / UASP) Driver & S.M.A.R.T. Telemetry
 * Strictly Zero Emojis per factory invariant.
 */

enum class StorageBusInterface(val label: String, val maxBandwidthGbps: Double) {
    USB_3_2_GEN2_OTG("USB 3.2 Gen 2 OTG", 10.0),
    NVME_PCIE("NVMe PCIe Gen 4", 32.0),
    UASP_SATA_SSD("UASP SATA III SSD", 6.0),
    THUNDERBOLT_4("Thunderbolt 4 / USB4", 40.0),
    SD_CARD_UHS_II("SD Card UHS-II", 2.5)
}

enum class FilesystemFormat(val fsName: String, val supportsFilesOver4Gb: Boolean) {
    EXFAT("exFAT", true),
    EXT4("ext4", true),
    NTFS("NTFS", true),
    FAT32("FAT32", false),
    APFS("APFS", true),
    UNKNOWN("Unknown / Raw", false)
}

enum class DriveMountState {
    MOUNTED_RW,
    MOUNTED_RO,
    UNMOUNTED,
    BUSY_LOCKED,
    IO_ERROR
}

data class DiskHealthSmartMetrics(
    val temperatureCelsius: Int,
    val wearLevelingPercent: Int, // 100% is new, lower means worn
    val powerOnHours: Long,
    val unsafeShutdowns: Int,
    val mediaErrors: Int,
    val isHealthy: Boolean
) : Serializable

data class ExternalDriveDescriptor(
    val driveId: String,
    val vendorModel: String,
    val busInterface: StorageBusInterface,
    val filesystem: FilesystemFormat,
    val mountPath: String,
    val totalCapacityBytes: Long,
    val freeSpaceBytes: Long,
    val mountState: DriveMountState,
    val smartMetrics: DiskHealthSmartMetrics
) : Serializable {
    val usedSpaceBytes: Long
        get() = Math.max(0L, totalCapacityBytes - freeSpaceBytes)

    val usagePercentage: Double
        get() = if (totalCapacityBytes > 0L) {
            Math.round((usedSpaceBytes.toDouble() / totalCapacityBytes.toDouble()) * 1000.0) / 10.0
        } else {
            0.0
        }
}

class ExternalStorageDriveDriver {

    companion object {
        const val DEFAULT_SAFETY_RESERVE_BYTES = 524288000L // 500 MiB safety reserve
        const val CRITICAL_TEMPERATURE_CELSIUS = 68
        const val HARDWARE_DRIVER_FRAME_HEADER = 0x53 // 'S' for Storage
    }

    /**
     * Formats raw bytes into standard human-readable units (KB, MB, GB, TB).
     */
    fun formatBytes(bytes: Long): String {
        if (bytes < 1024L) return "$bytes B"
        val exp = (Math.log(bytes.toDouble()) / Math.log(1024.0)).toInt()
        val unit = "KMGTPE"[exp - 1] + "B"
        val value = bytes / Math.pow(1024.0, exp.toDouble())
        return String.format(Locale.US, "%.2f %s", value, unit)
    }

    /**
     * Validates whether target storage has sufficient headroom including safety reserves.
     */
    fun evaluateCapacityHeadroom(
        descriptor: ExternalDriveDescriptor,
        requiredDownloadBytes: Long,
        reserveBufferBytes: Long = DEFAULT_SAFETY_RESERVE_BYTES
    ): Boolean {
        if (descriptor.mountState != DriveMountState.MOUNTED_RW) return false
        val availableAfterTransfer = descriptor.freeSpaceBytes - requiredDownloadBytes
        return availableAfterTransfer >= reserveBufferBytes
    }

    /**
     * Conducts S.M.A.R.T. health and filesystem diagnostics before triggering multi-gigabyte sync.
     */
    fun diagnoseDriveSafety(descriptor: ExternalDriveDescriptor): Pair<Boolean, String> {
        val diagnostics = mutableListOf<String>()

        if (descriptor.mountState != DriveMountState.MOUNTED_RW) {
            return Pair(false, "Drive is not mounted read-write (Current state: ${descriptor.mountState})")
        }

        if (descriptor.smartMetrics.temperatureCelsius >= CRITICAL_TEMPERATURE_CELSIUS) {
            diagnostics.add(String.format(
                Locale.US,
                "Thermal throttling warning: Drive temperature is high (%d C >= %d C threshold)",
                descriptor.smartMetrics.temperatureCelsius,
                CRITICAL_TEMPERATURE_CELSIUS
            ))
        }

        if (descriptor.smartMetrics.mediaErrors > 0) {
            diagnostics.add("Drive reported ${descriptor.smartMetrics.mediaErrors} media I/O sectors/errors")
        }

        if (descriptor.smartMetrics.wearLevelingPercent < 15) {
            diagnostics.add("Critical wear alert: Flash memory endurance is at ${descriptor.smartMetrics.wearLevelingPercent}%")
        }

        if (descriptor.filesystem == FilesystemFormat.FAT32) {
            diagnostics.add("Notice: Filesystem is FAT32. Individual files over 4GiB will be rejected")
        }

        val isSafe = diagnostics.none { it.contains("Thermal throttling") || it.contains("sectors/errors") }
        val report = if (diagnostics.isEmpty()) {
            "Storage drive healthy and optimal for high-throughput sync"
        } else {
            diagnostics.joinToString(" | ")
        }

        return Pair(isSafe, report)
    }

    /**
     * Serializes an 8-byte binary hardware frame for OTG bus bridge telemetry:
     * Byte 0: 0x53 ('S' storage frame sync header)
     * Byte 1: Bus interface ordinal
     * Byte 2: Filesystem ordinal
     * Byte 3: Mount state ordinal
     * Byte 4: Temperature in Celsius (signed byte)
     * Byte 5: Wear leveling percentage (0..100)
     * Byte 6: Usage percentage integer (0..100)
     * Byte 7: Checksum (XOR bytes 0..6)
     */
    fun serializeTelemetryFrame(descriptor: ExternalDriveDescriptor): ByteArray {
        val frame = ByteArray(8)
        frame[0] = HARDWARE_DRIVER_FRAME_HEADER.toByte()
        frame[1] = descriptor.busInterface.ordinal.toByte()
        frame[2] = descriptor.filesystem.ordinal.toByte()
        frame[3] = descriptor.mountState.ordinal.toByte()
        frame[4] = descriptor.smartMetrics.temperatureCelsius.toByte()
        frame[5] = descriptor.smartMetrics.wearLevelingPercent.toByte()
        frame[6] = descriptor.usagePercentage.toInt().coerceIn(0, 100).toByte()

        var xorSum = 0
        for (i in 0 until 7) {
            xorSum = xorSum xor frame[i].toInt()
        }
        frame[7] = (xorSum and 0xFF).toByte()

        return frame
    }

    /**
     * Decodes and verifies the integrity of an 8-byte hardware storage telemetry frame.
     */
    fun verifyTelemetryFrame(frame: ByteArray): Boolean {
        if (frame.size != 8 || frame[0] != HARDWARE_DRIVER_FRAME_HEADER.toByte()) {
            return false
        }
        var xorSum = 0
        for (i in 0 until 7) {
            xorSum = xorSum xor frame[i].toInt()
        }
        val expectedChecksum = (xorSum and 0xFF).toByte()
        return frame[7] == expectedChecksum
    }
}
