package ru.holofox.anicoubs.data.provider

interface ConnectivityProvider {
    fun isOnline(): Boolean
    fun getNetworkErrorMessage() : String
}