package ru.holofox.anicoubs.data.network.response.coub

import com.google.gson.annotations.SerializedName
import ru.holofox.anicoubs.data.db.entity.coub.CoubEntry

data class CoubTimelineResponse(
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    val coubs: List<CoubEntry>
)