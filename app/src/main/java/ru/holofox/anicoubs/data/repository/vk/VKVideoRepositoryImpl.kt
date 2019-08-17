package ru.holofox.anicoubs.data.repository.vk

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.holofox.anicoubs.data.db.entity.vk.builder.VKParameters
import ru.holofox.anicoubs.data.network.NetworkCall
import ru.holofox.anicoubs.data.network.NetworkException
import ru.holofox.anicoubs.data.network.api.VKApiService
import ru.holofox.anicoubs.data.network.api.vk.VKApiVideoService
import ru.holofox.anicoubs.data.network.await
import ru.holofox.anicoubs.data.network.data.VKNetworkDataSource
import ru.holofox.anicoubs.data.network.response.vk.VKVideoSaveResponse
import ru.holofox.anicoubs.internal.NoConnectivityException
import ru.holofox.anicoubs.internal.VKVideoRepositoryError

class VKVideoRepositoryImpl(
    private val vkNetworkDataSource: VKNetworkDataSource,
    private val vkApiService: VKApiService
) : VKVideoRepository {

    override suspend fun videoSave(parameters: VKParameters) : NetworkCall<VKVideoSaveResponse> {
        return withContext(Dispatchers.IO) {
            val response = NetworkCall<VKVideoSaveResponse>()
            try {
                val result = vkNetworkDataSource.perform(VKApiVideoService.Save(parameters)).await()

                try {
                    vkApiService.uploadVideoByUrlAsync(result.uploadUrl).await()
                } catch (error: NoConnectivityException) {
                    response.onError(NetworkException(error.message.toString()))
                }

                response.onSuccess(result)
            } catch (error: NetworkException) {
                response.onError(NetworkException(error.message.toString()))
            }
            return@withContext response
        }
    }

    override suspend fun videoDelete(parameters: VKParameters) {
        withContext(Dispatchers.IO) {
            try {
                vkNetworkDataSource.perform(VKApiVideoService.Delete(parameters)).await()
            } catch (error: NetworkException) {
                throw VKVideoRepositoryError(error)
            }
        }
    }

}