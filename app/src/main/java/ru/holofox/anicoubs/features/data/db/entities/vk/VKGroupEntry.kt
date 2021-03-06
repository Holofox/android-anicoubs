package ru.holofox.anicoubs.features.data.db.entities.vk

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "vk_groups")
data class VKGroupEntry(
    @SerializedName("admin_level")
    val adminLevel: Int,
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    val groupId: Int,
    @SerializedName("is_admin")
    val isAdmin: Int,
    @SerializedName("is_advertiser")
    val isAdvertiser: Int,
    @SerializedName("is_closed")
    val isClosed: Int,
    @SerializedName("is_member")
    val isMember: Int,
    val name: String,
    @SerializedName("photo_100")
    val photo100: String,
    @SerializedName("photo_200")
    val photo200: String,
    @SerializedName("photo_50")
    val photo50: String,
    @SerializedName("screen_name")
    val screenName: String,
    val type: String
)