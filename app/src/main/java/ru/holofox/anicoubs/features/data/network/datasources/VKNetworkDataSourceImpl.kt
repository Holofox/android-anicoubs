package ru.holofox.anicoubs.features.data.network.datasources

import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import com.vk.api.sdk.exceptions.VKApiExecutionException
import com.vk.api.sdk.internal.ApiCommand

import ru.holofox.anicoubs.features.data.network.NetworkCall
import ru.holofox.anicoubs.features.data.network.NetworkException
import ru.holofox.anicoubs.features.domain.network.data.VKNetworkDataSource
import ru.holofox.anicoubs.features.domain.providers.ConnectivityProvider

class VKNetworkDataSourceImpl(
    private val connectivityProvider: ConnectivityProvider
): VKNetworkDataSource {

    override suspend fun <T>perform(apiCommand: ApiCommand<T>) : NetworkCall<T> {
        val response = NetworkCall<T>()

        if (!connectivityProvider.isOnline())
            response.onError(NetworkException(connectivityProvider.getNetworkErrorMessage()))

        // Async call
        VK.execute(apiCommand, object : VKApiCallback<T> {
            override fun success(result: T) {
                response.onSuccess(result)
            }
            override fun fail(error: VKApiExecutionException) {
                response.onError(NetworkException(error.detailMessage))
            }
        })

        return response
    }

}