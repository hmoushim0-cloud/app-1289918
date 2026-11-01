package sa.factory.app1289918.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import sa.factory.app1289918.data.DriveBackupSyncJobRepository
import sa.factory.app1289918.model.DriveBackupSyncJob
import sa.factory.app1289918.model.DriveBackupSyncTask
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DriveBackupSyncJobViewModel(
    private val repository: DriveBackupSyncJobRepository
) : ViewModel() {
    val catalog: StateFlow<List<DriveBackupSyncTask>> = repository.getAvailableCatalog()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val activeRequest: StateFlow<DriveBackupSyncJob?> = repository.getActiveRequest()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun submitNewOrder(request: DriveBackupSyncJob) {
        viewModelScope.launch {
            repository.submitRequest(request)
        }
    }
}
