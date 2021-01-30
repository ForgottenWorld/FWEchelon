package it.forgottenworld.echelon.minigames.command

import it.forgottenworld.echelon.command.api.CommandFunction
import it.forgottenworld.echelon.minigames.MinigameScheduler
import org.bukkit.entity.Player
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinApiExtension
class CmdJoin : CommandFunction<Player>, KoinComponent {

    private val minigameScheduler by inject<MinigameScheduler>()

    override fun command(sender: Player, args: Array<out String>): Boolean {
        minigameScheduler.onPlayerJoinLobby(sender)
        return true
    }
}