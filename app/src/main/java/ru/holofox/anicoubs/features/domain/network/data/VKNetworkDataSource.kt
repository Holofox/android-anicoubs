package ru.holofox.anicoubs.features.domain.network.data

import com.vk.api.sdk.internal.ApiCommand
import ru.holofox.anicoubs.features.data.network.NetworkCall

interface VKNetworkDataSource {
    suspend fun <T>perform(apiCommand: ApiCommand<T>) : NetworkCall<T>
}