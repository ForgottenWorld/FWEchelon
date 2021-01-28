package it.forgottenworld.echelon

import it.forgottenworld.echelon.command.FWEchelonCommand
import it.forgottenworld.echelon.config.Config
import it.forgottenworld.echelon.discourse.CodeMessageSender
import it.forgottenworld.echelon.manager.ForumActivationManager
import it.forgottenworld.echelon.minigames.MinigameScheduler
import it.forgottenworld.echelon.services.DiscourseServiceImpl
import it.forgottenworld.echelon.services.MinigamesServiceImpl
import it.forgottenworld.echelon.services.MutexActivityServiceImpl
import it.forgottenworld.echelon.utils.register
import it.forgottenworld.echelonapi.FWEchelonApi
import it.forgottenworld.echelonapi.services.DiscourseService
import it.forgottenworld.echelonapi.services.MinigamesService
import it.forgottenworld.echelonapi.services.MutexActivityService
import org.bukkit.plugin.java.JavaPlugin

class FWEchelonPlugin : JavaPlugin(), FWEchelonApi {

    private val configuration by lazy { Config(config) }

    private val minigameScheduler by lazy { MinigameScheduler(configuration) }

    private val codeMessageSender by lazy { CodeMessageSender(configuration) }
    private val forumActivationManager by lazy { ForumActivationManager(configuration, codeMessageSender) }

    override val discourseService: DiscourseService by lazy { DiscourseServiceImpl(configuration) }
    override val mutexActivityService: MutexActivityService by lazy { MutexActivityServiceImpl() }
    override val minigamesService: MinigamesService by lazy { MinigamesServiceImpl(minigameScheduler) }

    override fun onEnable() {
        saveDefaultConfig()

        forumActivationManager.PlayerJoinListener().register(this)
        (mutexActivityService as MutexActivityServiceImpl).PlayerQuitListener().register(this)

        getCommand("fwechelon")!!.setExecutor(FWEchelonCommand())
    }

    override fun onDisable() {
        logger.info("Disabling FWEchelon...")
    }

    override fun reloadConfig() {
        super.reloadConfig()
        configuration.updateConfig(config)
    }
}