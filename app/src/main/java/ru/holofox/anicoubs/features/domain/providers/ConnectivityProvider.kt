package ru.holofox.anicoubs.features.domain.providers

interface ConnectivityProvider {
    fun isOnline(): Boolean
    fun getNetworkErrorMessage() : String
}