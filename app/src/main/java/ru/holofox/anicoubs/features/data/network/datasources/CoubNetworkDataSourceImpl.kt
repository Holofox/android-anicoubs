package ru.holofox.anicoubs.features.data.network.datasources

import android.util.Log
import ru.holofox.anicoubs.features.data.db.entities.coub.CoubEntry
import ru.holofox.anicoubs.features.data.network.NetworkCall
import ru.holofox.anicoubs.features.data.network.NetworkException
import ru.holofox.anicoubs.features.data.network.api.coub.models.responses.CoubChannelResponse
import ru.holofox.anicoubs.features.data.network.api.coub.models.responses.CoubTimelineResponse
import ru.holofox.anicoubs.features.domain.network.api.CoubApiService
import ru.holofox.anicoubs.features.domain.network.data.CoubNetworkDataSource
import ru.holofox.anicoubs.internal.NoConnectivityException

class CoubNetworkDataSourceImpl(
    private val coubApiService: CoubApiService
) : CoubNetworkDataSource {

    override suspend fun fetchFeed(page: Int, per_page: Int): NetworkCall<CoubTimelineResponse> {
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