package it.forgottenworld.echelonapi.mutexactivity

import org.bukkit.entity.Player

interface MutexActivityListener {

    /**
     * Gets called whenever a player is forcibly removed from the activity
     *
     * @param player the Player
     * @param reason the reason for the forced removal
     * */
    fun onPlayerForceRemoved(player: Player, reason: String?)

    /**
     * Gets called whenever all players are forcibly removed from the activity
     *
     * @param reason the reason for the forced removal
     * */
    fun onAllPlayersForceRemoved(reason: String?)
}