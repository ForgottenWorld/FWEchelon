package it.forgottenworld.echelon.discourse

import com.google.gson.Gson
import it.forgottenworld.echelon.FWEchelonPlugin
import it.forgottenworld.echelon.state.ForumActivationState
import org.bukkit.Bukkit.getLogger
import java.io.BufferedReader
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

object CodeMessageSender {

    private val url = URL("https://forum.forgottenworld.it/posts")
    private val apiKey = FWEchelonPlugin.instance.config.getString("discourseApiKey")!!

    data class RequestData(
            val title: String = "Codice di verifica",
            val raw: String,
            val target_recipients: String,
            val archetype: String = "private_message"
    )

    fun sendMessage(uuid: UUID, discourseUsername: String) {
        val connection = (url.openConnection() as HttpURLConnection).apply {
            requestMethod = "POST"
            doOutput = true

            setRequestProperty("Api-Key", apiKey)
            setRequestProperty("Api-Username", "system")
            setRequestProperty("charset", "utf-8")
            setRequestProperty("Content-Type", "application/json")
        }

        val key = ForumActivationState.addActivationDataForPlayer(uuid)
        val postData = Gson().toJson(
                RequestData(
                        raw = "CODICE DI VERIFICA: $key",
                        target_recipients = discourseUsername
                ))

        try {
            val outputStream = DataOutputStream(connection.outputStream)
            outputStream.write(postData.toByteArray(Charsets.UTF_8))
            outputStream.flush()
        } catch (e: Exception) {
            getLogger().info(e.message)
            e.printStackTrace()
        }

        if (connection.responseCode != HttpURLConnection.HTTP_OK && connection.responseCode != HttpURLConnection.HTTP_CREATED) {
            try {

                val inputStream = DataInputStream(connection.inputStream)
                val reader = BufferedReader(InputStreamReader(inputStream))
                val output = reader.readLine()

                getLogger().info(output)
            } catch (e: Exception) {
                getLogger().info(e.message)
                e.printStackTrace()
            }
        }
    }
}