package ru.holofox.anicoubs.data.db.unitlocalized.vk

import androidx.room.ColumnInfo
import org.threeten.bp.LocalDateTime
import ru.holofox.anicoubs.data.db.entity.vk.wall.*

data class VKWallEntry(
    @ColumnInfo(name = "attachments")
    override val attachments: List<Attachment>?,
    @ColumnInfo(name = "canDelete")
    override val canDelete: Int,
    @ColumnInfo(name = "canEdit")
    override val canEdit: Int,
    @ColumnInfo(name = "canPin")
    override val canPin: Int,
    @ColumnInfo(name = "comments")
    override val comments: Comments,
    @ColumnInfo(name = "createdBy")
    override val createdBy: Int,
    @ColumnInfo(name = "date")
    override val date: LocalDateTime,
    @ColumnInfo(name = "fromId")
    override val fromId: Int,
    @ColumnInfo(name = "id")
    override val id: Int,
    @ColumnInfo(name = "isFavorite")
    override val isFavorite: Boolean,
    @ColumnInfo(name = "likes")
    override val likes: Likes,
    @ColumnInfo(name = "markedAsAds")
    override val markedAsAds: Int,
    @ColumnInfo(name = "ownerId")
    override val ownerId: Int,
    @ColumnInfo(name = "postSource")
    override val postSource: PostSource,
    @ColumnInfo(name = "postType")
    override val postType: String,
    @ColumnInfo(name = "postponedId")
    override val postponedId: Int,
    @ColumnInfo(name = "reposts")
    override val reposts: Reposts,
    @ColumnInfo(name = "text")
    override val text: String,
    @ColumnInfo(name = "views")
    override val views: Views
) : UnitSpecificVKWallEntry