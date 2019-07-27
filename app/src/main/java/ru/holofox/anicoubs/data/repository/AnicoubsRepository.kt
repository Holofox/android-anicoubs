package ru.holofox.anicoubs.data.repository

import androidx.lifecycle.LiveData
import ru.holofox.anicoubs.data.db.unitlocalized.coub.UnitSpecificTimelineMinimalEntry

interface AnicoubsRepository {
    val timeline: LiveData<List<UnitSpecificTimelineMinimalEntry>>
    suspend fun getTimeLine(clean: Boolean)
}