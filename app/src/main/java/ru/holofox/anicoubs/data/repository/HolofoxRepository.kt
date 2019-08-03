package ru.holofox.anicoubs.data.repository

import ru.holofox.anicoubs.data.network.NetworkCall

interface HolofoxRepository {
    suspend fun checkInBlackList(channelId: Int) : NetworkCall<Boolean>
}