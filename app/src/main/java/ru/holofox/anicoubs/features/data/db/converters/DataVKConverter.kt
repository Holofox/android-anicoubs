package ru.holofox.anicoubs.features.data.db.converters

import androidx.room.TypeConverter
import ru.holofox.anicoubs.features.data.network.api.vk.models.wall.*
import ru.holofox.anicoubs.internal.fromJson
import ru.holofox.anicoubs.internal.toJson

object DataVKConverter {

    @TypeConverter
    @JvmStatic
    fun fromAttachments(category: List<VKWallAttachment>?) = category.toJson()

    @TypeConverter
    @JvmStatic
    fun toAttachments(str: String?): List<VKWallAttachment>? = str?.fromJson()

    @TypeConverter
    @JvmStatic
    fun fromComments(comments: VKWallComments?) = comments.toJson()

    @TypeConverter
    @JvmStatic
    fun toComments(str: String?): VKWallComments? = str?.fromJson()

    @TypeConverter
    @JvmStatic
    fun fromLikes(likes: VKWallLikes?) = likes.toJson()

    @TypeConverter
    @JvmStatic
    fun toLikes(str: String?): VKWallLikes? = str?.fromJson()

    @TypeConverter
    @JvmStatic
    fun fromPostSource(postSource: VKWallPostSource?) = postSource.toJson()

    @TypeConverter
    @JvmStatic
    fun toPostSource(str: String?): VKWallPostSource? = str?.fromJson()

    @TypeConverter
    @JvmStatic
    fun fromReposts(reposts: VKWallReposts?) = reposts.toJson()

    @TypeConverter
    @JvmStatic
    fun toReposts(str: String?): VKWallReposts? = str?.fromJson()

    @TypeConverter
    @JvmStatic
    fun fromViews(views: VKWallViews?) = views.toJson()

    @TypeConverter
    @JvmStatic
    fun toViews(str: String?): VKWallViews? = str?.fromJson()

    @TypeConverter
    @JvmStatic
    fun fromImageVersions(imageVersions: List<VKWallImageVersions>?) = imageVersions.toJson()

    @TypeConverter
    @JvmStatic
    fun toImageVersions(str: String?): List<VKWallImageVersions>? = str?.fromJson()

}