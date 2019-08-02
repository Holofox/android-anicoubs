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

    @Query("select * from vk_wall order by postId desc")
    fun getWall() : LiveData<List<VKWallEntry>>

    @Query("select * from vk_wall, vk_groups where vk_wall.ownerId == -vk_groups.groupId")
    fun getWallMinimal() : LiveData<List<VKWallMinimalEntry>>

    @Query("select * from vk_groups")
    fun getGroup() : LiveData<List<VKGroupsEntry>>

    @Transaction
    fun update(post: List<WallItemEntry>, group: List<GroupEntry>) {
        deleteAll()
        insert(post, group)
    }

    @Query("select count(postId) from vk_wall where date >= :startDate")
    fun count(startDate: Long) : Int

    @Query("delete from vk_wall where postId = :postId")
    fun delete(postId: Int)

    @Query("delete from vk_wall where date < :firstDateToKeep")
    fun deleteOldEntries(firstDateToKeep: Long)

    @Query("delete from vk_wall")
    fun deleteAll()
}