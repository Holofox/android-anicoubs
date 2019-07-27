package ru.holofox.anicoubs.data.db.entity.coub.timeline

data class Dimensions(
    val big: List<Int>,
    val med: List<Int>
) {
    fun bigRatio() = String.format("%d:%d", big[0], big[1])
    fun medRatio() = String.format("%d:%d", med[0], med[1])
}

