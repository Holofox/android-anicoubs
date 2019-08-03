package ru.holofox.anicoubs.data.network.data

import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import com.vk.api.sdk.exceptions.VKApiExecutionException
import com.vk.api.sdk.internal.ApiCommand

import ru.holofox.anicoubs.data.db.entity.vk.builder.VKParameters
import ru.holofox.anicoubs.data.network.NetworkCall
import ru.holofox.anicoubs.data.network.NetworkException
import ru.holofox.anicoubs.data.network.api.VKApiService
import ru.holofox.anicoubs.data.network.response.vk.VKWallGetResponse
import ru.holofox.anicoubs.data.network.response.vk.VKWallPostResponse
import ru.holofox.anicoubs.data.provider.ConnectivityProvider

class VKNetworkDataSourceImpl(
    private val connectivityProvider: ConnectivityProvider
): VKWallDataSource {

    override suspend fun wallGet(parameters: VKParameters) : NetworkCall<VKWallGetResponse> {
        return perform(VKApiService.WallGet(parameters))
    }

    override suspend fun wallPost(parameters: VKParameters) : NetworkCall<VKWallPostResponse> {
        return perform(VKApiService.WallPost(parameters))
    }

    override suspend fun wallDelete(parameters: VKParameters) : NetworkCall<Boolean> {
        return perform(VKApiService.WallDelete(parameters))
    }

    private fun <T>perform(apiCommand: ApiCommand<T>) : NetworkCall<T> {
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