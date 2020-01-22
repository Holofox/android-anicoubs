package ru.holofox.anicoubs.features.data.network.api.vk.services

import ru.holofox.anicoubs.features.data.network.api.vk.ResponseApiParserToLong
import ru.holofox.anicoubs.features.data.network.api.vk.VKBaseApiCommand

class VKApiUtilsService {

    companion object METHODS {
        private const val UTILS_GET_SERVER_TIME = "utils.getServerTime"
    }

    class GetServerTime : VKBaseApiCommand<Long>(
        method = UTILS_GET_SERVER_TIME,
        parser = ResponseApiParserToLong()
    )

}