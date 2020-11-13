package it.forgottenworld.echelon.config

import org.bukkit.configuration.file.FileConfiguration

object Config {

    var config: FileConfiguration? = null

    val apiKey get() = config?.getString("discourseApiKey")!!
}