package ru.holofox.anicoubs.features.domain.db.unitlocalized.coub

import org.threeten.bp.LocalDateTime
import ru.holofox.anicoubs.features.data.network.api.coub.models.timeline.Channel
import ru.holofox.anicoubs.features.data.network.api.coub.models.timeline.Dimensions
import ru.holofox.anicoubs.features.data.network.api.coub.models.timeline.Tag
import ru.holofox.anicoubs.features.data.network.api.coub.models.timeline.Versions

interface UnitSpecificTimelineMinimalEntry {
    val permalink: String
    val title: String
    val channel: Channel
    val createdAt: LocalDateTime
    val updatedAt: LocalDateTime
    val publishedAt: LocalDateTime
    val viewsCount: Int
    val firstFrameVersions: Versions
    val dimensions: Dimensions
    val tags: List<Tag>
    val recoubsCount: Int
    val likesCount: Int

    override fun equals(other: Any?): Boolean
}