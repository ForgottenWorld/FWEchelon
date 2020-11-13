package it.forgottenworld.echelon

import it.forgottenworld.echelon.config.Config
import it.forgottenworld.echelon.event.PlayerJoinListener
import it.forgottenworld.echelon.services.DiscourseService
import it.forgottenworld.fwechelonapi.FWEchelonApi
import org.bukkit.plugin.java.JavaPlugin

class FWEchelonPlugin : JavaPlugin(), FWEchelonApi {

    companion object {
        lateinit var instance: FWEchelonPlugin
    }

    override fun onEnable() {
        logger.info("Enabling FWEchelon...")

        saveDefaultConfig()

        Config.config = config

        instance = this

        server.pluginManager.registerEvents(PlayerJoinListener(), this)
    }

    override fun onDisable() {
        logger.info("Disabling FWEchelon...")
    }

    override fun getDiscourseService() = DiscourseService
}