package ru.holofox.anicoubs.features.data.repositories.vk

import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.holofox.anicoubs.features.data.db.unitlocalized.vk.asDomainModel
import ru.holofox.anicoubs.features.data.network.NetworkException
import ru.holofox.anicoubs.features.data.network.api.vk.builders.VKParametersBuilder
import ru.holofox.anicoubs.features.data.network.api.vk.services.VKApiWallService
import ru.holofox.anicoubs.features.data.network.await
import ru.holofox.anicoubs.features.domain.db.VKWallDao
import ru.holofox.anicoubs.features.domain.network.data.VKNetworkDataSource
import ru.holofox.anicoubs.features.domain.repositories.vk.VKWallRepository
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
        val parameters = VKParametersBuilder.Builder()
            .ownerId(Constants.TARGET_GROUP_ID)
            .count(25)
            .filter(filter)
            .extended(true)
            .build()

        withContext(Dispatchers.IO) {
            try {
                val result = vkNetworkDataSource.perform(VKApiWallService.Get(parameters)).await()
                vkWallDao.update(result.items, result.groups)
            } catch (error: NetworkException) {
                throw VKWallRepositoryError(error)
            }
        }
    }

    override suspend fun wallPost(parameters: VKParametersBuilder) {
        withContext(Dispatchers.IO) {
            try {
                vkNetworkDataSource.perform(VKApiWallService.Post(parameters)).await()
            } catch (error: NetworkException) {
                throw VKWallRepositoryError(error)
            }
        }
    }

    override suspend fun wallDaoDelete(postId: Int) {
        vkWallDao.delete(postId)
    }

    override suspend fun wallDelete(parameters: VKParametersBuilder) {
        withContext(Dispatchers.IO) {
            try {
                vkNetworkDataSource.perform(VKApiWallService.Delete(parameters)).await()
            } catch (error: NetworkException) {
                throw VKWallRepositoryError(error)
            }
        }
    }

}