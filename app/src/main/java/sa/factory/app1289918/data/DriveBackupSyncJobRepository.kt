package sa.factory.app1289918.data

import sa.factory.app1289918.model.DriveBackupSyncJob
import sa.factory.app1289918.model.DriveBackupSyncTask
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

interface DriveBackupSyncJobRepository {
    fun getAvailableCatalog(): Flow<List<DriveBackupSyncTask>>
    fun getActiveRequest(): Flow<DriveBackupSyncJob?>
    suspend fun submitRequest(request: DriveBackupSyncJob): Result<String>
}

class InMemoryDriveBackupSyncJobRepository : DriveBackupSyncJobRepository {
    private val _catalog = MutableStateFlow<List<DriveBackupSyncTask>>(listOf(
        DriveBackupSyncTask("1", "تهيئة rclone ومسار مزامنة موحد", "إعداد مستودعات الاتصال وتهيئة إعدادات rclone لرفع/تنزيل الملفات مع ضبط قواعد الاستباق والتجاهل بحسب احتياجات العميل.", 5000L, "verified"),
        DriveBackupSyncTask("2", "بناء نظام Automated Backup Sync على الهارد الخارجي", "تصميم تدفق مزامنة تلقائي مع استئناف عند الانقطاع، ومحاكاة ما قبل التنفيذ (dry-run) لتقليل المفاجآت وضمان الاستمرارية.", 9500L, "bolt")
    ))
    private val _active = MutableStateFlow<DriveBackupSyncJob?>(null)

    override fun getAvailableCatalog(): Flow<List<DriveBackupSyncTask>> = _catalog.asStateFlow()
    override fun getActiveRequest(): Flow<DriveBackupSyncJob?> = _active.asStateFlow()

    override suspend fun submitRequest(request: DriveBackupSyncJob): Result<String> {
        _active.value = request
        return Result.success(request.requestId)
    }
}
