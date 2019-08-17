package ru.holofox.anicoubs.data.network.data

import com.vk.api.sdk.internal.ApiCommand
import ru.holofox.anicoubs.data.network.NetworkCall

interface VKNetworkDataSource {
    suspend fun <T>perform(apiCommand: ApiCommand<T>) : NetworkCall<T>
}