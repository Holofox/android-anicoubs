package ru.holofox.anicoubs.features.data.db.unitlocalized.vk

import androidx.room.ColumnInfo
import org.threeten.bp.LocalDateTime
import ru.holofox.anicoubs.features.data.network.api.vk.models.wall.*
import ru.holofox.anicoubs.features.domain.db.unitlocalized.vk.UnitSpecificVKWallEntry

data class VKWallEntry(
    @ColumnInfo(name = "attachments")
    override val attachments: List<VKWallAttachment>?,
    @ColumnInfo(name = "canDelete")
    override val canDelete: Int,
    @ColumnInfo(name = "canEdit")
    override val canEdit: Int,
    @ColumnInfo(name = "canPin")
    override val canPin: Int,
    @ColumnInfo(name = "comments")
    override val comments: VKWallComments,
    @ColumnInfo(name = "createdBy")
    override val createdBy: Int,
    @ColumnInfo(name = "date")
    override val date: LocalDateTime,
    @ColumnInfo(name = "fromId")
    override val fromId: Int,
    @ColumnInfo(name = "postId")
    override val id: Int,
    @ColumnInfo(name = "isFavorite")
    override val isFavorite: Boolean,
    @ColumnInfo(name = "likes")
    override val likes: VKWallLikes,
    @ColumnInfo(name = "markedAsAds")
    override val markedAsAds: Int,
    @ColumnInfo(name = "ownerId")
    override val ownerId: Int,
    @ColumnInfo(name = "postSource")
    override val postSource: VKWallPostSource,
    @ColumnInfo(name = "postType")
    override val postType: String,
    @ColumnInfo(name = "reposts")
    override val reposts: VKWallReposts,
    @ColumnInfo(name = "text")
    override val text: String,
    @ColumnInfo(name = "views")
    override val views: VKWallViews
) : UnitSpecificVKWallEntry