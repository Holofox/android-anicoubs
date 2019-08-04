package ru.holofox.anicoubs.data.repository

import android.util.Log
import androidx.lifecycle.Transformations

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import ru.holofox.anicoubs.data.db.VKWallDao
import ru.holofox.anicoubs.data.db.entity.vk.builder.VKParameters
import ru.holofox.anicoubs.data.db.unitlocalized.vk.UnitSpecificVKWallMinimalEntry
import ru.holofox.anicoubs.data.db.unitlocalized.vk.asDomainModel

import ru.holofox.anicoubs.data.network.*
import ru.holofox.anicoubs.data.network.data.VKNetworkDataSource

import ru.holofox.anicoubs.internal.Constants
import ru.holofox.anicoubs.internal.VKWallRepositoryError

class VKWallRepositoryImpl(
    private val vkWallDao: VKWallDao,
    private val vkNetworkDataSource: VKNetworkDataSource
) : VKWallRepository {

    override val wall = Transformations.map(vkWallDao.getWallMinimal()) {
        it.asDomainModel()
    }

    override suspend fun wallGet(filter: String) {
        val parameters = VKParameters.Builder()
            .ownerId(Constants.TARGET_GROUP_ID)
            .count(25)
            .filter(filter)
            .extended(true)
            .build()

        withContext(Dispatchers.IO) {
            try {
                val result = vkNetworkDataSource.wallGet(parameters).await()
                vkWallDao.update(result.items, result.groups)
            } catch (error: NetworkException) {
                throw VKWallRepositoryError(error)
            }
        }
    }

    override suspend fun wallPost(item: UnitSpecificVKWallMinimalEntry) {
        val parameters = VKParameters.Builder()
            .ownerId(item.ownerId)
            .postId(item.postId)
            .build()

        withContext(Dispatchers.IO) {
            try {
                vkNetworkDataSource.wallPost(parameters).await()
                vkWallDao.delete(item.postId)
            } catch (error: NetworkException) {
                throw VKWallRepositoryError(error)
            }
        }
    }

    override suspend fun wallDelete(item: UnitSpecificVKWallMinimalEntry) {
        val parameters = VKParameters.Builder()
            .ownerId(item.ownerId)
            .postId(item.postId)
            .build()

        withContext(Dispatchers.IO) {
            try {
                vkNetworkDataSource.wallDelete(parameters).await()
                vkWallDao.delete(item.postId)
            } catch (error: NetworkException) {
                throw VKWallRepositoryError(error)
            }
        }
    }

}