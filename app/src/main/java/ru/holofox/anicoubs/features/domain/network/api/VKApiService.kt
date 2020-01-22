package ru.holofox.anicoubs.features.domain.network.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url
import ru.holofox.anicoubs.features.domain.network.ConnectivityInterceptor
import ru.holofox.anicoubs.features.data.network.api.vk.models.responses.video.VKVideoUploadResponse

interface VKApiService {

    @GET
    fun uploadVideoByUrlAsync(
        @Url url: String
    ): Deferred<VKVideoUploadResponse>

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): VKApiService {

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://dev.vk.com")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(VKApiService::class.java)
        }
    }
}