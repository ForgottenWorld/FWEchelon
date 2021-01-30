package it.forgottenworld.echelon

import it.forgottenworld.echelon.command.FWEchelonCommand
import it.forgottenworld.echelon.command.commandModule
import it.forgottenworld.echelon.config.Config
import it.forgottenworld.echelon.discourse.ForumActivationManager
import it.forgottenworld.echelon.discourse.discourseModule
import it.forgottenworld.echelon.minigames.command.MinigameCommand
import it.forgottenworld.echelon.minigames.minigamesModule
import it.forgottenworld.echelon.mutex.MutexActivityServiceImpl
import it.forgottenworld.echelon.mutex.mutexActivityModule
import it.forgottenworld.echelon.utils.coroutineUtilsModule
import it.forgottenworld.echelonapi.FWEchelonApi
import it.forgottenworld.echelonapi.services.DiscourseService
import it.forgottenworld.echelonapi.services.MinigameService
import it.forgottenworld.echelonapi.services.MutexActivityService
import org.bukkit.plugin.java.JavaPlugin
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.dsl.module

@KoinApiExtension
class FWEchelonPlugin : JavaPlugin(), FWEchelonApi, KoinComponent {

    private val configuration by inject<Config>()
    private val forumActivationManager by inject<ForumActivationManager>()

    override val discourseService by inject<DiscourseService>()
    override val mutexActivityService by inject<MutexActivityService>()
    override val minigameService by inject<MinigameService>()


    override fun onEnable() {
        saveDefaultConfig()

        startKoin {
            modules(
                module(createdAtStart = true) {
                    single { this@FWEchelonPlugin }
                    single { Config() }
                },
                coroutineUtilsModule,
                mutexActivityModule,
                discourseModule,
                minigamesModule,
                commandModule
            )
        }

        (mutexActivityService as MutexActivityServiceImpl).registerListeners()
        forumActivationManager.registerListeners()

        getCommand("fwechelon")!!.setExecutor(get<FWEchelonCommand>())
        getCommand("minigame")!!.setExecutor(get<MinigameCommand>())

        logger.info("FWEchelon enabled")
    }

    override fun onDisable() {
        logger.info("Disabling FWEchelon...")
    }

    override fun reloadConfig() {
        super.reloadConfig()
        configuration.updateConfig(config)
    }
}