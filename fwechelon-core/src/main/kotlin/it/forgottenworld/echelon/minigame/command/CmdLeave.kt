package it.forgottenworld.echelon.minigame.command

import it.forgottenworld.echelon.command.api.CommandHandler
import it.forgottenworld.echelon.minigame.MinigameScheduler
import org.bukkit.entity.Player
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CmdLeave : CommandHandler<Player>, KoinComponent {

    private val minigameScheduler by inject<MinigameScheduler>()

    override fun command(sender: Player, args: Array<out String>): Boolean {
        minigameScheduler.onPlayerLeaveLobby(sender)
        return true
    }
}