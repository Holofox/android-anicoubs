package ru.holofox.anicoubs.data.network.api.vk

import ru.holofox.anicoubs.data.db.entity.vk.builder.VKParameters
import ru.holofox.anicoubs.data.network.response.vk.VKVideoSaveResponse

class VKApiVideoService {
    companion object METHODS {
        private const val VIDEO_SAVE = "video.save"
        private const val VIDEO_DELETE = "video.delete"
    }

    class Save(parameters: VKParameters) : VKBaseApiCommand<VKVideoSaveResponse>(
        parameters = parameters,
        method = VIDEO_SAVE,
        parser = ResponseApiParser(VKVideoSaveResponse::class.java)
    )

    class Delete(parameters: VKParameters) : VKBaseApiCommand<Boolean>(
        parameters = parameters,
        method = VIDEO_DELETE,
        parser = ResponseApiParserToBoolean()
    )
}