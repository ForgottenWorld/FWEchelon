package it.forgottenworld.echelon

import it.forgottenworld.echelon.command.FWEchelonCommand
import it.forgottenworld.echelon.config.Config
import it.forgottenworld.echelon.event.PlayerJoinListener
import it.forgottenworld.echelon.event.PlayerQuitListener
import it.forgottenworld.echelon.services.DiscourseServiceImpl
import it.forgottenworld.echelon.services.MutexActivityServiceImpl
import it.forgottenworld.echelon.utils.getPlugin
import it.forgottenworld.echelonapi.FWEchelonApi
import org.bukkit.plugin.java.JavaPlugin

class FWEchelonPlugin : JavaPlugin(), FWEchelonApi {

    override fun onEnable() {
        logger.info("Enabling FWEchelon...")

        saveDefaultConfig()
        Config.config = config

        server.pluginManager.registerEvents(PlayerJoinListener(), this)
        server.pluginManager.registerEvents(PlayerQuitListener(), this)

        getCommand("fwechelon")?.setExecutor(FWEchelonCommand())
    }

    override fun onDisable() {
        logger.info("Disabling FWEchelon...")
    }

    override fun getDiscourseService() = DiscourseServiceImpl
    override fun getMutexActivityService() = MutexActivityServiceImpl

    companion object {

        fun reloadConfig() {
            val plugin = getPlugin<FWEchelonPlugin>()
            plugin.reloadConfig()
            Config.config = plugin.config
        }
    }
}