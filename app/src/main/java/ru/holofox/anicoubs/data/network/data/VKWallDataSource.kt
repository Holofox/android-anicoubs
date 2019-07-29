package ru.holofox.anicoubs.data.network.data

import androidx.lifecycle.LiveData
import ru.holofox.anicoubs.data.db.entity.vk.builder.VKParameters
import ru.holofox.anicoubs.data.network.response.VKWallGetByIdResponse
import ru.holofox.anicoubs.data.network.response.VKWallGetResponse
import ru.holofox.anicoubs.data.network.response.VKWallPostResponse
import ru.holofox.anicoubs.internal.dto.NetworkResult

interface VKWallDataSource {
    val vkWallGetResponse: LiveData<VKWallGetResponse>
    val vkWallPostResponse: LiveData<NetworkResult<VKWallPostResponse>>

    suspend fun wallGet(parameters: VKParameters)
    suspend fun wallPost(parameters: VKParameters)
}