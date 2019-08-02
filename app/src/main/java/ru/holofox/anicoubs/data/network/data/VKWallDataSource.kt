package ru.holofox.anicoubs.data.network.data

import ru.holofox.anicoubs.data.db.entity.vk.builder.VKParameters
import ru.holofox.anicoubs.data.network.NetworkCall
import ru.holofox.anicoubs.data.network.response.VKWallGetResponse
import ru.holofox.anicoubs.data.network.response.VKWallPostResponse

interface VKWallDataSource {
    suspend fun wallGet(parameters: VKParameters) : NetworkCall<VKWallGetResponse>
    suspend fun wallPost(parameters: VKParameters) : NetworkCall<VKWallPostResponse>
    suspend fun wallDelete(parameters: VKParameters) : NetworkCall<Boolean>
}