package ru.holofox.anicoubs.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.holofox.anicoubs.data.db.converters.TimestampConverter

import ru.holofox.anicoubs.data.db.entity.vk.GroupEntry
import ru.holofox.anicoubs.data.db.entity.vk.WallItemEntry
import ru.holofox.anicoubs.data.db.unitlocalized.vk.VKGroupsEntry
import ru.holofox.anicoubs.data.db.unitlocalized.vk.VKWallEntry
import ru.holofox.anicoubs.data.db.unitlocalized.vk.VKWallMinimalEntry

@Dao
@SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
@TypeConverters(TimestampConverter::class)

interface VKWallDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(post: List<WallItemEntry>, group: List<GroupEntry>)

    @Query("select * from vk_wall order by id desc")
    fun getWall() : LiveData<List<VKWallEntry>>

    @Query("select * from vk_wall, vk_groups where vk_wall.ownerId == -vk_groups.id") // order by id desc
    fun getWallMinimal() : LiveData<List<VKWallMinimalEntry>>

    @Query("select * from vk_groups")
    fun getGroup() : LiveData<List<VKGroupsEntry>>

    /* @Query("select date from vk_wall order by date desc limit 1")
    fun getOlder() : LocalDateTime */

    @Query("select count(id) from vk_wall where date >= :startDate")
    fun count(startDate: Long) : Int

    @Query("delete from vk_wall where date < :firstDateToKeep")
    fun deleteOldEntries(firstDateToKeep: Long)
}