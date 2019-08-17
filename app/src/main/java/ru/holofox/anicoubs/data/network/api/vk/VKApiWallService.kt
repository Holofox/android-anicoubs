package ru.holofox.anicoubs.data.network.api.vk

import ru.holofox.anicoubs.data.db.entity.vk.builder.VKParameters
import ru.holofox.anicoubs.data.network.response.vk.VKWallGetResponse
import ru.holofox.anicoubs.data.network.response.vk.VKWallPostResponse

class VKApiWallService {
    companion object METHODS {
        private const val WALL_GET = "wall.get"
        private const val WALL_POST = "wall.post"
        private const val WALL_DELETE = "wall.delete"
    }

    class Get(parameters: VKParameters) : VKBaseApiCommand<VKWallGetResponse>(
        parameters = parameters,
        method = WALL_GET,
        parser = ResponseApiParser(VKWallGetResponse::class.java)
    )

    class Post(parameters: VKParameters) : VKBaseApiCommand<VKWallPostResponse>(
        parameters = parameters,
        method = WALL_POST,
        parser = ResponseApiParser(VKWallPostResponse::class.java)
    )

    class Delete(parameters: VKParameters) : VKBaseApiCommand<Boolean>(
        parameters = parameters,
        method = WALL_DELETE,
        parser = ResponseApiParserToBoolean()
    )

}