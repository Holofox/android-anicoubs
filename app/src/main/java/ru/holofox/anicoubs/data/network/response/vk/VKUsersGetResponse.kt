package ru.holofox.anicoubs.data.network.response.vk

import ru.holofox.anicoubs.data.db.entity.vk.UsersGetEntry

data class VKUsersGetResponse(
    val response: List<UsersGetEntry>
)