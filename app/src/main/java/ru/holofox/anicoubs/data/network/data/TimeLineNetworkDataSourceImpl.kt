package ru.holofox.anicoubs.data.network.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.holofox.anicoubs.data.network.api.TimeLineApiService
import ru.holofox.anicoubs.data.network.response.TimeLineResponse

class TimeLineNetworkDataSourceImpl(
    private val feedApiService: TimeLineApiService
) : TimeLineNetworkDataSource {

    private val _loadedFeed = MutableLiveData<TimeLineResponse>()
    override val loadedFeed: LiveData<TimeLineResponse>
        get() = _loadedFeed

    override suspend fun fetchFeed(page: Int, per_page: Int) {
            val fetchedFeed = feedApiService
                .getTimeLineAsync(page, per_page)
                .await()
            _loadedFeed.postValue(fetchedFeed)
    }
}