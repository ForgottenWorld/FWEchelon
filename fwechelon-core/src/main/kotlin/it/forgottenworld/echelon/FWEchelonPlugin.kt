package it.forgottenworld.echelon

import it.forgottenworld.echelon.command.FWEchelonCommand
import it.forgottenworld.echelon.command.commandModule
import it.forgottenworld.echelon.config.Config
import it.forgottenworld.echelon.discourse.discourseModule
import it.forgottenworld.echelon.minigame.command.MinigameCommand
import it.forgottenworld.echelon.minigame.minigamesModule
import it.forgottenworld.echelon.mutexactivity.mutexActivityModule
import it.forgottenworld.echelon.utils.coroutineUtilsModule
import it.forgottenworld.echelon.utils.pluginlifecycle.PluginLifecycleListener
import it.forgottenworld.echelon.utils.pluginlifecycle.PluginLifecycleOwner
import it.forgottenworld.echelonapi.FWEchelonApi
import it.forgottenworld.echelonapi.discourse.DiscourseService
import it.forgottenworld.echelonapi.minigame.MinigameService
import it.forgottenworld.echelonapi.mutexactivity.MutexActivityService
import org.bukkit.plugin.PluginDescriptionFile
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.plugin.java.JavaPluginLoader
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import java.io.File

@Suppress("unused")
class FWEchelonPlugin : JavaPlugin, FWEchelonApi, KoinComponent, PluginLifecycleOwner {
    
    override val discourseService by inject<DiscourseService>()
    override val mutexActivityService by inject<MutexActivityService>()
    override val minigameService by inject<MinigameService>()

    override val lifecycleListeners = mutableListOf<PluginLifecycleListener>()

    constructor() : super()

    constructor(
        loader: JavaPluginLoader,
        description: PluginDescriptionFile,
        dataFolder: File,
        file: File?
    ) : super(loader, description, dataFolder, file ?: File("build/libs/fwechelon-core-${description.version}.jar"))


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

        getCommand("fwechelon")!!.setExecutor(get<FWEchelonCommand>())
        getCommand("minigame")!!.setExecutor(get<MinigameCommand>())

        onPluginEnabled()
    }

    override fun onDisable() {
        onPluginDisabled()
        stopKoin()
    }
}