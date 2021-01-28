package it.forgottenworld.echelon.config

import org.bukkit.configuration.file.FileConfiguration
import java.time.LocalTime

class Config(private val config: FileConfiguration) {

    val apiKey get() = config.getString("discourseApiKey") ?: "null"
    val forumActivationTimeout get() = config.getInt("forumActivationTimeout", 600)
    val discourseUrl get() = config.getString("discourseUrl", "https://forum.forgottenworld.it")!!
    val tosUrl get() = config.getString("tosUrl", "https://wikinew.forgottenworld.it/main/Termini")!!
    val enableTos get() = config.getBoolean("enableTos", false)
    val minigameScheduledAt get() = config.getStringList("minigameScheduledAt").map {
        val split = it.split(":")
        LocalTime.of(split[0].toInt(), split[1].toInt())
    }.sorted()
}