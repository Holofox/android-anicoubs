package ru.holofox.anicoubs.data.network.api.vk

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
        private const val VIDEO_DELETE = "video.delete"
        private const val UTILS_GET_SERVER_TIME = "utils.getServerTime"
    }

    class WallGet(parameters: VKParameters) : VKBaseApiCommand<VKWallGetResponse>(
        parameters = parameters,
        method = WALL_GET,
        parser = ResponseApiParser(VKWallGetResponse::class.java)
    )

    class WallPost(parameters: VKParameters) : VKBaseApiCommand<VKWallPostResponse>(
        parameters = parameters,
        method = WALL_POST,
        parser = ResponseApiParser(VKWallPostResponse::class.java)
    )

    class WallDelete(parameters: VKParameters) : VKBaseApiCommand<Boolean>(
        parameters = parameters,
        method = WALL_DELETE,
        parser = ResponseApiParserToBoolean()
    )

    class VideoSave(parameters: VKParameters) : VKBaseApiCommand<VKVideoSaveResponse>(
        parameters = parameters,
        method = VIDEO_SAVE,
        parser = ResponseApiParser(VKVideoSaveResponse::class.java)
    )

    class VideoDelete(parameters: VKParameters) : VKBaseApiCommand<Boolean>(
        parameters = parameters,
        method = VIDEO_DELETE,
        parser = ResponseApiParserToBoolean()
    )

    class UtilsGetServerTime : VKBaseApiCommand<Long>(
        method = UTILS_GET_SERVER_TIME,
        parser = ResponseApiParserToLong()
    )

}