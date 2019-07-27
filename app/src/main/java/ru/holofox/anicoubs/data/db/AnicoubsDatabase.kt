package ru.holofox.anicoubs.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.holofox.anicoubs.data.db.converters.DataCoubConverter
import ru.holofox.anicoubs.data.db.converters.DataVKConverter
import ru.holofox.anicoubs.data.db.entity.coub.CoubEntry
import ru.holofox.anicoubs.data.db.entity.vk.GroupEntry
import ru.holofox.anicoubs.data.db.entity.vk.WallItemEntry

@Database(
    entities = [CoubEntry::class, WallItemEntry::class, GroupEntry::class],
    version = 1
)

@TypeConverters(DataCoubConverter::class, DataVKConverter::class)
abstract class AnicoubsDatabase : RoomDatabase() {
    abstract fun postFeedDao(): TimeLineDao
    abstract fun vkDao(): VKWallDao

    companion object {
        @Volatile private var instance: AnicoubsDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                    AnicoubsDatabase::class.java, "anicoubs.db")
                   // .fallbackToDestructiveMigration()
                    .build()
    }
}