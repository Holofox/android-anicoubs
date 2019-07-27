package ru.holofox.anicoubs.data.db.unitlocalized.vk

import androidx.room.ColumnInfo

class VKGroupsEntry (
    @ColumnInfo(name = "adminLevel")
    override val adminLevel: Int,
    @ColumnInfo(name = "id")
    override val id: Int,
    @ColumnInfo(name = "isAdmin")
    override val isAdmin: Int,
    @ColumnInfo(name = "isAdvertiser")
    override val isAdvertiser: Int,
    @ColumnInfo(name = "isClosed")
    override val isClosed: Int,
    @ColumnInfo(name = "isMember")
    override val isMember: Int,
    @ColumnInfo(name = "name")
    override val name: String,
    @ColumnInfo(name = "photo100")
    override val photo100: String,
    @ColumnInfo(name = "photo200")
    override val photo200: String,
    @ColumnInfo(name = "photo50")
    override val photo50: String,
    @ColumnInfo(name = "screenName")
    override val screenName: String,
    @ColumnInfo(name = "type")
    override val type: String
) : UnitSpecificVKGroupsEntry