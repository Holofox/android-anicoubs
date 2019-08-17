package ru.holofox.anicoubs.data.network.response.coub

import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime
import ru.holofox.anicoubs.data.db.entity.coub.channel.Authentications
import ru.holofox.anicoubs.data.db.entity.coub.channel.Contacts
import ru.holofox.anicoubs.data.db.entity.coub.channel.Meta
import ru.holofox.anicoubs.data.db.entity.coub.timeline.Versions

data class CoubChannelResponse(
    @SerializedName("simple_coubs_count")
    val simpleCoubsCount: Int,
    val id: Int,
    @SerializedName("user_id")
    val userId: Int,
    val permalink: String,
    val title: String,
    val description: String,
    val contacts: Contacts,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("avatar_versions")
    val avatarVersions: Versions,
    @SerializedName("followers_count")
    val followersCount: Int,
    @SerializedName("following_count")
    val followingCount: Int,
    @SerializedName("recoubs_count")
    val recoubsCount: Int,
    @SerializedName("likes_count")
    val likesCount: Int,
    @SerializedName("stories_count")
    val storiesCount: Int,
    val authentications: List<Authentications>,
    @SerializedName("background_image")
    val backgroundImage: String,
    @SerializedName("timelime_banner_image")
    val timelineBannerImage: String,
    val meta: Meta,
    @SerializedName("views_count")
    val viewsCount: Int
)