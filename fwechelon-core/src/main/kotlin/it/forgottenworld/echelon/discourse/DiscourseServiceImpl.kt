package it.forgottenworld.echelon.discourse


import io.github.rybalkinsd.kohttp.dsl.httpGet
import io.github.rybalkinsd.kohttp.ext.asString
import io.github.rybalkinsd.kohttp.ext.url
import it.forgottenworld.echelon.config.Config
import it.forgottenworld.echelon.utils.asyncDispatcher
import it.forgottenworld.echelon.utils.launch
import it.forgottenworld.echelonapi.discourse.DiscoursePost
import it.forgottenworld.echelonapi.discourse.DiscourseService
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.function.Consumer

@KoinApiExtension
internal class DiscourseServiceImpl : DiscourseService, KoinComponent {

    private val config by inject<Config>()

    override fun getPostsWithCustomNoticeTypeInTopic(
        id: Int,
        onResponse: Consumer<List<DiscoursePost>>,
        onFailure: Consumer<String>
    ) {

        val apiKey = config.apiKey

        launch {

            withContext(asyncDispatcher) {

                httpGet {
                    url("${config.discourseUrl}/t/$id.json")

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