package ru.holofox.anicoubs.data.network.data

import ru.holofox.anicoubs.data.network.NetworkCall
import ru.holofox.anicoubs.data.network.response.TimeLineResponse

interface TimeLineNetworkDataSource {
    suspend fun fetchFeed(page: Int, per_page: Int) : NetworkCall<TimeLineResponse>
}