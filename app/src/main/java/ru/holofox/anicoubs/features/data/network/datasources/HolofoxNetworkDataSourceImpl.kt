package ru.holofox.anicoubs.features.data.network.datasources

import android.util.Log
import ru.holofox.anicoubs.features.data.network.NetworkCall
import ru.holofox.anicoubs.features.data.network.NetworkException
import ru.holofox.anicoubs.features.domain.network.api.HolofoxApiService
import ru.holofox.anicoubs.features.domain.network.data.HolofoxNetworkDataSource

class HolofoxNetworkDataSourceImpl(
    private val holofoxApiService: HolofoxApiService
) : HolofoxNetworkDataSource {

    override suspend fun checkInBlackList(channelId: Int): NetworkCall<Boolean> {
        val response = NetworkCall<Boolean>()

        try {
            val result = holofoxApiService
                .checkChannelAsync(channelId)
                .await()
            response.onSuccess(result.response)
        } catch (error: NetworkException) {
            Log.e("Connectivity", error.message, error)
            response.onError(NetworkException(error.message.toString()))
        }

        return response
    }

}