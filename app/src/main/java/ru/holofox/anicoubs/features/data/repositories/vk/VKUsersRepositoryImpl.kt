package ru.holofox.anicoubs.features.data.repositories.vk

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.holofox.anicoubs.features.domain.repositories.vk.VKUsersRepository

import ru.holofox.anicoubs.features.data.network.api.vk.builders.VKParametersBuilder
import ru.holofox.anicoubs.features.data.network.NetworkCall
import ru.holofox.anicoubs.features.data.network.NetworkException
import ru.holofox.anicoubs.features.data.network.api.vk.services.VKApiUsersService
import ru.holofox.anicoubs.features.data.network.await
import ru.holofox.anicoubs.features.domain.network.data.VKNetworkDataSource
import ru.holofox.anicoubs.features.data.network.api.vk.models.responses.VKUsersGetResponse

class VKUsersRepositoryImpl(
    private val vkNetworkDataSource: VKNetworkDataSource
) : VKUsersRepository {

    override suspend fun usersGet(userIds: String): NetworkCall<VKUsersGetResponse> {
        return withContext(Dispatchers.IO) {
            val response = NetworkCall<VKUsersGetResponse>()
            val parameters = VKParametersBuilder.Builder()
                .userIds(userIds)
                .build()

            try {
                val result = vkNetworkDataSource.perform(VKApiUsersService.Get(parameters)).await()
                response.onSuccess(result)
            } catch (error: NetworkException) {
                response.onError(NetworkException(error.message.toString()))
            }
            return@withContext response
        }
    }

}