package ru.holofox.anicoubs.features.data.network.api.vk.models.wall

import com.google.gson.annotations.SerializedName

data class VKWallImageVersions(
    val height: Int,
    val url: String,
    val width: Int,
    @SerializedName("with_padding")
    val withPadding: Int
) {
    fun dimensionRatio() = String.format("%d:%d", width, height)
}