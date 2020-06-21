package it.forgottenworld.echelon

import it.forgottenworld.echelon.event.PlayerJoinListener
import it.forgottenworld.echelon.event.PlayerMoveListener
import org.bukkit.plugin.java.JavaPlugin

class FWEchelonPlugin : JavaPlugin() {
    companion object {
        lateinit var instance: FWEchelonPlugin
    }

    override fun onEnable() {
        logger.info("Enabling FWEchelon...")

        saveDefaultConfig()

        instance = this


        server.pluginManager.registerEvents(PlayerJoinListener(), this)
        server.pluginManager.registerEvents(PlayerMoveListener(), this)
    }

    override fun onDisable() {
        logger.info("Disabling FWEchelon...")
    }

}