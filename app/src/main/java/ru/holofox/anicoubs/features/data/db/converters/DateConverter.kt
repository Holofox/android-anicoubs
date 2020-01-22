package ru.holofox.anicoubs.features.data.db.converters

import androidx.room.TypeConverter
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter

object DateConverter {

    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    @TypeConverter
    @JvmStatic
    fun toLocalDateTime(str: String?) = str?.let {
       LocalDateTime.ofInstant(Instant.parse(it), ZoneId.systemDefault())
    }

    @TypeConverter
    @JvmStatic
    fun fromLocalDate(dateTime: LocalDateTime?) = dateTime?.format(formatter)

}