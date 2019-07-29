package ru.holofox.anicoubs.data.repository

import androidx.lifecycle.LiveData
import ru.holofox.anicoubs.data.db.entity.vk.builder.VKParameters
import ru.holofox.anicoubs.data.db.unitlocalized.vk.UnitSpecificVKWallMinimalEntry
import ru.holofox.anicoubs.data.network.response.VKWallPostResponse
import ru.holofox.anicoubs.internal.dto.NetworkResult

interface VKWallRepository {
    val wall: LiveData<List<UnitSpecificVKWallMinimalEntry>>
    val postWallResult : LiveData<NetworkResult<VKWallPostResponse>>

    suspend fun wallGet(parameters: VKParameters)
    suspend fun wallPost(parameters: VKParameters)
}