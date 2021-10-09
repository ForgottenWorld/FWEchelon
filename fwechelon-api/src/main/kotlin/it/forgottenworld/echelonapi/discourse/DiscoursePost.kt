package it.forgottenworld.echelonapi.discourse

import java.time.LocalDateTime

interface DiscoursePost {

    val id: Int

    val name: String

    val username: String

    val createdAt: LocalDateTime?

    val content: String

    val topicId: Int
}
