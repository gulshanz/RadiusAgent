package taksande.gulshan.radiusagent.data.api

import retrofit2.Response
import retrofit2.http.GET
import taksande.gulshan.radiusagent.data.model.ApiResponse

interface ApiService {

    @GET("iranjith4/ad-assignment/db")
    suspend fun getApiData(): Response<ApiResponse>
}