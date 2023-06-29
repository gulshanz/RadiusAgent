package taksande.gulshan.radiusagent.data.repository

import taksande.gulshan.radiusagent.data.api.ApiService
import taksande.gulshan.radiusagent.data.model.ApiResponse
import javax.inject.Inject

class FacilityRepository @Inject constructor(
    private val apiService: ApiService,
) {

    suspend fun fetchData(): ApiResponse? {
        val response = apiService.getApiData()
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }


}