package ru.holofox.anicoubs.data.repository

import ru.holofox.anicoubs.data.network.NetworkCall
import ru.holofox.anicoubs.data.network.NetworkException
import ru.holofox.anicoubs.data.network.await
import ru.holofox.anicoubs.data.network.data.HolofoxNetworkDataSource
import ru.holofox.anicoubs.internal.NoConnectivityException

class HolofoxRepositoryImpl(
    private val holofoxNetworkDataSource: HolofoxNetworkDataSource
) : HolofoxRepository {

    override suspend fun checkInBlackList(channelId: Int): NetworkCall<Boolean> {
        val response = NetworkCall<Boolean>()

        try {
            val result = holofoxNetworkDataSource
                .checkInBlackList(channelId)
                .await()
            response.onSuccess(result)
        } catch (error: NoConnectivityException) {
            response.onError(NetworkException(error.message.toString()))
        }

        return response
    }

}