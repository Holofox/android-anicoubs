package ru.holofox.anicoubs.features.domain.network.data

import ru.holofox.anicoubs.features.data.db.entities.coub.CoubEntry
import ru.holofox.anicoubs.features.data.network.NetworkCall
import ru.holofox.anicoubs.features.data.network.api.coub.models.responses.CoubChannelResponse
import ru.holofox.anicoubs.features.data.network.api.coub.models.responses.CoubTimelineResponse

interface CoubNetworkDataSource {
    suspend fun fetchFeed(page: Int, per_page: Int): NetworkCall<CoubTimelineResponse>
    suspend fun fetchCoub(permalink: String): NetworkCall<CoubEntry>
    suspend fun fetchChannel(id: Int): NetworkCall<CoubChannelResponse>
}