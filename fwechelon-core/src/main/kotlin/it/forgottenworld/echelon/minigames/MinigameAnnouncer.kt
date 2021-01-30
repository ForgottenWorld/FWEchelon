package it.forgottenworld.echelon.minigames

import it.forgottenworld.echelon.config.Strings
import it.forgottenworld.echelonapi.minigames.Minigame
import org.bukkit.entity.Player

class MinigameAnnouncer {

    fun announceMinigame(minigame: Minigame, players: Iterable<Player>) {
        val title = String.format(Strings.NEW_EVENT_ABOUT_TO_START, minigame.name)
        for (p in players) {
            p.sendTitle(title, minigame.description,10,70,20)
        }
    }
}