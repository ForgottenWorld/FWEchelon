package it.forgottenworld.echelon.discourse

import com.beust.klaxon.JsonObject
import io.github.rybalkinsd.kohttp.dsl.httpPost
import io.github.rybalkinsd.kohttp.ext.url
import it.forgottenworld.echelon.config.Config
import it.forgottenworld.echelon.manager.ForumActivationManager
import it.forgottenworld.echelon.utils.launchAsync
import java.util.*

object CodeMessageSender {

    fun sendMessage(uuid: UUID, discourseUsername: String) {
        val key = ForumActivationManager.addActivationDataForPlayer(uuid)
        val data = JsonObject(mapOf(
                "title" to "Codice di verifica",
                "raw" to "CODICE DI VERIFICA: $key",
                "target_recipients" to discourseUsername,
                "archetype" to "private_message",
        )).toJsonString()
        val apiKey = Config.apiKey
        launchAsync {
            httpPost {
                url("https://forum.forgottenworld.it/posts")
                charset("utf-8")
                header {
                    "api-key" to apiKey
                    "api-username" to "system"
                }
                body { json(data) }
            }
        }
    }
}