package ru.holofox.anicoubs.data.network.api.vk

import ru.holofox.anicoubs.data.db.entity.vk.builder.VKParameters
import ru.holofox.anicoubs.data.network.response.vk.VKUsersGetResponse

class VKApiUsersService {
    companion object METHODS {
        private const val USERS_GET = "users.get"
    }

    class Get(parameters: VKParameters) : VKBaseApiCommand<VKUsersGetResponse>(
        parameters = parameters,
        method = USERS_GET,
        parser = ResponseApiArrayParser(VKUsersGetResponse::class.java)
    )

}