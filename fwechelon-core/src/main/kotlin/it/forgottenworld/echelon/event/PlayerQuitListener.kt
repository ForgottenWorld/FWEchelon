package it.forgottenworld.echelon.event

import it.forgottenworld.echelon.FWEchelonPlugin
import it.forgottenworld.echelon.services.MutexActivityServiceImpl
import it.forgottenworld.echelon.utils.getPlugin
import it.forgottenworld.echelonapi.FWEchelonApi
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent


class PlayerQuitListener: Listener {

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val uuid = event.player.uniqueId
        with (getPlugin<FWEchelonPlugin>().mutexActivityService) {
            if (playersToRemoveFromMutexActivityOnDisconnect.remove(uuid)) {
                val name = getPlayerMutexActivityName(event.player) ?: return
                removePlayerFromMutexActivity(event.player, name)
            }
        }
    }
}