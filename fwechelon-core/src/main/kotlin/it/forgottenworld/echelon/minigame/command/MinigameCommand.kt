package it.forgottenworld.echelon.minigame.command

import it.forgottenworld.echelon.command.api.BranchingCommand
import it.forgottenworld.echelon.command.api.PlayerCommand
import it.forgottenworld.echelon.command.api.TreeCommand
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

internal class MinigameCommand : TreeCommand(), KoinComponent {
    override val name = "minigame"
    override val cmdTree = BranchingCommand(
        "join" to PlayerCommand(get<CmdJoin>()),
        "leave" to PlayerCommand(get<CmdLeave>())
    )
}