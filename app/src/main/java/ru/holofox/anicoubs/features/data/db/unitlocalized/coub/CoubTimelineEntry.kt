package ru.holofox.anicoubs.features.data.db.unitlocalized.coub

import androidx.room.ColumnInfo
import org.threeten.bp.LocalDateTime
import ru.holofox.anicoubs.features.data.network.api.coub.models.timeline.*
import ru.holofox.anicoubs.features.domain.db.unitlocalized.coub.UnitSpecificTimeLineEntry

data class CoubTimelineEntry(
    @ColumnInfo(name = "type")
    override val type: String,
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
    @ColumnInfo(name = "reversed")
    override val reversed: Boolean,
    @ColumnInfo(name = "originalSound")
    override val originalSound: Boolean,
    @ColumnInfo(name = "hasSound")
    override val hasSound: Boolean,
    @ColumnInfo(name = "audioVersions")
    override val audioVersions: AudioVersions?,
    @ColumnInfo(name = "imageVersions")
    override val imageVersions: Versions,
    @ColumnInfo(name = "firstFrameVersions")
    override val firstFrameVersions: Versions,
    @ColumnInfo(name = "dimensions")
    override val dimensions: Dimensions,
    @ColumnInfo(name = "fileVersions")
    override val fileVersions: FileVersions,
    @ColumnInfo(name = "audioFileUrl")
    override val audioFileUrl: String?,
    @ColumnInfo(name = "picture")
    override val picture: String,
    @ColumnInfo(name = "timelinePicture")
    override val timelinePicture: String,
    @ColumnInfo(name = "smallPicture")
    override val smallPicture: String,
    @ColumnInfo(name = "percentDone")
    override val percentDone: Double,
    @ColumnInfo(name = "tags")
    override val tags: List<Tag>,
    @ColumnInfo(name = "categories")
    override val category: List<Category>,
    @ColumnInfo(name = "recoubsCount")
    override val recoubsCount: Int,
    @ColumnInfo(name = "remixesCount")
    override val remixesCount: Int,
    @ColumnInfo(name = "likesCount")
    override val likesCount: Int,
    @ColumnInfo(name = "uploadedByIosApp")
    override val uploadedByIosApp: Boolean,
    @ColumnInfo(name = "uploadedByAndroidApp")
    override val uploadedByAndroidApp: Boolean,
    @ColumnInfo(name = "mediaBlocks")
    override val mediaBlocks: MediaBlocks,
    @ColumnInfo(name = "duration")
    override val duration: Double,
    @ColumnInfo(name = "audioCopyrightClaim")
    override val audioCopyrightClaim: String?,
    @ColumnInfo(name = "positionOnPage")
    override val positionOnPage: Int
) : UnitSpecificTimeLineEntry