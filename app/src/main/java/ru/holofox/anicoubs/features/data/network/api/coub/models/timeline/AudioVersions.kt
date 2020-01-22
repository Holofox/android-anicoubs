package ru.holofox.anicoubs.features.data.network.api.coub.models.timeline

data class AudioVersions(
    val template: String,
    val versions: List<String>,
    val chunks: Chunks
)