package it.forgottenworld.echelon.command

import it.forgottenworld.echelon.command.api.BlockCommand
import it.forgottenworld.echelon.command.api.BranchingCommand
import it.forgottenworld.echelon.command.api.SenderCommand
import it.forgottenworld.echelon.command.api.TreeCommand
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

internal class FWEchelonCommand : TreeCommand(), KoinComponent {
    override val name = "fwechelon"
    override val cmdTree = BranchingCommand(
        "reload" to SenderCommand(get<CmdReload>()),
        "unlockcollectible" to BlockCommand(get<CmdUnlockCollectible>())
    )
}