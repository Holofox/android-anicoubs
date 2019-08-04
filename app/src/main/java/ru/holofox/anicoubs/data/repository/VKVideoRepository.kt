package ru.holofox.anicoubs.data.repository

import ru.holofox.anicoubs.data.db.entity.vk.builder.VKParameters
import ru.holofox.anicoubs.data.db.entity.vk.wall.Video
import ru.holofox.anicoubs.data.network.NetworkCall
import ru.holofox.anicoubs.data.network.response.vk.VKVideoSaveResponse

interface VKVideoRepository {
    suspend fun videoSave(parameters: VKParameters) : NetworkCall<VKVideoSaveResponse>
    suspend fun videoDelete(video: Video)
}