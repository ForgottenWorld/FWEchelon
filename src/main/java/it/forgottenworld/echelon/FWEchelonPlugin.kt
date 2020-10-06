package it.forgottenworld.echelon

import it.forgottenworld.echelon.event.PlayerJoinListener
import it.forgottenworld.echelon.state.ForumActivationState
import org.bukkit.Bukkit.getOfflinePlayer
import org.bukkit.Bukkit.getPlayer
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.util.*

class FWEchelonPlugin : JavaPlugin() {

    companion object {
        lateinit var instance: FWEchelonPlugin
    }

    override fun onEnable() {
        logger.info("Enabling FWEchelon...")

        saveDefaultConfig()

        instance = this

        server.pluginManager.registerEvents(PlayerJoinListener(), this)
    }

    override fun onDisable() {
        logger.info("Disabling FWEchelon...")
    }
}