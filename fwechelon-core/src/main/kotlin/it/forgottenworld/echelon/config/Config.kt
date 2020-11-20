package it.forgottenworld.echelon.config

import org.bukkit.configuration.file.FileConfiguration

object Config {

    var config: FileConfiguration? = null

    val apiKey get() = config!!.getString("discourseApiKey")!!
    val forumActivationTimeout get() = config!!.getInt("forumActivationTimeout")
    val discourseUrl get() = config!!.getString("discourseUrl")!!
    val tosUrl get() = config!!.getString("tosUrl")!!
}