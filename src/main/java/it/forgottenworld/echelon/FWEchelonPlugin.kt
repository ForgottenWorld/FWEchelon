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
        //lateinit var storage: FileConfiguration
        //lateinit var pluginDataFolder: File
    }

    override fun onEnable() {
        logger.info("Enabling FWEchelon...")

        saveDefaultConfig()

        instance = this
        //pluginDataFolder = dataFolder
        //val confirmedFile = File(dataFolder, "confirmed.yml")
        //if (!confirmedFile.exists()) confirmedFile.createNewFile()
        //storage = YamlConfiguration().apply { load(confirmedFile) }

        /*storage.getKeys(false).forEach{
            ForumActivationState.addActivatedPlayer(UUID.fromString(it))
        }*/

        server.pluginManager.registerEvents(PlayerJoinListener(), this)
    }

    override fun onDisable() {
        logger.info("Disabling FWEchelon...")
    }

}