package ru.holofox.anicoubs.features.data.db.entities.vk

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import ru.holofox.anicoubs.features.data.network.api.vk.models.wall.*

@Entity(tableName = "vk_wall")

data class VKWallItemEntry(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    val postId: Int,
    @SerializedName("from_id")
    val fromId: Int,
    @SerializedName("owner_id")
    val ownerId: Int,
    val date: Long,
    @SerializedName("postponed_id")
    val postponedId: Int,
    @SerializedName("marked_as_ads")
    val markedAsAds: Int,
    @SerializedName("post_type")
    val postType: String,
    val text: String,
    @SerializedName("can_edit")
    val canEdit: Int,
    @SerializedName("created_by")
    val createdBy: Int,
    @SerializedName("can_delete")
    val canDelete: Int,
    @SerializedName("can_pin")
    val canPin: Int,
    val attachments: List<VKWallAttachment>?,
    @SerializedName("post_source")
    val postSource: VKWallPostSource,
    val comments: VKWallComments,
    val likes: VKWallLikes,
    val reposts: VKWallReposts,
    val views: VKWallViews,
    @SerializedName("is_favorite")
    val isFavorite: Boolean
)