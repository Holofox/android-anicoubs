package ru.holofox.anicoubs.data.network.api

import ru.holofox.anicoubs.data.network.response.VKWallGetResponse
import ru.holofox.anicoubs.data.db.entity.vk.builder.VKParameters
import ru.holofox.anicoubs.data.network.response.VKVideoSaveResponse
import ru.holofox.anicoubs.data.network.response.VKWallPostResponse

class VKApiService {

    companion object METHODS {
        private const val WALL_GET = "wall.get"
        private const val WALL_POST = "wall.post"
        private const val VIDEO_SAVE = "video.save"
    }

    class WallGet(parameters: VKParameters) : VKBaseApiCommand<VKWallGetResponse>(
        parameters = parameters,
        method = WALL_GET
    ) {
        override val classToken = VKWallGetResponse::class.java
    }

    class WallPost(parameters: VKParameters) : VKBaseApiCommand<VKWallPostResponse>(
        parameters = parameters,
        method = WALL_POST
    ) {
        override val classToken = VKWallPostResponse::class.java
    }

    class VideoSave(parameters: VKParameters) : VKBaseApiCommand<VKVideoSaveResponse>(
        parameters = parameters,
        method = VIDEO_SAVE
    ){
        override val classToken = VKVideoSaveResponse::class.java
    }

}