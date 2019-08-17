package ru.holofox.anicoubs.data.network.data

import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import com.vk.api.sdk.exceptions.VKApiExecutionException
import com.vk.api.sdk.internal.ApiCommand

import ru.holofox.anicoubs.data.network.NetworkCall
import ru.holofox.anicoubs.data.network.NetworkException
import ru.holofox.anicoubs.data.provider.ConnectivityProvider

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