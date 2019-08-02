package ru.holofox.anicoubs.data.network.data

import android.util.Log
import ru.holofox.anicoubs.data.network.NetworkCall
import ru.holofox.anicoubs.data.network.NetworkException
import ru.holofox.anicoubs.data.network.api.TimeLineApiService
import ru.holofox.anicoubs.data.network.response.TimeLineResponse
import ru.holofox.anicoubs.internal.NoConnectivityException

class TimeLineNetworkDataSourceImpl(
    private val feedApiService: TimeLineApiService
) : TimeLineNetworkDataSource {

    override suspend fun fetchFeed(page: Int, per_page: Int) : NetworkCall<TimeLineResponse> {
        val response = NetworkCall<TimeLineResponse>()

        try {
            val result = feedApiService
                .getTimeLineAsync(page, per_page)
                .await()
            response.onSuccess(result)
        } catch (error: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection!", error)
            response.onError(NetworkException("No internet connection!"))
        }

        return response
    }
}