package ru.holofox.anicoubs.data.db.entity.vk.builder

class VKParameters(b: Builder) {

    class Builder {

        var args: MutableMap<String,String> = LinkedHashMap()

        fun name(name: String) = apply { this.args["name"] = name }
        fun description(description: String) = apply { this.args["description"] = description}
        fun isPrivate(isPrivate: Boolean) = apply { this.args["is_private"] = if (isPrivate) "1" else "0" }
        fun wallPost(wallPost: Boolean) = apply { this.args["wallpost"] = if (wallPost) "1" else "0" }
        fun link(link: String) = apply { this.args["link"] = link }
        fun groupId(groupId: Int) = apply { this.args["group_id"] = groupId.toString() }
        fun albumId(albumId: Int) = apply { this.args["album_id"] = albumId.toString() }
        fun privacyView(privacyView: String) = apply { this.args["privacy_view"] = privacyView }
        fun privacyComment(privacyComment: String) = apply { this.args["privacy_comment"] = privacyComment }
        fun noComments(noComments: Boolean) = apply { this.args["no_comments"] = if (noComments) "1" else "0" }

        fun ownerId(ownerId: Int) = apply { this.args["owner_id"] = ownerId.toString() }
        fun postId(postId: Int) = apply { this.args["post_id"] = postId.toString() }
        fun domain(domain: String) = apply { this.args["domain"] = domain }
        fun offset(offset: Int) = apply { this.args["offset"] = offset.toString() }
        fun count(count: Int) = apply { this.args["count"] = count.toString() }
        fun filter(filter: String) = apply { this.args["filter"] = filter }
        fun extended(extended: Boolean) = apply { this.args["extended"] = if (extended) "1" else "0"  }
        fun fields(fields: String) = apply { this.args["fields"] = fields}
        fun build() = VKParameters(this)
    }

    val args: Map<String,String>

    init {
        this.args = b.args
    }

}