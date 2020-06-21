package it.forgottenworld.echelon.event

import it.forgottenworld.echelon.gui.TOSPrompt
import it.forgottenworld.echelon.state.TOSState
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoinListener: Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent?) {
        event ?: return

        if (!TOSState.hasPlayerAcceptedTos(event.player))
            TOSPrompt().startConversationForPlayer(event.player)
    }
}