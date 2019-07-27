package ru.holofox.anicoubs.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import org.threeten.bp.LocalDateTime
import ru.holofox.anicoubs.data.db.converters.DateConverter
import ru.holofox.anicoubs.data.db.entity.coub.CoubEntry
import ru.holofox.anicoubs.data.db.unitlocalized.coub.TimelineMinimalEntry

@Dao
@SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
@TypeConverters(DateConverter::class)

interface TimeLineDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: List<CoubEntry>)

    @Query("select * from coubs order by id desc")
    fun getTimeline(): LiveData<List<TimelineMinimalEntry>>

    @Query("select count(id) from coubs where date(updatedAt) >= date(:startDate)")
    fun count(startDate: LocalDateTime) : Int

    @Query("delete from coubs where date(publishedAt) < date(:firstDateToKeep)")
    fun deleteOldEntries(firstDateToKeep: LocalDateTime)
}