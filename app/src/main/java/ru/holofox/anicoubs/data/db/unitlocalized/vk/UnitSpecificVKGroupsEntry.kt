package ru.holofox.anicoubs.data.db.unitlocalized.vk

interface UnitSpecificVKGroupsEntry {
    val adminLevel: Int
    val id: Int
    val isAdmin: Int
    val isAdvertiser: Int
    val isClosed: Int
    val isMember: Int
    val name: String
    val photo100: String
    val photo200: String
    val photo50: String
    val screenName: String
    val type: String
}