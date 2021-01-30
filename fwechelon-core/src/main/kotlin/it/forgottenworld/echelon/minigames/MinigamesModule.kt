package it.forgottenworld.echelon.minigames

import it.forgottenworld.echelon.minigames.command.CmdJoin
import it.forgottenworld.echelon.minigames.command.CmdLeave
import it.forgottenworld.echelon.minigames.command.MinigameCommand
import it.forgottenworld.echelonapi.services.MinigameService
import org.koin.core.component.KoinApiExtension
import org.koin.dsl.module

@OptIn(KoinApiExtension::class)
val minigamesModule = module {
    single { MinigameScheduler() }
    single<MinigameService> { MinigameServiceImpl() }
    factory { MinigameAnnouncer() }
    factory { CmdJoin() }
    factory { CmdLeave() }
    factory { MinigameCommand() }
}