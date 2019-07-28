package ru.holofox.anicoubs.data.network.api

import ru.holofox.anicoubs.data.network.response.VKWallGetResponse
import ru.holofox.anicoubs.data.db.entity.vk.builder.VKParameters
import ru.holofox.anicoubs.data.network.response.VKVideoSaveResponse
import ru.holofox.anicoubs.data.network.response.VKWallGetByIdResponse

class VKApiService {

    companion object METHODS {
        private const val WALL_GET = "wall.get"
        private const val WALL_GET_BY_ID = "wall.getById"
        private const val VIDEO_SAVE = "video.save"
    }

    class WallGet(parameters: VKParameters) : VKBaseApiCommand<VKWallGetResponse>(
        parameters = parameters,
        method = WALL_GET
    ) {
        override val classToken = VKWallGetResponse::class.java
    }

    class WallGetById(parameters: VKParameters) : VKBaseApiCommand<VKWallGetByIdResponse>(
        parameters = parameters,
        method = WALL_GET_BY_ID
    ) {
        override val classToken = VKWallGetByIdResponse::class.java
    }

    class VideoSave(parameters: VKParameters) : VKBaseApiCommand<VKVideoSaveResponse>(
        parameters = parameters,
        method = VIDEO_SAVE
    ){
        override val classToken = VKVideoSaveResponse::class.java
    }

}