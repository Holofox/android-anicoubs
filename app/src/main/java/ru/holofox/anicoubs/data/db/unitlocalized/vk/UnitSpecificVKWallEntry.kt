package ru.holofox.anicoubs.data.db.unitlocalized.vk

import org.threeten.bp.LocalDateTime
import ru.holofox.anicoubs.data.db.entity.vk.wall.*

interface UnitSpecificVKWallEntry {
    val attachments: List<Attachment>?
    val canDelete: Int
    val canEdit: Int
    val canPin: Int
    val comments: Comments
    val createdBy: Int
    val date: LocalDateTime
    val fromId: Int
    val id: Int
    val isFavorite: Boolean
    val likes: Likes
    val markedAsAds: Int
    val ownerId: Int
    val postSource: PostSource
    val postType: String
    val postponedId: Int
    val reposts: Reposts
    val text: String
    val views: Views
}