package ru.holofox.anicoubs.features.domain.db.unitlocalized.coub

import org.threeten.bp.LocalDateTime
import ru.holofox.anicoubs.features.data.network.api.coub.models.timeline.*

interface UnitSpecificTimeLineEntry {
    val type: String
    val permalink: String
    val title: String
    val channel: Channel
    val createdAt: LocalDateTime
    val updatedAt: LocalDateTime
    val publishedAt: LocalDateTime
    val viewsCount: Int
    val reversed: Boolean
    val originalSound: Boolean
    val hasSound: Boolean
    val audioVersions: AudioVersions?
    val imageVersions: Versions
    val firstFrameVersions: Versions
    val dimensions: Dimensions
    val fileVersions: FileVersions
    val audioFileUrl: String?
    val picture: String
    val timelinePicture: String
    val smallPicture: String
    val percentDone: Double
    val tags: List<Tag>
    val category: List<Category>
    val recoubsCount: Int
    val remixesCount: Int
    val likesCount: Int
    val uploadedByIosApp: Boolean
    val uploadedByAndroidApp: Boolean
    val mediaBlocks: MediaBlocks
    val duration: Double
    val audioCopyrightClaim: String?
    val positionOnPage: Int
}