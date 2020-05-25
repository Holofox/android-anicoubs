package ru.holofox.anicoubs.features.data.repositories.vk

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.holofox.anicoubs.features.data.network.NetworkCall
import ru.holofox.anicoubs.features.data.network.NetworkException
import ru.holofox.anicoubs.features.data.network.api.vk.builders.VKParametersBuilder
import ru.holofox.anicoubs.features.data.network.api.vk.models.responses.video.VKVideoSaveResponse
import ru.holofox.anicoubs.features.data.network.api.vk.services.VKApiVideoService
import ru.holofox.anicoubs.features.data.network.await
import ru.holofox.anicoubs.features.domain.network.api.VKApiService
import ru.holofox.anicoubs.features.domain.network.data.VKNetworkDataSource
import ru.holofox.anicoubs.features.domain.repositories.vk.VKVideoRepository
import ru.holofox.anicoubs.internal.NoConnectivityException
import ru.holofox.anicoubs.internal.VKVideoRepositoryError

class VKVideoRepositoryImpl(
    private val vkNetworkDataSource: VKNetworkDataSource,
    private val vkApiService: VKApiService
) : VKVideoRepository {

    override suspend fun videoSave(parameters: VKParametersBuilder): NetworkCall<VKVideoSaveResponse> {
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

    override suspend fun videoDelete(parameters: VKParametersBuilder) {
        withContext(Dispatchers.IO) {
            try {
                vkNetworkDataSource.perform(VKApiVideoService.Delete(parameters)).await()
            } catch (error: NetworkException) {
                throw VKVideoRepositoryError(error)
            }
        }
    }

}