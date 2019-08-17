package ru.holofox.anicoubs.data.network.api.vk

class VKApiUtilsService {

    companion object METHODS {
        private const val UTILS_GET_SERVER_TIME = "utils.getServerTime"
    }

    class GetServerTime : VKBaseApiCommand<Long>(
        method = UTILS_GET_SERVER_TIME,
        parser = ResponseApiParserToLong()
    )

}