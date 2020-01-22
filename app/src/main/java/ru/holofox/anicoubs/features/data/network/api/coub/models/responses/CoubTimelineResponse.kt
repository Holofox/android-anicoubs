package ru.holofox.anicoubs.features.data.network.api.coub.models.responses

import com.google.gson.annotations.SerializedName
import ru.holofox.anicoubs.features.data.db.entities.coub.CoubEntry

data class CoubTimelineResponse(
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    val coubs: List<CoubEntry>
)