package ru.holofox.anicoubs.data.network.data

import androidx.lifecycle.LiveData
import ru.holofox.anicoubs.data.network.response.TimeLineResponse

interface TimeLineNetworkDataSource {
    val loadedFeed: LiveData<TimeLineResponse>

    suspend fun fetchFeed(
        page: Int,
        per_page: Int
     //   order_by: String
    )
}