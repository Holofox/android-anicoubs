package ru.holofox.anicoubs.features.data.db.unitlocalized.vk

import org.threeten.bp.LocalDateTime
import ru.holofox.anicoubs.features.data.network.api.vk.models.wall.VKWallAttachment
import ru.holofox.anicoubs.features.data.network.api.vk.models.wall.VKWallViews
import ru.holofox.anicoubs.features.domain.db.unitlocalized.vk.UnitSpecificVKWallMinimalEntry
import ru.holofox.anicoubs.internal.Constants.VK_WALL_URL_TEMPLATE

data class VKWallMinimalEntry(
    override val postId: Int,
    override val ownerId: Int,
    override val date: LocalDateTime,
    override val text: String,
    override val views: VKWallViews?,
    override val postType: String,
    override val attachments: List<VKWallAttachment>?,
    override val photo100: String,
    override val name: String
) : UnitSpecificVKWallMinimalEntry {

    override fun wallGroupUrl() = String.format(VK_WALL_URL_TEMPLATE, ownerId, postId)
}

fun List<VKWallMinimalEntry>.asDomainModel(): List<UnitSpecificVKWallMinimalEntry> {
    return map {
        VKWallMinimalEntry(
            postId = it.postId,
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