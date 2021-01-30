package it.forgottenworld.echelonapi.services

import it.forgottenworld.echelonapi.discourse.DiscoursePost
import java.util.function.Consumer

interface DiscourseService {

    /**
     * Gets all posts in a topic whose notice-type is set to "custom"
     *
     * @param id the id of the topic
     * @onResponse accepts the list of found posts
     * @onError accepts a string describing the error
     * */
    fun getPostsWithCustomNoticeTypeInTopic(
        id: Int,
        onResponse: Consumer<List<DiscoursePost>>,
        onFailure: Consumer<String>
    )
}
