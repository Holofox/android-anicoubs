package ru.holofox.anicoubs.data.network.response

import ru.holofox.anicoubs.data.db.entity.vk.GroupEntry
import ru.holofox.anicoubs.data.db.entity.vk.WallItemEntry

data class VKWallGetResponse(
    val count: Int,
    val groups: List<GroupEntry>,
    val items: List<WallItemEntry>
)