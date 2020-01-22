package ru.holofox.anicoubs.features.domain.network.data

import ru.holofox.anicoubs.features.data.network.NetworkCall

interface HolofoxNetworkDataSource {
    suspend fun checkInBlackList(channelId: Int) : NetworkCall<Boolean>
}