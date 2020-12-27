package it.forgottenworld.echelon.config

import it.forgottenworld.echelon.utils.echelon

object Config {

    val apiKey get() = echelon.config.getString("discourseApiKey") ?: "null"
    val forumActivationTimeout get() = echelon.config.getInt("forumActivationTimeout", 600)
    val discourseUrl get() = echelon.config.getString("discourseUrl", "https://forum.forgottenworld.it")!!
    val tosUrl get() = echelon.config.getString("tosUrl", "https://wikinew.forgottenworld.it/main/Termini")!!
    val enableTos get() = echelon.config.getBoolean("enableTos", false)
}