package it.forgottenworld.echelon.services

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import io.github.rybalkinsd.kohttp.dsl.httpGet
import io.github.rybalkinsd.kohttp.ext.asString
import io.github.rybalkinsd.kohttp.ext.url
import it.forgottenworld.echelon.config.Config
import it.forgottenworld.echelon.discourse.post.DiscoursePostImpl
import it.forgottenworld.echelon.utils.async
import it.forgottenworld.echelon.utils.launch
import it.forgottenworld.fwechelonapi.discourse.DiscoursePost
import it.forgottenworld.fwechelonapi.services.DiscourseService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.bukkit.Bukkit
import java.util.function.Consumer

object DiscourseService : DiscourseService {

    override fun getPostsWithCustomNoticeTypeInTopic(
            id: Int,
            onResponse: Consumer<List<DiscoursePost>>?,
            onFailure: Consumer<String>?
    ) {

        val apiKey = Config.apiKey

        launch {
            withContext(Dispatchers.async) {
                httpGet {
                    url("https://forum.forgottenworld.it/t/$id.json")

                    header {
                        "api-key" to apiKey
                    }
                }
            }.use { resp ->

                if (!resp.isSuccessful) {
                    onFailure?.accept("Response Code: ${resp.code()}")
                    return@use
                }

                val json = resp.asString() ?: run {
                    onFailure?.accept("Invalid response")
                    return@use
                }

                Bukkit.getLogger().info(json)

                val parser = Parser.default()
                val posts = (parser.parse(StringBuilder(json)) as? JsonObject)
                        ?.obj("post_stream")
                        ?.array<JsonObject>("posts") ?: run {
                    onFailure?.accept("Parsing failed")
                    return@use
                }

                val filteredPosts = posts.filter { it.string("notice_type") == "custom" }

                onResponse?.accept(filteredPosts.map(::DiscoursePostImpl))

            }
        }
    }
}