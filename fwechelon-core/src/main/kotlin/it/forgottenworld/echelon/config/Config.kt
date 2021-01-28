package it.forgottenworld.echelon.config

import it.forgottenworld.echelon.config.Config.OnConfigChangedListener
import org.bukkit.configuration.file.FileConfiguration
import java.time.LocalTime

internal class Config(private var config: FileConfiguration) {

    val listeners = mutableListOf<OnConfigChangedListener>()

    val apiKey get() = config.getString("discourseApiKey") ?: "null"
    val forumActivationTimeout get() = config.getInt("forumActivationTimeout", DefaultValues.FORUM_ACTIVATION_TIMEOUT)
    val discourseUrl get() = config.getString("discourseUrl", DefaultValues.DISCOURSE_URL)!!
    val tosUrl get() = config.getString("tosUrl", DefaultValues.WIKI_URL)!!
    val enableTos get() = config.getBoolean("enableTos", false)
    val minigameScheduledAt get() = config.getStringList("minigameScheduledAt").map {
        val split = it.split(":")
        LocalTime.of(split[0].toInt(), split[1].toInt())
    }.sorted()

    fun updateConfig(config: FileConfiguration) {
        this.config = config
        for (l in listeners) l.onConfigChanged(this)
    }

    inline fun addOnConfigChangedListener(crossinline listener: (Config) -> Unit) {
        listeners.add(OnConfigChangedListener { listener(it) })
    }

    fun interface OnConfigChangedListener {
        fun onConfigChanged(config: Config)
    }

    private object DefaultValues {
        const val FORUM_ACTIVATION_TIMEOUT = 600
        const val DISCOURSE_URL = "https://forum.forgottenworld.it"
        const val WIKI_URL = "https://wiki.forgottenworld.it/main/Termini"
    }
}