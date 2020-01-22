package ru.holofox.anicoubs.features.domain.repositories.vk

import ru.holofox.anicoubs.features.data.network.NetworkCall
import ru.holofox.anicoubs.features.data.network.api.vk.models.responses.VKUsersGetResponse

interface VKUsersRepository {
    suspend fun usersGet(userIds: String) : NetworkCall<VKUsersGetResponse>
}