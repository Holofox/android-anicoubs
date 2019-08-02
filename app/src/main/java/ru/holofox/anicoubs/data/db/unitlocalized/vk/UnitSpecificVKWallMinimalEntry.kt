package ru.holofox.anicoubs.data.db.unitlocalized.vk

import org.threeten.bp.LocalDateTime
import ru.holofox.anicoubs.data.db.entity.vk.wall.Attachment
import ru.holofox.anicoubs.data.db.entity.vk.wall.Views

interface UnitSpecificVKWallMinimalEntry {
    val postId: Int
    val ownerId: Int
    val date: LocalDateTime
    val text: String
    val views: Views?
    val postType: String
    val attachments: List<Attachment>?
    val photo100: String
    val name: String

    override fun equals(other: Any?) : Boolean

    fun wallGroupUrl(): String
}