package ru.holofox.anicoubs.features.domain.repositories.vk

import androidx.lifecycle.LiveData
import ru.holofox.anicoubs.features.data.network.api.vk.builders.VKParametersBuilder
import ru.holofox.anicoubs.features.domain.db.unitlocalized.vk.UnitSpecificVKWallMinimalEntry

interface VKWallRepository {
    val wall: LiveData<List<UnitSpecificVKWallMinimalEntry>>

    suspend fun wallGet(filter: String)
    suspend fun wallPost(parameters: VKParametersBuilder)
    suspend fun wallDelete(parameters: VKParametersBuilder)

    suspend fun wallDaoDelete(postId: Int)
}