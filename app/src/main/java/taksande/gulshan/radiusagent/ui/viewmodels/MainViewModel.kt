package taksande.gulshan.radiusagent.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import taksande.gulshan.radiusagent.data.model.ApiResponse
import taksande.gulshan.radiusagent.data.model.Facility
import taksande.gulshan.radiusagent.data.repository.FacilityRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: FacilityRepository) : ViewModel() {
    private val _apiResponse = MutableLiveData<ApiResponse>()
    val apiResponse get() = _apiResponse

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> get() = _isLoading


    fun fetchData() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val data = repository.fetchData()
                _isLoading.value = false
                _apiResponse.value = data!!
            } catch (e: Exception) {
                _isLoading.value = false
                _error.value = e.localizedMessage
            }
        }
    }
}