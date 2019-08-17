package ru.holofox.anicoubs.data.repository.vk

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import ru.holofox.anicoubs.data.db.entity.vk.builder.VKParameters
import ru.holofox.anicoubs.data.network.NetworkCall
import ru.holofox.anicoubs.data.network.NetworkException
import ru.holofox.anicoubs.data.network.api.vk.VKApiUsersService
import ru.holofox.anicoubs.data.network.await
import ru.holofox.anicoubs.data.network.data.VKNetworkDataSource
import ru.holofox.anicoubs.data.network.response.vk.VKUsersGetResponse

class VKUsersRepositoryImpl(
    private val vkNetworkDataSource: VKNetworkDataSource
) : VKUsersRepository {

    override suspend fun usersGet(userIds: String) : NetworkCall<VKUsersGetResponse> {
        return withContext(Dispatchers.IO) {
            val response = NetworkCall<VKUsersGetResponse>()
            val parameters = VKParameters.Builder()
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