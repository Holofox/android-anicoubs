package ru.holofox.anicoubs.features.data.network.api.vk.models.responses.wall

import ru.holofox.anicoubs.features.data.db.entities.vk.VKGroupEntry
import ru.holofox.anicoubs.features.data.db.entities.vk.VKWallItemEntry

data class VKWallGetResponse(
    val count: Int,
    val groups: List<VKGroupEntry>,
    val items: List<VKWallItemEntry>
)