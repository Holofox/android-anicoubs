package ru.holofox.anicoubs.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.holofox.anicoubs.data.db.entity.vk.builder.VKParameters
import ru.holofox.anicoubs.data.db.entity.vk.wall.Video
import ru.holofox.anicoubs.data.network.NetworkCall
import ru.holofox.anicoubs.data.network.NetworkException
import ru.holofox.anicoubs.data.network.await
import ru.holofox.anicoubs.data.network.data.VKNetworkDataSource
import ru.holofox.anicoubs.data.network.response.vk.VKVideoSaveResponse
import ru.holofox.anicoubs.internal.VKVideoRepositoryError

class VKVideoRepositoryImpl(
    private val vkNetworkDataSource: VKNetworkDataSource
) : VKVideoRepository {
    override suspend fun videoSave(parameters: VKParameters) : NetworkCall<VKVideoSaveResponse> {
        return withContext(Dispatchers.IO) {
            val response = NetworkCall<VKVideoSaveResponse>()

            try {
                val result = vkNetworkDataSource.videoSave(parameters).await()
                response.onSuccess(result)
            } catch (error: NetworkException) {
                response.onError(NetworkException(error.message.toString()))
            }
            return@withContext response
        }

    }

    override suspend fun videoDelete(video: Video) {
        val parameters = VKParameters.Builder()
            .videoId(video.id)
            .ownerId(video.ownerId)
            .build()

        withContext(Dispatchers.IO) {
            try {
                vkNetworkDataSource.videoDelete(parameters).await()
            } catch (error: NetworkException) {
                throw VKVideoRepositoryError(error)
            }
        }
    }

}