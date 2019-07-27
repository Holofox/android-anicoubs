package ru.holofox.anicoubs.data.repository

import androidx.lifecycle.LiveData
import ru.holofox.anicoubs.data.db.entity.vk.builder.VKParameters
import ru.holofox.anicoubs.data.db.unitlocalized.vk.UnitSpecificVKWallMinimalEntry

interface VKWallRepository {
    val wall: LiveData<List<UnitSpecificVKWallMinimalEntry>>

    suspend fun wallGet(parameters: VKParameters)
    suspend fun wallPost(parameters: VKParameters)
}