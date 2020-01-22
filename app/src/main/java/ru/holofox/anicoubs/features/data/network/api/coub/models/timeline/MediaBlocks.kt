package ru.holofox.anicoubs.features.data.network.api.coub.models.timeline

import com.google.gson.annotations.SerializedName

data class MediaBlocks(
    @SerializedName("uploaded_raw_videos")
    val uploadedRawVideos: List<SourceFromCoub?>,
    @SerializedName("external_raw_videos")
    val externalRawVideos: List<SourceFromCoub?>,
    @SerializedName("remixed_from_coubs")
    val remixedFromCoubs: List<SourceFromCoub?>
)