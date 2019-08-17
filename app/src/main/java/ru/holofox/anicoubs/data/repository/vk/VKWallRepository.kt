package ru.holofox.anicoubs.data.repository.vk

import androidx.lifecycle.LiveData
import ru.holofox.anicoubs.data.db.entity.vk.builder.VKParameters
import ru.holofox.anicoubs.data.db.unitlocalized.vk.UnitSpecificVKWallMinimalEntry

interface VKWallRepository {
    val wall: LiveData<List<UnitSpecificVKWallMinimalEntry>>

    suspend fun wallGet(filter: String)
    suspend fun wallPost(parameters: VKParameters)
    suspend fun wallDelete(parameters: VKParameters)

    suspend fun wallDaoDelete(postId: Int)
}