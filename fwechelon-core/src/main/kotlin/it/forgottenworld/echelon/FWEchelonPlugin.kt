package it.forgottenworld.echelon

import it.forgottenworld.echelon.command.FWEchelonCommand
import it.forgottenworld.echelon.config.Config
import it.forgottenworld.echelon.discourse.CodeMessageSender
import it.forgottenworld.echelon.gui.TosPrompt
import it.forgottenworld.echelon.manager.ForumActivationManager
import it.forgottenworld.echelon.minigames.MinigameScheduler
import it.forgottenworld.echelon.services.DiscourseServiceImpl
import it.forgottenworld.echelon.services.MinigamesServiceImpl
import it.forgottenworld.echelon.services.MutexActivityServiceImpl
import it.forgottenworld.echelon.utils.MCCoroutineKtx
import it.forgottenworld.echelon.utils.register
import it.forgottenworld.echelonapi.FWEchelonApi
import it.forgottenworld.echelonapi.services.DiscourseService
import it.forgottenworld.echelonapi.services.MinigamesService
import it.forgottenworld.echelonapi.services.MutexActivityService
import org.bukkit.plugin.PluginDescriptionFile
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.plugin.java.JavaPluginLoader
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.dsl.module
import java.io.File

@Suppress("unused")
@KoinApiExtension
class FWEchelonPlugin : JavaPlugin, FWEchelonApi, KoinComponent {

    constructor() : super()

    constructor(
        loader: JavaPluginLoader,
        description: PluginDescriptionFile,
        dataFolder: File,
        file: File?
    ) : super(loader, description, dataFolder, file ?: File("build/libs/fwechelon-core-${description.version}.jar"))

    private val configuration by inject<Config>()
    private val forumActivationManager by inject<ForumActivationManager>()

    override val discourseService by inject<DiscourseService>()
    override val mutexActivityService by inject<MutexActivityService>()
    override val minigamesService by inject<MinigamesService>()

    override fun onEnable() {
        saveDefaultConfig()

        MCCoroutineKtx.plugin = this

        val echelonModule = module {
            single { this@FWEchelonPlugin }
            factory { TosPrompt() }
            single { Config() }
            single { ForumActivationManager() }
            single { MinigameScheduler() }
            single { CodeMessageSender() }
            single<DiscourseService> { DiscourseServiceImpl() }
            single<MutexActivityService> { MutexActivityServiceImpl() }
            single<MinigamesService> { MinigamesServiceImpl() }
        }

        startKoin {
            modules(echelonModule)
        }

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