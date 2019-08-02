package ru.holofox.anicoubs.data.repository

import androidx.lifecycle.Transformations

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import ru.holofox.anicoubs.data.db.VKWallDao
import ru.holofox.anicoubs.data.db.entity.vk.builder.VKParameters
import ru.holofox.anicoubs.data.db.unitlocalized.vk.UnitSpecificVKWallMinimalEntry
import ru.holofox.anicoubs.data.db.unitlocalized.vk.asDomainModel

import ru.holofox.anicoubs.data.network.*
import ru.holofox.anicoubs.data.network.data.VKWallDataSource

import ru.holofox.anicoubs.internal.Constants

class VKWallRepositoryImpl(
    private val vkWallDao: VKWallDao,
    private val vkNetworkDataSource: VKWallDataSource
) : VKWallRepository {

    override val wall = Transformations.map(vkWallDao.getWallMinimal()) {
        it.asDomainModel()
    }

    override suspend fun wallGet(filter: String) = withContext(Dispatchers.IO) {
        val parameters = VKParameters.Builder()
            .ownerId(Constants.TARGET_GROUP_ID)
            .count(25)
            .filter(filter)
            .extended(true)
            .build()

        try {
            val result = vkNetworkDataSource.wallGet(parameters).await()
            vkWallDao.update(result.items, result.groups)
        } catch (error: NetworkException) {
            throw VKWallRefreshError(error)
        }
    }

    override suspend fun wallPost(item: UnitSpecificVKWallMinimalEntry) = withContext(Dispatchers.IO) {
        val parameters = VKParameters.Builder()
            .ownerId(item.ownerId)
            .postId(item.postId)
            .build()

        try {
            val result = vkNetworkDataSource.wallPost(parameters).await()
            vkWallDao.delete(result.postId)
        } catch (error: NetworkException) {
            throw VKWallRefreshError(error)
        }
    }

    override suspend fun wallDelete(item: UnitSpecificVKWallMinimalEntry) = withContext(Dispatchers.IO) {
        val parameters = VKParameters.Builder()
            .ownerId(item.ownerId)
            .postId(item.postId)
            .build()

        try {
            vkNetworkDataSource.wallDelete(parameters).await()
            vkWallDao.delete(item.postId)
        } catch (error: NetworkException) {
            throw VKWallRefreshError(error)
        }
    }

}

class VKWallRefreshError(cause: Throwable) : Throwable(cause.message, cause)