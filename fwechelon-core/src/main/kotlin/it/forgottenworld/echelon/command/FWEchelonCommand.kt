package it.forgottenworld.echelon.command

import it.forgottenworld.echelon.command.api.BranchingCommand
import it.forgottenworld.echelon.command.api.SenderCommand
import it.forgottenworld.echelon.command.api.TreeCommand
import it.forgottenworld.echelon.command.subcommands.cmdNoticeType

class FWEchelonCommand : TreeCommand(
        "fwechelon",
        BranchingCommand(
                "discourse" to BranchingCommand(
                        "noticetype" to SenderCommand(::cmdNoticeType)
                )
        )
)