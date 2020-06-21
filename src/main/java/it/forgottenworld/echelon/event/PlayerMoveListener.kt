package it.forgottenworld.echelon.event

import it.forgottenworld.echelon.gui.TOSPrompt
import it.forgottenworld.echelon.state.TOSState
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerMoveEvent

class PlayerMoveListener: Listener {

    @EventHandler
    fun onPlayerMove(event: PlayerMoveEvent?) {
        event ?: return

        if (!TOSState.hasPlayerAcceptedTos(event.player))
            event.isCancelled = true
    }
}