package ru.holofox.anicoubs.data.db.unitlocalized.vk

import org.threeten.bp.LocalDateTime
import ru.holofox.anicoubs.data.db.entity.vk.wall.Attachment
import ru.holofox.anicoubs.data.db.entity.vk.wall.Views

data class VKWallMinimalEntry(
    override val id: Int,
    override val ownerId: Int,
    override val date: LocalDateTime,
    override val text: String,
    override val views: Views?,
    override val postType: String,
    override val attachments: List<Attachment>?,
    override val photo100: String,
    override val name: String
) : UnitSpecificVKWallMinimalEntry

fun List<VKWallMinimalEntry>.asDomainModel(): List<UnitSpecificVKWallMinimalEntry> {
    return map {
        VKWallMinimalEntry(
            id = it.id,
            ownerId = it.ownerId,
            date = it.date,
            text = it.text,
            views = it.views,
            postType = it.postType,
            attachments = it.attachments,
            photo100 = it.photo100,
            name = it.name
        )
    }
}