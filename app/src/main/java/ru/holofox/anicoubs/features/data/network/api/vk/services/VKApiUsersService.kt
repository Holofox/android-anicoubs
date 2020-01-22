package ru.holofox.anicoubs.features.data.network.api.vk.services

import ru.holofox.anicoubs.features.data.network.api.vk.builders.VKParametersBuilder
import ru.holofox.anicoubs.features.data.network.api.vk.ResponseApiArrayParser
import ru.holofox.anicoubs.features.data.network.api.vk.models.responses.VKUsersGetResponse
import ru.holofox.anicoubs.features.data.network.api.vk.VKBaseApiCommand

class VKApiUsersService {
    companion object METHODS {
        private const val USERS_GET = "users.get"
    }

    class Get(parameters: VKParametersBuilder) : VKBaseApiCommand<VKUsersGetResponse>(
        parameters = parameters,
        method = USERS_GET,
        parser = ResponseApiArrayParser(VKUsersGetResponse::class.java)
    )

}