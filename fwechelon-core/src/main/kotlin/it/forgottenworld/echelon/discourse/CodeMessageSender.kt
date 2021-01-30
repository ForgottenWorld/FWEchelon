package it.forgottenworld.echelon.discourse

import io.github.rybalkinsd.kohttp.dsl.httpPost
import io.github.rybalkinsd.kohttp.ext.url
import it.forgottenworld.echelon.config.Config
import it.forgottenworld.echelon.utils.launchAsync
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinApiExtension
internal class CodeMessageSender : KoinComponent {

    private val config by inject<Config>()

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