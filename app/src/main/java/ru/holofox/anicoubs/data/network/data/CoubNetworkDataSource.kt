package ru.holofox.anicoubs.data.network.data

import ru.holofox.anicoubs.data.network.response.coub.CoubChannelResponse
import ru.holofox.anicoubs.data.db.entity.coub.CoubEntry
import ru.holofox.anicoubs.data.network.NetworkCall
import ru.holofox.anicoubs.data.network.response.coub.CoubTimelineResponse

interface CoubNetworkDataSource {
    suspend fun fetchFeed(page: Int, per_page: Int) : NetworkCall<CoubTimelineResponse>
    suspend fun fetchCoub(permalink: String) : NetworkCall<CoubEntry>
    suspend fun fetchChannel(id: String) : NetworkCall<CoubChannelResponse>
}