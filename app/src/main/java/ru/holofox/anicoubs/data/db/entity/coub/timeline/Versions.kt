package ru.holofox.anicoubs.data.db.entity.coub.timeline

data class Versions(
    val template: String,
    val versions: List<String>?
) {
    fun getImageUrl(filter: String) : String {
        versions?.let {
            val id = it.indexOf(filter)
            return template.replace("%{version}", it[id])
        }
        return template
    }
}