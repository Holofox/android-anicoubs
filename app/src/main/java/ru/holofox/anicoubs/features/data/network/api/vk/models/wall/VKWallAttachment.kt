package ru.holofox.anicoubs.features.data.network.api.vk.models.wall

import ru.holofox.anicoubs.features.data.network.api.vk.models.wall.VKWallVideo

data class VKWallAttachment(
    val type: String,
    val video: VKWallVideo?
)