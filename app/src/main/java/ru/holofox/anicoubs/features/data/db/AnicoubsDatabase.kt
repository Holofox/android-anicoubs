package ru.holofox.anicoubs.features.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.holofox.anicoubs.features.data.db.converters.DataCoubConverter
import ru.holofox.anicoubs.features.data.db.converters.DataVKConverter
import ru.holofox.anicoubs.features.data.db.entities.coub.CoubEntry
import ru.holofox.anicoubs.features.data.db.entities.vk.VKGroupEntry
import ru.holofox.anicoubs.features.data.db.entities.vk.VKWallItemEntry
import ru.holofox.anicoubs.features.domain.db.TimeLineDao
import ru.holofox.anicoubs.features.domain.db.VKWallDao

@Database(
    entities = [CoubEntry::class, VKWallItemEntry::class, VKGroupEntry::class],
    version = 1
)

@TypeConverters(DataCoubConverter::class, DataVKConverter::class)
abstract class AnicoubsDatabase : RoomDatabase() {
    abstract fun postFeedDao(): TimeLineDao
    abstract fun vkDao(): VKWallDao

    companion object {
        @Volatile
        private var instance: AnicoubsDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AnicoubsDatabase::class.java, "anicoubs.db"
            )
                // .fallbackToDestructiveMigration()
                .build()
    }
}