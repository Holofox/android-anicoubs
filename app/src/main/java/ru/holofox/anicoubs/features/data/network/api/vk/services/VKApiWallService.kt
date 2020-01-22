package ru.holofox.anicoubs.features.data.network.api.vk.services

import ru.holofox.anicoubs.features.data.network.api.vk.builders.VKParametersBuilder
import ru.holofox.anicoubs.features.data.network.api.vk.ResponseApiParser
import ru.holofox.anicoubs.features.data.network.api.vk.ResponseApiParserToBoolean
import ru.holofox.anicoubs.features.data.network.api.vk.models.responses.wall.VKWallGetResponse
import ru.holofox.anicoubs.features.data.network.api.vk.models.responses.wall.VKWallPostResponse
import ru.holofox.anicoubs.features.data.network.api.vk.VKBaseApiCommand

class VKApiWallService {
    companion object METHODS {
        private const val WALL_GET = "wall.get"
        private const val WALL_POST = "wall.post"
        private const val WALL_DELETE = "wall.delete"
    }

    class Get(parameters: VKParametersBuilder) : VKBaseApiCommand<VKWallGetResponse>(
        parameters = parameters,
        method = WALL_GET,
        parser = ResponseApiParser(VKWallGetResponse::class.java)
    )

    class Post(parameters: VKParametersBuilder) : VKBaseApiCommand<VKWallPostResponse>(
        parameters = parameters,
        method = WALL_POST,
        parser = ResponseApiParser(VKWallPostResponse::class.java)
    )

    class Delete(parameters: VKParametersBuilder) : VKBaseApiCommand<Boolean>(
        parameters = parameters,
        method = WALL_DELETE,
        parser = ResponseApiParserToBoolean()
    )

}