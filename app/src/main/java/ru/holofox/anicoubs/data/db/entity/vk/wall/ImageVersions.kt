package ru.holofox.anicoubs.data.db.entity.vk.wall

import com.google.gson.annotations.SerializedName

data class ImageVersions(
    val height: Int,
    val url: String,
    val width: Int,
    @SerializedName("with_padding")
    val withPadding: Int
) {
    fun dimensionRatio() = String.format("%d:%d", width, height)
}