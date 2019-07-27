package ru.holofox.anicoubs.internal.dto

import ru.holofox.anicoubs.internal.enums.UnitResultType

data class NetworkResult<out T>(
    var resultType: UnitResultType,
    val data: T? = null,
    val message: String? = null
) {
    companion object {
        fun <T> success(data: T?): NetworkResult<T> {
            return NetworkResult(UnitResultType.SUCCESS, data)
        }

        fun <T> empty(data: T?): NetworkResult<T> {
            return NetworkResult(UnitResultType.EMPTY_DATA,  data)
        }

        fun <T> error(message: String): NetworkResult<T> {
            return NetworkResult(UnitResultType.ERROR,  message = message)
        }
    }
}