package ru.holofox.anicoubs.data.repository

import androidx.lifecycle.Transformations
import kotlinx.coroutines.*

import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZonedDateTime

import ru.holofox.anicoubs.data.db.TimeLineDao
import ru.holofox.anicoubs.data.db.entity.coub.CoubEntry
import ru.holofox.anicoubs.data.db.unitlocalized.coub.asDomainModel
import ru.holofox.anicoubs.data.network.NetworkCall
import ru.holofox.anicoubs.data.network.NetworkException
import ru.holofox.anicoubs.data.network.await
import ru.holofox.anicoubs.data.network.data.CoubNetworkDataSource
import ru.holofox.anicoubs.data.network.response.coub.CoubChannelResponse
import ru.holofox.anicoubs.data.network.response.coub.CoubTimelineResponse
import ru.holofox.anicoubs.internal.CoubRepositoryError

class CoubRepositoryImpl(
    private val timelineDao: TimeLineDao,
    private val timelineNetworkDataSource: CoubNetworkDataSource
) : CoubRepository {

    override val timeline = Transformations.map(timelineDao.getTimeline()) {
        it.asDomainModel()
    }

    override suspend fun getTimeLine(clean: Boolean) {
        withContext(Dispatchers.IO) {
            initTimeLineData()
        }
    }

    override suspend fun getCoub(permalink: String) = withContext(Dispatchers.IO) {
        val response = NetworkCall<CoubEntry>()

        try {
            val result = timelineNetworkDataSource.fetchCoub(permalink).await()
            response.onSuccess(result)
        } catch (error: NetworkException) {
            response.onError(error)
        }

        return@withContext response
    }

    override suspend fun getChannel(id: Int) = withContext(Dispatchers.IO) {
        val response = NetworkCall<CoubChannelResponse>()

        try {
            val result = timelineNetworkDataSource.fetchChannel(id).await()
            response.onSuccess(result)
        } catch (error: NetworkException) {
            response.onError(error)
        }

        return@withContext response
    }

    private fun persistFetchedTimeLine(fetchedCoubTimeline: CoubTimelineResponse) {

        fun deleteOldTimelineData() {
            val today = LocalDateTime.now()
            timelineDao.deleteOldEntries(today)
        }

        GlobalScope.launch(Dispatchers.IO) {
            deleteOldTimelineData()
            timelineDao.insert(fetchedCoubTimeline.coubs)
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
            throw CoubRepositoryError(error)
        }
    }

    private fun isFetchTimeLineNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }

}