package it.forgottenworld.echelon

import it.forgottenworld.echelon.command.FWEchelonCommand
import it.forgottenworld.echelon.event.PlayerJoinListener
import it.forgottenworld.echelon.event.PlayerQuitListener
import it.forgottenworld.echelon.services.DiscourseServiceImpl
import it.forgottenworld.echelon.services.MinigamesServiceImpl
import it.forgottenworld.echelon.services.MutexActivityServiceImpl
import it.forgottenworld.echelon.utils.echelon
import it.forgottenworld.echelonapi.FWEchelonApi
import it.forgottenworld.echelonapi.services.DiscourseService
import it.forgottenworld.echelonapi.services.MinigamesService
import it.forgottenworld.echelonapi.services.MutexActivityService
import org.bukkit.plugin.java.JavaPlugin

class FWEchelonPlugin : JavaPlugin(), FWEchelonApi {

    override val discourseService by lazy { DiscourseServiceImpl() }
    override val mutexActivityService by lazy { MutexActivityServiceImpl() }
    override val minigamesService by lazy { MinigamesServiceImpl() }

    override fun onEnable() {
        saveDefaultConfig()

        server.pluginManager.registerEvents(PlayerJoinListener(), this)
        server.pluginManager.registerEvents(PlayerQuitListener(), this)

        getCommand("fwechelon")!!.setExecutor(FWEchelonCommand())
    }

    override fun onDisable() {
        logger.info("Disabling FWEchelon...")
    }

    companion object {

        fun reloadConfig() = echelon.reloadConfig()
    }
}