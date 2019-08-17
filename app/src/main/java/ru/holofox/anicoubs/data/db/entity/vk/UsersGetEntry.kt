package ru.holofox.anicoubs.data.db.entity.vk

import com.google.gson.annotations.SerializedName

data class UsersGetEntry(
    val id: Long,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    val bdate: Long,
    @SerializedName("bdate_visibility")
    val bdateVisibility: Byte,
    @SerializedName("home_town")
    val homeTown: String?,
    val phone: String?,
    val relation: Byte,
    @SerializedName("screen_name")
    val screenName: String,
    val sex: Byte,
    val status: String?
)