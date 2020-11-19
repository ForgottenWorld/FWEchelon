package it.forgottenworld.echelon.config

import org.bukkit.configuration.file.FileConfiguration

object Config {

    var config: FileConfiguration? = null

    val apiKey by lazy { config!!.getString("discourseApiKey")!! }
    val forumActivationTimeout by lazy { config!!.getInt("forumActivationTimeout") }
    val discourseUrl by lazy { config!!.getString("discourseUrl")!! }
    val tosUrl by lazy { config!!.getString("tosUrl")!! }
}