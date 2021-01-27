package it.forgottenworld.echelon.services


import io.github.rybalkinsd.kohttp.dsl.httpGet
import io.github.rybalkinsd.kohttp.ext.asString
import io.github.rybalkinsd.kohttp.ext.url
import it.forgottenworld.echelon.config.Config
import it.forgottenworld.echelon.discourse.post.DiscoursePostImpl
import it.forgottenworld.echelon.utils.async
import it.forgottenworld.echelon.utils.launch
import it.forgottenworld.echelonapi.discourse.DiscoursePost
import it.forgottenworld.echelonapi.services.DiscourseService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.util.function.Consumer

class DiscourseServiceImpl : DiscourseService {

    override fun getPostsWithCustomNoticeTypeInTopic(
            id: Int,
            onResponse: Consumer<List<DiscoursePost>>,
            onFailure: Consumer<String>
    ) {

        val apiKey = Config.apiKey

        launch {

            withContext(Dispatchers.async) {

                httpGet {
                    url("${Config.discourseUrl}/t/$id.json")

                    header {
                        "api-key" to apiKey
                    }
                }

            }.use { resp ->

                if (!resp.isSuccessful) {
                    onFailure.accept("Response Code: ${resp.code()}")
                    return@use
                }

                val json = resp.asString() ?: run {
                    onFailure.accept("Invalid response")
                    return@use
                }

                try {

                    val posts = Json.parseToJsonElement(json)
                             .jsonObject["post_stream"]
                             ?.jsonObject?.get("posts")
                             ?.jsonArray ?: run {
                        onFailure.accept("Parsing failed")
                        return@use
                    }

                    val filteredPosts = posts
                            .filter {
                                it.jsonObject["notice"]
                                        ?.jsonObject
                                        ?.get("type")
                                        ?.jsonPrimitive
                                        ?.content == "custom"
                            }.map { it.jsonObject }

                    onResponse.accept(filteredPosts.map(::DiscoursePostImpl))

                } catch (e: SerializationException) {
                    onFailure.accept("Parsing failed")
                    return@use
                }

            }
        }
    }
}