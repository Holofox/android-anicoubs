package ru.holofox.anicoubs.data.repository

import androidx.lifecycle.LiveData

import ru.holofox.anicoubs.data.db.entity.coub.CoubEntry
import ru.holofox.anicoubs.data.db.unitlocalized.coub.UnitSpecificTimelineMinimalEntry
import ru.holofox.anicoubs.data.network.NetworkCall
import ru.holofox.anicoubs.data.network.response.coub.CoubChannelResponse

interface CoubRepository {
    val timeline: LiveData<List<UnitSpecificTimelineMinimalEntry>>
    suspend fun getTimeLine(clean: Boolean)
    suspend fun getCoub(permalink: String) : NetworkCall<CoubEntry>
    suspend fun getChannel(id: Int) : NetworkCall<CoubChannelResponse>
}