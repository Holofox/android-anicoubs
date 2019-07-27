package ru.holofox.anicoubs.data.network.response

import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDate

@ExperimentalUnsignedTypes
data class VKProfileInfoResponse constructor(
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    val bdate: LocalDate?,
    @SerializedName("bdate_visibility")
    val bdateVisibility: UByte,
    @SerializedName("home_town")
    val homeTown: String?,
    val phone: String?,
    val relation: UByte,
    @SerializedName("screen_name")
    val screenName: String,
    val sex: UByte,
    val status: String?
)