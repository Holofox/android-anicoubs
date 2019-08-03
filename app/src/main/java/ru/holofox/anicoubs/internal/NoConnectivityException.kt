package ru.holofox.anicoubs.internal

import java.io.IOException

class NoConnectivityException : IOException() {
    override val message: String?
        get() =  "No internet connection!"
}