package ru.holofox.anicoubs.data.db.entity.coub.timeline

data class Chunks(
    val template: String,
    val versions: List<String>,
    val chunks: List<Int>
)