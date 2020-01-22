package ru.holofox.anicoubs.features.data.db.unitlocalized.coub

import androidx.room.ColumnInfo
import org.threeten.bp.LocalDateTime
import ru.holofox.anicoubs.features.data.network.api.coub.models.timeline.Channel
import ru.holofox.anicoubs.features.data.network.api.coub.models.timeline.Dimensions
import ru.holofox.anicoubs.features.data.network.api.coub.models.timeline.Tag
import ru.holofox.anicoubs.features.data.network.api.coub.models.timeline.Versions
import ru.holofox.anicoubs.features.domain.db.unitlocalized.coub.UnitSpecificTimelineMinimalEntry

data class TimelineMinimalEntry(
    @ColumnInfo(name = "permalink")
    override val permalink: String,
    @ColumnInfo(name = "title")
    override val title: String,
    @ColumnInfo(name = "channel")
    override val channel: Channel,
    @ColumnInfo(name = "createdAt")
    override val createdAt: LocalDateTime,
    @ColumnInfo(name = "updatedAt")
    override val updatedAt: LocalDateTime,
    @ColumnInfo(name = "publishedAt")
    override val publishedAt: LocalDateTime,
    @ColumnInfo(name = "viewsCount")
    override val viewsCount: Int,
    @ColumnInfo(name = "firstFrameVersions")
    override val firstFrameVersions: Versions,
    @ColumnInfo(name = "dimensions")
    override val dimensions: Dimensions,
    @ColumnInfo(name = "tags")
    override val tags: List<Tag>,
    @ColumnInfo(name = "recoubsCount")
    override val recoubsCount: Int,
    @ColumnInfo(name = "likesCount")
    override val likesCount: Int
) : UnitSpecificTimelineMinimalEntry

fun List<TimelineMinimalEntry>.asDomainModel(): List<UnitSpecificTimelineMinimalEntry> {
    return map {
        TimelineMinimalEntry(
            permalink = it.permalink,
            title = it.title,
            createdAt = it.createdAt,
            updatedAt = it.updatedAt,
            publishedAt = it.publishedAt,
            viewsCount = it.viewsCount,
            firstFrameVersions = it.firstFrameVersions,
            dimensions = it.dimensions,
            channel = it.channel,
            tags = it.tags,
            recoubsCount = it.recoubsCount,
            likesCount = it.likesCount
        )
    }
}