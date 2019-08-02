package ru.holofox.anicoubs.data.repository

import androidx.lifecycle.LiveData
import ru.holofox.anicoubs.data.db.unitlocalized.vk.UnitSpecificVKWallMinimalEntry

interface VKWallRepository {
    val wall: LiveData<List<UnitSpecificVKWallMinimalEntry>>

    suspend fun wallGet(filter: String)
    suspend fun wallPost(item: UnitSpecificVKWallMinimalEntry)
    suspend fun wallDelete(item: UnitSpecificVKWallMinimalEntry)
}