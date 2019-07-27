package ru.holofox.anicoubs.data.db.entity.coub.timeline

data class AudioVersions(
    val template: String,
    val versions: List<String>,
    val chunks: Chunks
)