package it.forgottenworld.echelon.minigame

import it.forgottenworld.echelon.minigame.command.CmdJoin
import it.forgottenworld.echelon.minigame.command.CmdLeave
import it.forgottenworld.echelon.minigame.command.MinigameCommand
import it.forgottenworld.echelonapi.minigame.MinigameService
import org.koin.core.component.KoinApiExtension
import org.koin.dsl.module

@OptIn(KoinApiExtension::class)
val minigamesModule = module {
    single { MinigameScheduler() }
    single<MinigameService>(createdAtStart = true) { MinigameServiceImpl() }
    factory { MinigameAnnouncer() }
    factory { CmdJoin() }
    factory { CmdLeave() }
    factory { MinigameCommand() }
}