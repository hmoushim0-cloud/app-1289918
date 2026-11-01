package sa.factory.app1289918.model

import kotlinx.serialization.Serializable

@Serializable
data class DriveBackupSyncTask(
    val id: String,
    val title: String,
    val description: String,
    val priceMinorUnits: Long,
    val iconVector: String = "verified"
)

@Serializable
data class DriveBackupSyncJob(
    val requestId: String,
    val items: List<DriveBackupSyncTask>,
    val customerPhone: String,
    val totalAmountMinorUnits: Long,
    val status: DriveBackupSyncJobStatus = DriveBackupSyncJobStatus.PENDING,
    val createdAtEpochMs: Long = System.currentTimeMillis()
)

enum class DriveBackupSyncJobStatus {
    PENDING,
    ACCEPTED,
    IN_PROGRESS,
    COMPLETED,
    CANCELLED
}
