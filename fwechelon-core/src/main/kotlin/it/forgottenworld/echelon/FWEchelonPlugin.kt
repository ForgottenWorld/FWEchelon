package it.forgottenworld.echelon

import it.forgottenworld.echelon.command.FWEchelonCommand
import it.forgottenworld.echelon.config.Config
import it.forgottenworld.echelon.discourse.CodeMessageSender
import it.forgottenworld.echelon.event.PlayerJoinListener
import it.forgottenworld.echelon.manager.ForumActivationManager
import it.forgottenworld.echelon.services.DiscourseServiceImpl
import it.forgottenworld.echelon.services.MinigamesServiceImpl
import it.forgottenworld.echelon.services.MutexActivityServiceImpl
import it.forgottenworld.echelon.utils.echelon
import it.forgottenworld.echelonapi.FWEchelonApi
import org.bukkit.plugin.java.JavaPlugin

class FWEchelonPlugin : JavaPlugin(), FWEchelonApi {

    private val configuration by lazy { Config(config) }

    override val discourseService by lazy { DiscourseServiceImpl(configuration) }
    override val mutexActivityService by lazy { MutexActivityServiceImpl() }
    override val minigamesService by lazy { MinigamesServiceImpl() }

    private val forumActivationManager by lazy { ForumActivationManager(configuration) }
    private val codeMessageSender by lazy { CodeMessageSender(configuration, forumActivationManager) }

    override fun onEnable() {
        saveDefaultConfig()

        server.pluginManager.registerEvents(
            PlayerJoinListener(
                configuration,
                codeMessageSender,
                forumActivationManager
            ),
            this
        )
        server.pluginManager.registerEvents(mutexActivityService.PlayerQuitListener(), this)

        getCommand("fwechelon")!!.setExecutor(FWEchelonCommand())
    }

    override fun onDisable() {
        logger.info("Disabling FWEchelon...")
    }

    companion object {

        fun reloadConfig() = echelon.reloadConfig()
    }
}