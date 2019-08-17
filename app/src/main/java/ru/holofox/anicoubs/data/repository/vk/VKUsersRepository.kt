package ru.holofox.anicoubs.data.repository.vk

import ru.holofox.anicoubs.data.network.NetworkCall
import ru.holofox.anicoubs.data.network.response.vk.VKUsersGetResponse

interface VKUsersRepository {
    suspend fun usersGet(userIds: String) : NetworkCall<VKUsersGetResponse>
}