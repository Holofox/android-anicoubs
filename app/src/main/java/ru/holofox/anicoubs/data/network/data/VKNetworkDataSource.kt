package ru.holofox.anicoubs.data.network.data

import ru.holofox.anicoubs.data.db.entity.vk.builder.VKParameters
import ru.holofox.anicoubs.data.network.NetworkCall
import ru.holofox.anicoubs.data.network.response.vk.VKVideoSaveResponse
import ru.holofox.anicoubs.data.network.response.vk.VKWallGetResponse
import ru.holofox.anicoubs.data.network.response.vk.VKWallPostResponse

interface VKNetworkDataSource {
    // Wall
    suspend fun wallGet(parameters: VKParameters) : NetworkCall<VKWallGetResponse>
    suspend fun wallPost(parameters: VKParameters) : NetworkCall<VKWallPostResponse>
    suspend fun wallDelete(parameters: VKParameters) : NetworkCall<Boolean>

    // Video
    suspend fun videoSave(parameters: VKParameters) : NetworkCall<VKVideoSaveResponse>
    suspend fun videoDelete(parameters: VKParameters) : NetworkCall<Boolean>

    // Utils
    suspend fun utilsGetServerTime() : NetworkCall<Long>
}