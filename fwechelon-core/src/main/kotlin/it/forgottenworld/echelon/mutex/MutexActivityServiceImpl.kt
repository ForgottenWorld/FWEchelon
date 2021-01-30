package it.forgottenworld.echelon.mutex

import it.forgottenworld.echelon.FWEchelonPlugin
import it.forgottenworld.echelon.utils.pluginlifecycle.addOnPluginEnabledListener
import it.forgottenworld.echelon.utils.register
import it.forgottenworld.echelonapi.mutexactivity.MutexActivityListener
import it.forgottenworld.echelonapi.services.MutexActivityService
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

@KoinApiExtension
internal class MutexActivityServiceImpl : MutexActivityService, KoinComponent {

    private val plugin by inject<FWEchelonPlugin>()

    private val mutexActivities = mutableMapOf<String, MutexActivityListener>()
    private val playerMutexActivities = mutableMapOf<UUID, String>()
    val playersToRemoveFromMutexActivityOnDisconnect = mutableSetOf<UUID>()

    init {
        plugin.addOnPluginEnabledListener {
            PlayerQuitListener().register(plugin)
            Bukkit.getLogger().info("Registered events for MutexActivityServiceImpl")
        }
    }

    override fun isPlayerInMutexActivity(player: Player) = playerMutexActivities.containsKey(player.uniqueId)

    override fun getPlayerMutexActivityName(player: Player) = playerMutexActivities[player.uniqueId]

    override fun playerJoinMutexActivity(player: Player, name: String, removeOnDisconnect: Boolean) = when {
        !mutexActivities.containsKey(name) -> false
        playerMutexActivities.containsKey(player.uniqueId) -> false
        else -> {
            if (removeOnDisconnect)
                playersToRemoveFromMutexActivityOnDisconnect.add(player.uniqueId)
            playerMutexActivities[player.uniqueId] = name
            true
        }
    }

    override fun removePlayerFromMutexActivity(player: Player, name: String) =
        if (playerMutexActivities[player.uniqueId] == name) {
            playerMutexActivities.remove(player.uniqueId)
            true
        } else false

    override fun forceRemovePlayerFromMutexActivity(player: Player, reason: String?): Boolean {
        val mutexActivityName = playerMutexActivities[player.uniqueId] ?: return false
        mutexActivities[mutexActivityName]?.onPlayerForceRemoved(player, reason)
        playerMutexActivities.remove(player.uniqueId)
        return true
    }

    override fun forceRemoveAllPlayersFromMutexActivity(name: String, reason: String?) {
        mutexActivities[name]?.onAllPlayersForceRemoved(reason)
        playerMutexActivities.values.removeIf { it == name }
    }

    override fun forceRemoveAllPlayersFromAllMutexActivities(reason: String?) {
        mutexActivities.values.forEach { it.onAllPlayersForceRemoved(reason) }
        playerMutexActivities.clear()
    }

    override fun registerMutexActivity(name: String, listener: MutexActivityListener): Boolean {
        if (mutexActivities.containsKey(name)) return false
        mutexActivities[name] = listener
        return true
    }

    inner class PlayerQuitListener : Listener {

        @EventHandler
        fun onPlayerQuit(event: PlayerQuitEvent) {
            val uuid = event.player.uniqueId
            if (playersToRemoveFromMutexActivityOnDisconnect.remove(uuid)) {
                val name = getPlayerMutexActivityName(event.player) ?: return
                removePlayerFromMutexActivity(event.player, name)
            }
        }
    }
}