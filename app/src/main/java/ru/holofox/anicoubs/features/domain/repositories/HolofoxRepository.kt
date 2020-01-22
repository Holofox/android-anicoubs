package ru.holofox.anicoubs.features.domain.repositories

import ru.holofox.anicoubs.features.data.network.NetworkCall

interface HolofoxRepository {
    suspend fun checkInBlackList(channelId: Int): NetworkCall<Boolean>
}