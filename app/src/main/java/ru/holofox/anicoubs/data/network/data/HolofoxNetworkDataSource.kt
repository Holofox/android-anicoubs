package ru.holofox.anicoubs.data.network.data

import ru.holofox.anicoubs.data.network.NetworkCall

interface HolofoxNetworkDataSource {
    suspend fun checkInBlackList(channelId: Int) : NetworkCall<Boolean>
}