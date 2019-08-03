package ru.holofox.anicoubs.internal

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*
import java.util.*

fun <T> lazyDeferred(block: suspend CoroutineScope.() -> T): Lazy<Deferred<T>> {
    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }
}

inline fun <reified T> String.fromJson(): T {
    val notesType = object : TypeToken<T>() {}.type
    return Gson().fromJson(this, notesType)
}

inline fun <reified T> T.toJson(): String {
    return Gson().toJson(this)
}