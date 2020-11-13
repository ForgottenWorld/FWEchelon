package it.forgottenworld.echelon.discourse.post

import it.forgottenworld.fwechelonapi.discourse.DiscoursePost

data class DiscoursePostImpl(val map: Map<String, Any?>): DiscoursePost {

    private val id = map["id"] as Int
    private val name: String = map["name"] as String
    private val username: String = map["username"] as String
    private val createdAt: String = map["created_at"] as String
    private val cooked: String = map["cooked"] as String
    private val topicId: Int = map["topic_id"] as Int

    override fun getId() = id.toLong()

    override fun getName() = name

    override fun getUsername() = username

    override fun getCreatedAt() = createdAt.toLongOrNull() ?: 0L

    override fun getContent() = cooked

    override fun getTopicId() = topicId
}