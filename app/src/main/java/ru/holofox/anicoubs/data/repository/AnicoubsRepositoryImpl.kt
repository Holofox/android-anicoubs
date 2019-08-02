package ru.holofox.anicoubs.data.repository

import androidx.lifecycle.Transformations
import kotlinx.coroutines.*

import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZonedDateTime

import ru.holofox.anicoubs.data.db.TimeLineDao
import ru.holofox.anicoubs.data.db.unitlocalized.coub.asDomainModel
import ru.holofox.anicoubs.data.network.NetworkException
import ru.holofox.anicoubs.data.network.await
import ru.holofox.anicoubs.data.network.data.TimeLineNetworkDataSource
import ru.holofox.anicoubs.data.network.response.TimeLineResponse

class AnicoubsRepositoryImpl(
    private val timelineDao: TimeLineDao,
    private val timelineNetworkDataSource: TimeLineNetworkDataSource
) : AnicoubsRepository {

    override val timeline = Transformations.map(timelineDao.getTimeline()) {
        it.asDomainModel()
    }

    override suspend fun getTimeLine(clean: Boolean) {
        withContext(Dispatchers.IO) {
            initTimeLineData()
        }
    }

    private fun persistFetchedTimeLine(fetchedTimeLine: TimeLineResponse) {

        fun deleteOldTimelineData() {
            val today = LocalDateTime.now()
            timelineDao.deleteOldEntries(today)
        }

        GlobalScope.launch(Dispatchers.IO) {
            deleteOldTimelineData()
            timelineDao.insert(fetchedTimeLine.coubs)
        }
    }

    private suspend fun initTimeLineData(){
        if (isFetchTimeLineNeeded(ZonedDateTime.now().minusHours(1)))
            fetchTimeLine()
    }

    private suspend fun fetchTimeLine()  {
        try {
            val result = timelineNetworkDataSource.fetchFeed(0, 10).await()
            persistFetchedTimeLine(result)
        } catch (error: NetworkException) {
            throw AnicoubsRefreshError(error)
        }
    }

    private fun isFetchTimeLineNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }

}

class AnicoubsRefreshError(cause: Throwable) : Throwable(cause.message, cause)