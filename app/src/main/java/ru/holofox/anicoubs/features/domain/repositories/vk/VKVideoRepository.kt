package ru.holofox.anicoubs.features.domain.repositories.vk

import ru.holofox.anicoubs.features.data.network.api.vk.builders.VKParametersBuilder
import ru.holofox.anicoubs.features.data.network.NetworkCall
import ru.holofox.anicoubs.features.data.network.api.vk.models.responses.video.VKVideoSaveResponse

interface VKVideoRepository {
    suspend fun videoSave(parameters: VKParametersBuilder): NetworkCall<VKVideoSaveResponse>
    suspend fun videoDelete(parameters: VKParametersBuilder)
}