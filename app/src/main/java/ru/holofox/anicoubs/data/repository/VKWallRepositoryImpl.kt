package ru.holofox.anicoubs.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.*

import ru.holofox.anicoubs.data.db.VKWallDao
import ru.holofox.anicoubs.data.db.entity.vk.builder.VKParameters
import ru.holofox.anicoubs.data.db.unitlocalized.vk.asDomainModel
import ru.holofox.anicoubs.data.network.data.VKWallDataSource
import ru.holofox.anicoubs.data.network.response.VKWallGetResponse
import ru.holofox.anicoubs.data.network.response.VKWallPostResponse
import ru.holofox.anicoubs.internal.dto.NetworkResult

class VKWallRepositoryImpl(
    private val vkWallDao: VKWallDao,
    private val vkNetworkDataSource: VKWallDataSource
) : VKWallRepository {

    override val wall = Transformations.map(vkWallDao.getWallMinimal()) {
        it.asDomainModel()
    }

    private val _postWallResult = MutableLiveData<NetworkResult<VKWallPostResponse>>()
    override val postWallResult: LiveData<NetworkResult<VKWallPostResponse>>
        get() = _postWallResult

    init {
        vkNetworkDataSource.apply {
            vkWallGetResponse.observeForever { items ->
                persistFetchedVKWall(items)
            }
            vkWallPostResponse.observeForever {
                _postWallResult.value = it
            }
        }
    }

    override suspend fun wallGet(parameters: VKParameters) {
        return withContext(Dispatchers.IO) {
            if (isFetchTimeLineNeeded(ZonedDateTime.now().minusHours(1)))
                vkNetworkDataSource.wallGet(parameters)
        }
    }

    override suspend fun wallPost(parameters: VKParameters) {
        return withContext(Dispatchers.IO) {
            vkNetworkDataSource.wallPost(parameters)
        }
    }

    private fun persistFetchedVKWall(vkWallGetResponse: VKWallGetResponse) {

        fun deleteOldData() {
            val today = ZonedDateTime.now()
            vkWallDao.deleteOldEntries(today.toEpochSecond())
        }

        GlobalScope.launch(Dispatchers.IO) {
            deleteOldData()
            vkWallDao.insert(vkWallGetResponse.items, vkWallGetResponse.groups)
        }
    }

    private fun isFetchTimeLineNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }

}