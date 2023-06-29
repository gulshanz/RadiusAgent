package taksande.gulshan.radiusagent.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import taksande.gulshan.radiusagent.data.api.ApiService
import taksande.gulshan.radiusagent.data.repository.FacilityRepository

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideRetrofitInstance(): ApiService {
        return Retrofit.Builder().baseUrl("https://my-json-server.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ApiService::class.java)
    }


    @Provides
    fun provideRepository(apiService: ApiService): FacilityRepository {
        return FacilityRepository(apiService)
    }


}