/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.holofox.anicoubs.features.data.network

import android.os.Handler
import android.os.Looper
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Call for our network library used to observe results
 */
class NetworkCall<T> {
    var result: NetworkResult<T>? = null
    val listeners = mutableListOf<NetworkListener<T>>()

    private val uiHandler = Handler(Looper.getMainLooper())

    /**
     * Register a result listener to observe this callback.
     *
     * Errors will be passed to this callback as an instance of [NetworkError] and successful
     * calls will be passed to this callback as an instance of [NetworkSuccess].
     *
     * @param listener the callback to call when this request completes
     */
    fun addOnResultListener(listener: (NetworkResult<T>) -> Unit) {
        trySendResult(listener)
        listeners += listener
    }

    /**
     * The library will call this when a result is available
     */
    fun onSuccess(data: T) {
        result = NetworkSuccess(data)
        sendResultToAllListeners()
    }

    /**
     * The library will call this when an error happens
     */
    fun onError(throwable: Throwable) {
        result = NetworkError(throwable)
        sendResultToAllListeners()
    }

    /**
     * Broadcast the current result (success or error) to all registered listeners.
     */
    private fun sendResultToAllListeners() = listeners.map { trySendResult(it) }

    /**
     * Send the current result to a specific listener.
     *
     * If no result is set (null), this method will do nothing.
     */
    private fun trySendResult(listener: NetworkListener<T>) {
        val thisResult = result
        thisResult?.let {
            uiHandler.post {
                listener(thisResult)
            }
        }
    }
}

/**
 * Network result class that represents both success and errors
 */
sealed class NetworkResult<T>

/**
 * Passed to listener when the network request was successful
 *
 * @param data the result
 */
class NetworkSuccess<T>(val data: T) : NetworkResult<T>()

/**
 * Passed to listener when the network failed
 *
 * @param error the exception that caused this error
 */
class NetworkError<T>(val error: Throwable) : NetworkResult<T>()

/**
 * Listener "type" for observing a [NetworkCall]
 */
typealias NetworkListener<T> = (NetworkResult<T>) -> Unit

/**
 * Throwable to use in network errors.
 */
class NetworkException(message: String) : Throwable(message)

/**
 * Suspend function to use callback-based [NetworkCall] in coroutines
 *
 * @return network result after completion
 * @throws Throwable original exception from library if network request fails
 */
suspend fun <T> NetworkCall<T>.await(): T {
    return suspendCoroutine { continuation ->
        addOnResultListener { result ->
            when (result) {
                is NetworkSuccess<T> -> continuation.resume(result.data)
                is NetworkError -> continuation.resumeWithException(result.error)
            }
        }
    }
}