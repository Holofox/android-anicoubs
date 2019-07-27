package ru.holofox.anicoubs.data.provider

interface ConnectivityProvider {
    fun isOnline(): Boolean
}