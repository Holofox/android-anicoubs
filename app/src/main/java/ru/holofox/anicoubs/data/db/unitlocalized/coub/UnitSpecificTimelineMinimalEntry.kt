package ru.holofox.anicoubs.data.db.unitlocalized.coub

import org.threeten.bp.LocalDateTime
import ru.holofox.anicoubs.data.db.entity.coub.timeline.Channel
import ru.holofox.anicoubs.data.db.entity.coub.timeline.Dimensions
import ru.holofox.anicoubs.data.db.entity.coub.timeline.Tag
import ru.holofox.anicoubs.data.db.entity.coub.timeline.Versions

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

    override fun equals(other: Any?) : Boolean
}