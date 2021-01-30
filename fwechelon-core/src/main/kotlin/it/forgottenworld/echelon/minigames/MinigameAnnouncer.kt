package it.forgottenworld.echelon.minigames

import it.forgottenworld.echelon.config.Strings
import it.forgottenworld.echelon.utils.append
import it.forgottenworld.echelon.utils.chatComponent
import it.forgottenworld.echelon.utils.clickEvent
import it.forgottenworld.echelonapi.minigames.Minigame
import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.ClickEvent
import org.bukkit.entity.Player

class MinigameAnnouncer {

    fun announceMinigame(minigame: Minigame, players: Iterable<Player>) {
        val title = String.format(Strings.NEW_EVENT_ABOUT_TO_START, minigame.name)
        val chatMessage = chatComponent {
            append(title)
            append(Strings.CLICK_HERE_TO_JOIN_EVENT, ChatColor.GREEN)
            clickEvent(ClickEvent.Action.RUN_COMMAND, "minigame join")
        }
        for (p in players) {
            p.sendTitle(title, minigame.description,10,70,20)
            p.spigot().sendMessage(*chatMessage)
        }
    }
}