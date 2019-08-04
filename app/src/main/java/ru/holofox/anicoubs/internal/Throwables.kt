package ru.holofox.anicoubs.internal

class VKWallRepositoryError(cause: Throwable) : Throwable(cause.message, cause)
class VKVideoRepositoryError(cause: Throwable) : Throwable(cause.message, cause)
class CoubRepositoryError(cause: Throwable) : Throwable(cause.message, cause)
class HolofoxRepositoryError(cause: Throwable) : Throwable(cause.message, cause)