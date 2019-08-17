package ru.holofox.anicoubs.data.network.data

import android.util.Log
import ru.holofox.anicoubs.data.network.response.coub.CoubChannelResponse
import ru.holofox.anicoubs.data.db.entity.coub.CoubEntry
import ru.holofox.anicoubs.data.network.NetworkCall
import ru.holofox.anicoubs.data.network.NetworkException
import ru.holofox.anicoubs.data.network.api.CoubApiService
import ru.holofox.anicoubs.data.network.response.coub.CoubTimelineResponse
import ru.holofox.anicoubs.internal.NoConnectivityException

class CoubNetworkDataSourceImpl(
    private val coubApiService: CoubApiService
) : CoubNetworkDataSource {

    override suspend fun fetchFeed(page: Int, per_page: Int) : NetworkCall<CoubTimelineResponse> {
        val response = NetworkCall<CoubTimelineResponse>()

        try {
            val result = coubApiService
                .getTimelineAsync(page, per_page)
                .await()
            response.onSuccess(result)
        } catch (error: NoConnectivityException) {
            Log.e("Connectivity", error.message, error)
            response.onError(NetworkException(error.message.toString()))
        }

        return response
    }

    override suspend fun fetchCoub(permalink: String): NetworkCall<CoubEntry> {
        val response = NetworkCall<CoubEntry>()

        try {
            val result = coubApiService
                .getCoubAsync(permalink)
                .await()
            response.onSuccess(result)
        } catch (error: NoConnectivityException) {
            Log.e("Connectivity", error.message, error)
            response.onError(NetworkException(error.message.toString()))
        }

        return response
    }

    override suspend fun fetchChannel(id: Int): NetworkCall<CoubChannelResponse> {
        val response = NetworkCall<CoubChannelResponse>()

        try {
            val result = coubApiService
                .getChannelAsync(id)
                .await()
            response.onSuccess(result)
        } catch (error: NoConnectivityException) {
            Log.e("Connectivity", error.message, error)
            response.onError(NetworkException(error.message.toString()))
        }

        return response
    }

}