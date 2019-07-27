package ru.holofox.anicoubs.data.db.converters

import androidx.room.TypeConverter
import ru.holofox.anicoubs.data.db.entity.vk.wall.*
import ru.holofox.anicoubs.internal.fromJson
import ru.holofox.anicoubs.internal.toJson

object DataVKConverter {

    @TypeConverter
    @JvmStatic
    fun fromAttachments(category: List<Attachment>?) = category.toJson()

    @TypeConverter
    @JvmStatic
    fun toAttachments(str: String?) : List<Attachment>? = str?.fromJson()

    @TypeConverter
    @JvmStatic
    fun fromComments(comments: Comments?) = comments.toJson()

    @TypeConverter
    @JvmStatic
    fun toComments(str: String?) : Comments? = str?.fromJson()

    @TypeConverter
    @JvmStatic
    fun fromLikes(likes: Likes?) = likes.toJson()

    @TypeConverter
    @JvmStatic
    fun toLikes(str: String?) : Likes? = str?.fromJson()

    @TypeConverter
    @JvmStatic
    fun fromPostSource(postSource: PostSource?) = postSource.toJson()

    @TypeConverter
    @JvmStatic
    fun toPostSource(str: String?) : PostSource? = str?.fromJson()

    @TypeConverter
    @JvmStatic
    fun fromReposts(reposts: Reposts?) = reposts.toJson()

    @TypeConverter
    @JvmStatic
    fun toReposts(str: String?) : Reposts? = str?.fromJson()

    @TypeConverter
    @JvmStatic
    fun fromViews(views: Views?) = views.toJson()

    @TypeConverter
    @JvmStatic
    fun toViews(str: String?) : Views? = str?.fromJson()

    @TypeConverter
    @JvmStatic
    fun fromImageVersions(imageVersions: List<ImageVersions>?) = imageVersions.toJson()

    @TypeConverter
    @JvmStatic
    fun toImageVersions(str: String?) : List<ImageVersions>? = str?.fromJson()

}