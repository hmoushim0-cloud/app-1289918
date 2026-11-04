package sa.factory.app1289918.domain.sync

import java.io.Serializable
import java.util.Locale
import java.util.zip.Adler32
import java.util.zip.CRC32

/**
 * Differential Delta Synchronization & Rolling Hash Engine
 * Implements cloud-to-local mirror and sync reconciliation algorithms.
 * Strictly Zero Emojis per factory invariant.
 */

enum class ChecksumType {
    ADLER32,
    CRC32,
    SHA256_HEX,
    MD5_HEX
}

enum class SyncDirection {
    PULL_FROM_CLOUD,
    PUSH_TO_CLOUD,
    BI_DIRECTIONAL
}

enum class SyncActionType {
    DOWNLOAD_NEW,
    UPDATE_MODIFIED,
    DELETE_LOCAL,
    SKIP_IDENTICAL,
    CONFLICT_LOCAL_NEWER,
    ERROR_FILESYSTEM_SIZE_EXCEEDED
}

data class RemoteFileMetadata(
    val relativePath: String,
    val sizeBytes: Long,
    val lastModifiedEpochMs: Long,
    val checksum: String,
    val checksumType: ChecksumType = ChecksumType.CRC32
) : Serializable

data class LocalFileMetadata(
    val relativePath: String,
    val sizeBytes: Long,
    val lastModifiedEpochMs: Long,
    val checksum: String,
    val checksumType: ChecksumType = ChecksumType.CRC32,
    val isAccessible: Boolean = true
) : Serializable

data class SyncActionItem(
    val relativePath: String,
    val actionType: SyncActionType,
    val bytesToTransfer: Long,
    val sourceModifiedMs: Long,
    val targetModifiedMs: Long,
    val reasonExplanation: String
) : Serializable

data class SyncReconciliationPlan(
    val totalFilesEvaluated: Int,
    val actions: List<SyncActionItem>,
    val totalBytesToDownload: Long,
    val totalBytesToDelete: Long,
    val totalFilesSkipped: Int,
    val hasConflicts: Boolean,
    val executionSummary: String
) : Serializable

data class ChunkTransferSlice(
    val chunkIndex: Int,
    val startByte: Long,
    val endByte: Long,
    val chunkSizeBytes: Long,
    val isFinalChunk: Boolean
) : Serializable

class DriveDeltaSyncEngine {

    companion object {
        const val FAT32_MAX_FILE_SIZE_BYTES = 4294967295L // 4 GiB - 1 byte
        const val DEFAULT_MULTIPART_CHUNK_SIZE = 4194304L // 4 MiB
    }

    /**
     * Computes CRC32 checksum for raw byte buffers.
     */
    fun computeCrc32Hex(data: ByteArray): String {
        val crc = CRC32()
        crc.update(data)
        return String.format(Locale.US, "%08X", crc.value)
    }

    /**
     * Computes Adler-32 rolling checksum for high-speed block verification.
     */
    fun computeAdler32Hex(data: ByteArray): String {
        val adler = Adler32()
        adler.update(data)
        return String.format(Locale.US, "%08X", adler.value)
    }

    /**
     * Splits a large file transfer into multipart byte slices for resilient resumption.
     */
    fun partitionMultipartChunks(
        fileSizeBytes: Long,
        chunkSizeBytes: Long = DEFAULT_MULTIPART_CHUNK_SIZE
    ): List<ChunkTransferSlice> {
        if (fileSizeBytes <= 0L) {
            return listOf(ChunkTransferSlice(0, 0L, 0L, 0L, true))
        }

        val slices = mutableListOf<ChunkTransferSlice>()
        var offset = 0L
        var chunkIndex = 0

        while (offset < fileSizeBytes) {
            val remaining = fileSizeBytes - offset
            val currentChunkSize = Math.min(chunkSizeBytes, remaining)
            val endByte = offset + currentChunkSize - 1
            val isFinal = (offset + currentChunkSize) >= fileSizeBytes

            slices.add(
                ChunkTransferSlice(
                    chunkIndex = chunkIndex,
                    startByte = offset,
                    endByte = endByte,
                    chunkSizeBytes = currentChunkSize,
                    isFinalChunk = isFinal
                )
            )

            offset += currentChunkSize
            chunkIndex++
        }

        return slices
    }

