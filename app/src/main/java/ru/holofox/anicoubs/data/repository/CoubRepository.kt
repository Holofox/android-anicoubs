package ru.holofox.anicoubs.data.repository

import androidx.lifecycle.LiveData
import ru.holofox.anicoubs.data.db.entity.coub.CoubEntry
import ru.holofox.anicoubs.data.db.unitlocalized.coub.UnitSpecificTimelineMinimalEntry
import ru.holofox.anicoubs.data.network.NetworkCall

interface CoubRepository {
    val timeline: LiveData<List<UnitSpecificTimelineMinimalEntry>>
    suspend fun getTimeLine(clean: Boolean)
    suspend fun getCoub(permalink: String) : NetworkCall<CoubEntry>
}