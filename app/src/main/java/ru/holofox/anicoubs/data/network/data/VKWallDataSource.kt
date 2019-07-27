package ru.holofox.anicoubs.data.network.data

import androidx.lifecycle.LiveData
import ru.holofox.anicoubs.data.db.entity.vk.builder.VKParameters
import ru.holofox.anicoubs.data.network.response.VKWallGetResponse

interface VKWallDataSource {
    val vkWallGetResponse: LiveData<VKWallGetResponse>

    suspend fun wallGet(parameters: VKParameters)
    suspend fun wallPost(parameters: VKParameters)
}