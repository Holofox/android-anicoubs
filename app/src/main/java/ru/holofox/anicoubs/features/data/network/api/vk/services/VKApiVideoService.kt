package ru.holofox.anicoubs.features.data.network.api.vk.services

import ru.holofox.anicoubs.features.data.network.api.vk.builders.VKParametersBuilder
import ru.holofox.anicoubs.features.data.network.api.vk.ResponseApiParser
import ru.holofox.anicoubs.features.data.network.api.vk.ResponseApiParserToBoolean
import ru.holofox.anicoubs.features.data.network.api.vk.models.responses.video.VKVideoSaveResponse
import ru.holofox.anicoubs.features.data.network.api.vk.VKBaseApiCommand

class VKApiVideoService {
    companion object METHODS {
        private const val VIDEO_SAVE = "video.save"
        private const val VIDEO_DELETE = "video.delete"
    }

    class Save(parameters: VKParametersBuilder) : VKBaseApiCommand<VKVideoSaveResponse>(
        parameters = parameters,
        method = VIDEO_SAVE,
        parser = ResponseApiParser(VKVideoSaveResponse::class.java)
    )

    class Delete(parameters: VKParametersBuilder) : VKBaseApiCommand<Boolean>(
        parameters = parameters,
        method = VIDEO_DELETE,
        parser = ResponseApiParserToBoolean()
    )
}