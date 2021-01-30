package it.forgottenworld.echelon.minigames.command

import it.forgottenworld.echelon.command.api.CommandHandler
import it.forgottenworld.echelon.minigames.MinigameScheduler
import org.bukkit.entity.Player
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinApiExtension
class CmdLeave : CommandHandler<Player>, KoinComponent {

    private val minigameScheduler by inject<MinigameScheduler>()

    override fun command(sender: Player, args: Array<out String>): Boolean {
        minigameScheduler.onPlayerLeaveLobby(sender)
        return true
    }
}