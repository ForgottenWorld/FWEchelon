package it.forgottenworld.echelon.discourse

import io.github.rybalkinsd.kohttp.dsl.httpPost
import io.github.rybalkinsd.kohttp.ext.url
import it.forgottenworld.echelon.config.Config
import it.forgottenworld.echelon.utils.launchAsync
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

internal class CodeMessageSender(private var config: Config) {

    init {
        config.addOnConfigChangedListener {
            config = it
        }
    }

    fun sendMessage(discourseUsername: String, key: String) {
        val data = buildJsonObject {
            put("title", "Codice di verifica")
            put("raw", "CODICE DI VERIFICA: $key")
            put("target_recipients", discourseUsername)
            put("archetype", "private_message")
        }.toString()

        val apiKey = config.apiKey

        launchAsync {
            httpPost {
                url("${config.discourseUrl}/posts")
                charset("utf-8")
                header {
                    "api-key" to apiKey
                    "api-username" to "system"
                }
                body { json(data) }
            }.close()
        }
    }
}