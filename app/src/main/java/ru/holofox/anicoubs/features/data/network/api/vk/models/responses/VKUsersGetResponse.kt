package ru.holofox.anicoubs.features.data.network.api.vk.models.responses

import ru.holofox.anicoubs.features.data.db.entities.vk.VKUsersGetEntry

data class VKUsersGetResponse(
    val response: List<VKUsersGetEntry>
)