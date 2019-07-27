package ru.holofox.anicoubs.data.network.response

import com.google.gson.annotations.SerializedName
import ru.holofox.anicoubs.data.db.entity.vk.wall.*

data class VKWallGetByIdResponse (
    val attachments:  List<Attachment>,
    @SerializedName("can_delete")
    val canDelete: Int,
    @SerializedName("can_edit")
    val canEdit: Int,
    @SerializedName("can_pin")
    val canPin: Int,
    val comments: Comments,
    @SerializedName("created_by")
    val createdBy: Int,
    val date: Int,
    @SerializedName("from_id")
    val fromId: Int,
    val id: Int,
    @SerializedName("is_favorite")
    val isFavorite: Boolean,
    val likes: Likes,
    @SerializedName("marked_as_ads")
    val markedAsAds: Int,
    @SerializedName("owner_id")
    val ownerId: Int,
    @SerializedName("post_source")
    val postSource: PostSource,
    @SerializedName("post_type")
    val postType: String,
    @SerializedName("postponed_id")
    val postponedId: Int,
    val reposts: Reposts,
    val text: String,
    val views: Views
)