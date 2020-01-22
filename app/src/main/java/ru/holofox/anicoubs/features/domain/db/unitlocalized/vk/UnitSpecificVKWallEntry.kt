package ru.holofox.anicoubs.features.domain.db.unitlocalized.vk

import org.threeten.bp.LocalDateTime
import ru.holofox.anicoubs.features.data.network.api.vk.models.wall.*

interface UnitSpecificVKWallEntry {
    val attachments: List<VKWallAttachment>?
    val canDelete: Int
    val canEdit: Int
    val canPin: Int
    val comments: VKWallComments
    val createdBy: Int
    val date: LocalDateTime
    val fromId: Int
    val id: Int
    val isFavorite: Boolean
    val likes: VKWallLikes
    val markedAsAds: Int
    val ownerId: Int
    val postSource: VKWallPostSource
    val postType: String
    val postponedId: Int
    val reposts: VKWallReposts
    val text: String
    val views: VKWallViews
}