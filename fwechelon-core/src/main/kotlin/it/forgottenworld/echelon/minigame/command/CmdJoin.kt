package it.forgottenworld.echelon.minigame.command

import it.forgottenworld.echelon.command.api.CommandHandler
import it.forgottenworld.echelon.config.Strings
import it.forgottenworld.echelon.minigame.MinigameScheduler
import it.forgottenworld.echelon.utils.append
import it.forgottenworld.echelon.utils.chatComponent
import it.forgottenworld.echelon.utils.clickEvent
import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.ClickEvent
import org.bukkit.entity.Player
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinApiExtension
class CmdJoin : CommandHandler<Player>, KoinComponent {

    private val minigameScheduler by inject<MinigameScheduler>()

    override fun command(sender: Player, args: Array<out String>): Boolean {
        if (!minigameScheduler.onPlayerJoinLobby(sender)) {
            sender.sendMessage(Strings.EVENT_IS_FULL)
            return true
        }
        sender.spigot().sendMessage(*chatComponent {
            append(Strings.EVENT_JOINED)
            append(Strings.CLICK_HERE_TO_LEAVE_EVENT, ChatColor.GOLD)
            clickEvent(ClickEvent.Action.RUN_COMMAND, "minigame leave")
        })
        return true
    }
}