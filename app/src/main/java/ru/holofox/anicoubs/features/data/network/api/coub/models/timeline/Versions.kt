package ru.holofox.anicoubs.features.data.network.api.coub.models.timeline

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