package it.forgottenworld.echelon.discourse

import it.forgottenworld.echelonapi.discourse.DiscoursePost
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonPrimitive
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException


internal data class DiscoursePostImpl(val map: JsonObject) : DiscoursePost {

    override val id = map["id"]?.jsonPrimitive?.int ?: -1

    override val name = map["name"]?.jsonPrimitive?.content ?: ""

    override val username = map["username"]?.jsonPrimitive?.content ?: ""

    override val createdAt = map["created_at"]?.jsonPrimitive?.content?.let {
        try {
            LocalDateTime.parse(it, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"))
        } catch (_: DateTimeParseException) {
            null
        }
    }

    override val content = map["cooked"]?.jsonPrimitive?.content ?: ""

    override val topicId = map["topic_id"]?.jsonPrimitive?.int ?: -1

    override fun toString() = "id: $id\nname: $name\nusername: $username\ncreatedAt: $createdAt\ncontent: $content\ntopicId: $topicId"
}