    /**
     * Reconciles remote Google Drive manifests against local external storage manifests.
     * Evaluates size, modification timestamps, and checksums to build an atomic execution plan.
     */
    fun buildReconciliationPlan(
        remoteFiles: List<RemoteFileMetadata>,
        localFiles: List<LocalFileMetadata>,
        isTargetFat32: Boolean = false,
        mirrorDeleteUnmatchedLocal: Boolean = false
    ): SyncReconciliationPlan {
        val localMap = localFiles.associateBy { it.relativePath }
        val remoteMap = remoteFiles.associateBy { it.relativePath }

        val actions = mutableListOf<SyncActionItem>()
        var totalDownloadBytes = 0L
        var totalDeleteBytes = 0L
        var skippedCount = 0
        var conflictFlag = false

        // 1. Process all remote files against local targets
        for (remote in remoteFiles) {
            val local = localMap[remote.relativePath]

            if (isTargetFat32 && remote.sizeBytes > FAT32_MAX_FILE_SIZE_BYTES) {
                actions.add(
                    SyncActionItem(
                        relativePath = remote.relativePath,
                        actionType = SyncActionType.ERROR_FILESYSTEM_SIZE_EXCEEDED,
                        bytesToTransfer = 0L,
                        sourceModifiedMs = remote.lastModifiedEpochMs,
                        targetModifiedMs = local?.lastModifiedEpochMs ?: 0L,
                        reasonExplanation = "File exceeds 4GiB limit of FAT32 filesystem"
                    )
                )
                continue
            }

            if (local == null) {
                // New file to download
                actions.add(
                    SyncActionItem(
                        relativePath = remote.relativePath,
                        actionType = SyncActionType.DOWNLOAD_NEW,
                        bytesToTransfer = remote.sizeBytes,
                        sourceModifiedMs = remote.lastModifiedEpochMs,
                        targetModifiedMs = 0L,
                        reasonExplanation = "New remote file missing in external drive"
                    )
                )
                totalDownloadBytes += remote.sizeBytes
            } else {
                // File exists both remotely and locally: compare size, hash, and timestamp
                val sizeMatches = remote.sizeBytes == local.sizeBytes
                val checksumMatches = remote.checksum.equals(local.checksum, ignoreCase = true)

                if (sizeMatches && checksumMatches) {
                    actions.add(
                        SyncActionItem(
                            relativePath = remote.relativePath,
                            actionType = SyncActionType.SKIP_IDENTICAL,
                            bytesToTransfer = 0L,
                            sourceModifiedMs = remote.lastModifiedEpochMs,
                            targetModifiedMs = local.lastModifiedEpochMs,
                            reasonExplanation = "Identical size and checksum; up to date"
                        )
                    )
                    skippedCount++
                } else if (local.lastModifiedEpochMs > remote.lastModifiedEpochMs + 2000L) {
                    // Local file is newer by more than 2 seconds (safe margin for filesystem granularity)
                    actions.add(
                        SyncActionItem(
                            relativePath = remote.relativePath,
                            actionType = SyncActionType.CONFLICT_LOCAL_NEWER,
                            bytesToTransfer = remote.sizeBytes,
                            sourceModifiedMs = remote.lastModifiedEpochMs,
                            targetModifiedMs = local.lastModifiedEpochMs,
                            reasonExplanation = "Conflict: Local version was modified after remote cloud file"
                        )
                    )
                    conflictFlag = true
                } else {
                    // Remote file is newer or modified
                    actions.add(
                        SyncActionItem(
                            relativePath = remote.relativePath,
                            actionType = SyncActionType.UPDATE_MODIFIED,
                            bytesToTransfer = remote.sizeBytes,
                            sourceModifiedMs = remote.lastModifiedEpochMs,
                            targetModifiedMs = local.lastModifiedEpochMs,
                            reasonExplanation = "Remote file has updated content/hash"
                        )
                    )
                    totalDownloadBytes += remote.sizeBytes
                }
            }
        }

        // 2. In mirror mode, delete local orphan files that do not exist in remote Drive
        if (mirrorDeleteUnmatchedLocal) {
            for (local in localFiles) {
                if (!remoteMap.containsKey(local.relativePath)) {
                    actions.add(
                        SyncActionItem(
                            relativePath = local.relativePath,
                            actionType = SyncActionType.DELETE_LOCAL,
                            bytesToTransfer = 0L,
                            sourceModifiedMs = 0L,
                            targetModifiedMs = local.lastModifiedEpochMs,
                            reasonExplanation = "Mirror mode: File deleted from cloud Drive; queuing local deletion"
                        )
                    )
                    totalDeleteBytes += local.sizeBytes
                }
            }
        }

        val totalEvaluated = remoteFiles.size + (if (mirrorDeleteUnmatchedLocal) localFiles.size else 0)
        val summary = String.format(
            Locale.US,
            "Reconciliation Plan: %d actions | Download: %.2f MB | Deletions: %d files (%.2f MB) | Skipped: %d | Conflicts: %s",
            actions.size,
            totalDownloadBytes.toDouble() / (1024.0 * 1024.0),
            actions.count { it.actionType == SyncActionType.DELETE_LOCAL },
            totalDeleteBytes.toDouble() / (1024.0 * 1024.0),
            skippedCount,
            if (conflictFlag) "DETECTED" else "NONE"
        )

        return SyncReconciliationPlan(
            totalFilesEvaluated = totalEvaluated,
            actions = actions,
            totalBytesToDownload = totalDownloadBytes,
            totalBytesToDelete = totalDeleteBytes,
            totalFilesSkipped = skippedCount,
            hasConflicts = conflictFlag,
            executionSummary = summary
        )
    }

    /**
     * Calculates transfer throughput and estimated time remaining.
     */
    fun calculateTransferMetrics(
        bytesTransferred: Long,
        totalBytes: Long,
        elapsedMillis: Long
    ): Pair<Double, Long> {
        if (elapsedMillis <= 0L || bytesTransferred <= 0L) {
            return Pair(0.0, 0L)
        }

        val speedBytesPerSec = (bytesTransferred.toDouble() / elapsedMillis.toDouble()) * 1000.0
        val speedMbPerSec = speedBytesPerSec / (1024.0 * 1024.0)

        val remainingBytes = Math.max(0L, totalBytes - bytesTransferred)
        val etaSeconds = if (speedBytesPerSec > 0.0) {
            Math.round(remainingBytes / speedBytesPerSec)
        } else {
            0L
        }

        return Pair(Math.round(speedMbPerSec * 100.0) / 100.0, etaSeconds)
    }
}
