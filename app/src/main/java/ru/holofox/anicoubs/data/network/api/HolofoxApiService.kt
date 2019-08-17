package ru.holofox.anicoubs.data.network.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred

import okhttp3.Interceptor
import okhttp3.OkHttpClient

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

import ru.holofox.anicoubs.data.network.ConnectivityInterceptor
import ru.holofox.anicoubs.data.network.response.HolofoxCheckChannelResponse

interface HolofoxApiService {

    @GET(value = "channels/id/{channel_id}")
    fun checkChannelAsync(
        @Path("channel_id") channelId: Int
    ): Deferred<HolofoxCheckChannelResponse>

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): HolofoxApiService {
            val requestInterceptor = Interceptor { chain ->

                val request = chain.request()
                    .newBuilder()
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://holofox.ru/api/v1/method/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(HolofoxApiService::class.java)
        }
    }
}