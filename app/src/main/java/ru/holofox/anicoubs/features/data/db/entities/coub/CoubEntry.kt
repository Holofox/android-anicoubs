package ru.holofox.anicoubs.features.data.db.entities.coub

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import ru.holofox.anicoubs.features.data.network.api.coub.models.timeline.*

@Entity(tableName = "coubs", indices = [Index(value = ["publishedAt"], unique = true)])
data class CoubEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val type: String,
    val permalink: String,
    val title: String,
    @SerializedName("channel_id")
    val channelId: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("published_at")
    val publishedAt: String,
    @SerializedName("views_count")
    val viewsCount: Int,
    val reversed: Boolean,
    @SerializedName("original_sound")
    val originalSound: Boolean,
    @SerializedName("has_sound")
    val hasSound: Boolean,
    @SerializedName("file_versions")
    val fileVersions: FileVersions,
    @SerializedName("audio_versions")
    val audioVersions: AudioVersions?,
    @SerializedName("image_versions")
    val imageVersions: Versions,
    @SerializedName("first_frame_versions")
    val firstFrameVersions: Versions,
    val dimensions: Dimensions,
    @SerializedName("audio_file_url")
    val audioFileUrl: String?,
    val channel: Channel,
    val picture: String,
    @SerializedName("timeline_picture")
    val timelinePicture: String,
    @SerializedName("small_picture")
    val smallPicture: String,
    @SerializedName("percent_done")
    val percentDone: Double,
    val tags: List<Tag>,
    val categories: List<Category>,
    @SerializedName("recoubs_count")
    val recoubsCount: Int,
    @SerializedName("remixes_count")
    val remixesCount: Int,
    @SerializedName("likes_count")
    val likesCount: Int,
    @SerializedName("uploaded_by_ios_app")
    val uploadedByIosApp: Boolean,
    @SerializedName("uploaded_by_android_app")
    val uploadedByAndroidApp: Boolean,
    @SerializedName("media_blocks")
    val mediaBlocks: MediaBlocks,
    @SerializedName("duration")
    val duration: Double,
    @SerializedName("audio_copyright_claim")
    val audioCopyrightClaim: String?,
    @SerializedName("position_on_page")
    val positionOnPage: Int
)