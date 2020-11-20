package it.forgottenworld.echelon.command

import it.forgottenworld.echelon.command.api.BranchingCommand
import it.forgottenworld.echelon.command.api.SenderCommand
import it.forgottenworld.echelon.command.api.TreeCommand
import it.forgottenworld.echelon.command.subcommands.cmdReload

class FWEchelonCommand : TreeCommand(
        "fwechelon",
        BranchingCommand("reload" to SenderCommand(::cmdReload))
)