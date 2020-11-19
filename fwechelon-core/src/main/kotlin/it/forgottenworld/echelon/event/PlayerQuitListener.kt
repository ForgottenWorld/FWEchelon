package it.forgottenworld.echelon.event

import it.forgottenworld.echelon.services.MutexActivityServiceImpl
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent


class PlayerQuitListener: Listener {

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val uuid = event.player.uniqueId
        if (MutexActivityServiceImpl.playersToRemoveFromMutexActivityOnDisconnect.remove(uuid)) {
            val name = MutexActivityServiceImpl.getPlayerMutexActivityName(event.player) ?: return
            MutexActivityServiceImpl.removePlayerFromMutexActivity(event.player, name)
        }
    }
}