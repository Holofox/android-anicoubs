package ru.holofox.anicoubs.data.db.entity.vk.wall

import com.google.gson.annotations.SerializedName

data class Comments(
    @SerializedName("can_close")
    val canClose: Int,
    @SerializedName("can_post")
    val canPost: Int,
    val count: Int,
    @SerializedName("group_can_post")
    val groupsCanPost: Boolean
)