package ru.holofox.anicoubs.data.network.api

import ru.holofox.anicoubs.data.network.response.vk.VKWallGetResponse
import ru.holofox.anicoubs.data.db.entity.vk.builder.VKParameters
import ru.holofox.anicoubs.data.network.response.vk.VKVideoSaveResponse
import ru.holofox.anicoubs.data.network.response.vk.VKWallPostResponse

class VKApiService {

    companion object METHODS {
        private const val WALL_GET = "wall.get"
        private const val WALL_POST = "wall.post"
        private const val WALL_DELETE = "wall.delete"
        private const val VIDEO_SAVE = "video.save"
    }

    class WallGet(parameters: VKParameters) : VKBaseApiCommand<VKWallGetResponse>(
        parameters = parameters,
        method = WALL_GET,
        classToken = VKWallGetResponse::class.java
    )

    class WallPost(parameters: VKParameters) : VKBaseApiCommand<VKWallPostResponse>(
        parameters = parameters,
        method = WALL_POST,
        classToken = VKWallPostResponse::class.java
    )

    class WallDelete(parameters: VKParameters) : VKBaseApiCommand<Boolean>(
        parameters = parameters,
        method = WALL_DELETE,
        classToken = Boolean::class.java
    )

    class VideoSave(parameters: VKParameters) : VKBaseApiCommand<VKVideoSaveResponse>(
        parameters = parameters,
        method = VIDEO_SAVE,
        classToken = VKVideoSaveResponse::class.java
    )

}