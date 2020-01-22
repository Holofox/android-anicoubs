package ru.holofox.anicoubs.features.domain.repositories

import androidx.lifecycle.LiveData

import ru.holofox.anicoubs.features.data.db.entities.coub.CoubEntry
import ru.holofox.anicoubs.features.domain.db.unitlocalized.coub.UnitSpecificTimelineMinimalEntry
import ru.holofox.anicoubs.features.data.network.NetworkCall
import ru.holofox.anicoubs.features.data.network.api.coub.models.responses.CoubChannelResponse

interface CoubRepository {
    val timeline: LiveData<List<UnitSpecificTimelineMinimalEntry>>
    suspend fun getTimeLine(clean: Boolean)
    suspend fun getCoub(permalink: String) : NetworkCall<CoubEntry>
    suspend fun getChannel(id: Int) : NetworkCall<CoubChannelResponse>
}