package ru.holofox.anicoubs.data.network.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred

import okhttp3.Interceptor
import okhttp3.OkHttpClient

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

import ru.holofox.anicoubs.data.db.entity.coub.CoubEntry
import ru.holofox.anicoubs.data.network.response.coub.CoubChannelResponse
import ru.holofox.anicoubs.data.network.ConnectivityInterceptor
import ru.holofox.anicoubs.data.network.response.coub.CoubTimelineResponse

// https://coub.com/api/v2/timeline/tag/anime?page=1&per_page=10?order_by=oldest
// https://coub.com/api/v2/timeline/explore/anime?page=1&per_page=10

interface CoubApiService {

    @GET(value = "timeline/explore/anime")
    fun getTimelineAsync(
        @Query(value = "page") page: Int,
        @Query(value = "per_page") per_page: Int = 10
    ): Deferred<CoubTimelineResponse>

    @GET(value = "coubs/{permalink}")
    fun getCoubAsync(
        @Path("permalink") permalink: String
    ): Deferred<CoubEntry>

    @GET(value = "channels/{id}")
    fun getChannelAsync(
        @Path("id") id: String
    ): Deferred<CoubChannelResponse>

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): CoubApiService {
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
                .baseUrl("https://coub.com/api/v2/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CoubApiService::class.java)
        }
    }

}