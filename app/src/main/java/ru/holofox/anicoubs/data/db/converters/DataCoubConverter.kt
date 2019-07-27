package ru.holofox.anicoubs.data.db.converters

import androidx.room.TypeConverter

import ru.holofox.anicoubs.data.db.entity.coub.timeline.*
import ru.holofox.anicoubs.internal.fromJson
import ru.holofox.anicoubs.internal.toJson

object DataCoubConverter {

    @TypeConverter
    @JvmStatic
    fun fromDimensions(dimensions: Dimensions?) = dimensions.toJson()

    @TypeConverter
    @JvmStatic
    fun toDimensions(str: String?) : Dimensions? = str?.fromJson()

    @TypeConverter
    @JvmStatic
    fun fromFileVersions(fileVersions: FileVersions?) = fileVersions.toJson()

    @TypeConverter
    @JvmStatic
    fun toFileVersions(str: String?) : FileVersions? = str?.fromJson()

    @TypeConverter
    @JvmStatic
    fun fromAudioVersions(audioVersions: AudioVersions?) = audioVersions.toJson()

    @TypeConverter
    @JvmStatic
    fun toAudioVersions(str: String?) : AudioVersions? = str?.fromJson()

    @TypeConverter
    @JvmStatic
    fun fromVersions(versions: Versions?) = versions.toJson()

    @TypeConverter
    @JvmStatic
    fun toVersions(str: String?) : Versions? = str?.fromJson()

    @TypeConverter
    @JvmStatic
    fun fromChannel(channel: Channel?) = channel.toJson()

    @TypeConverter
    @JvmStatic
    fun toChannel(str: String?) : Channel? = str?.fromJson()

    @TypeConverter
    @JvmStatic
    fun fromMediaBlocks(mediaBlocks: MediaBlocks?) = mediaBlocks.toJson()

    @TypeConverter
    @JvmStatic
    fun toMediaBlocks(str: String?) : MediaBlocks? = str?.fromJson()

    @TypeConverter
    @JvmStatic
    fun fromTag(tag: List<Tag>?) = tag.toJson()

    @TypeConverter
    @JvmStatic
    fun toTag(str: String?) : List<Tag>? = str?.fromJson()

    @TypeConverter
    @JvmStatic
    fun fromCategory(category: List<Category>?) = category.toJson()

    @TypeConverter
    @JvmStatic
    fun toCategory(str: String?) : List<Category>? = str?.fromJson()

}