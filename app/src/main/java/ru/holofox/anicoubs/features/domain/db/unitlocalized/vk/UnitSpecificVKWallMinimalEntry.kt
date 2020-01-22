package ru.holofox.anicoubs.features.domain.db.unitlocalized.vk

import org.threeten.bp.LocalDateTime
import ru.holofox.anicoubs.features.data.network.api.vk.models.wall.VKWallAttachment
import ru.holofox.anicoubs.features.data.network.api.vk.models.wall.VKWallViews

interface UnitSpecificVKWallMinimalEntry {
    val postId: Int
    val ownerId: Int
    val date: LocalDateTime
    val text: String
    val views: VKWallViews?
    val postType: String
    val attachments: List<VKWallAttachment>?
    val photo100: String
    val name: String

    override fun equals(other: Any?): Boolean

    fun wallGroupUrl(): String
}