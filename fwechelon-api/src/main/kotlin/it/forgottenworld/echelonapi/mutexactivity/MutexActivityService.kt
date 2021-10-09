package it.forgottenworld.echelonapi.mutexactivity

import org.bukkit.entity.Player

interface MutexActivityService {

    /**
     * Check whether a player is currently in a mutually exclusive activity
     *
     * @param player the player
     * @return true if the player is in a mutually exclusive activity, false otherwise
     * */
    fun isPlayerInMutexActivity(player: Player): Boolean

    /**
     * Gets the name of the mutually exclusive activity the player is in
     *
     * @param player the player
     * @return the name of the mutually exclusive activity the player is in, null if none
     * */
    fun getPlayerMutexActivityName(player: Player): String?

    /**
     * Gets the name of the mutually exclusive activity the player is in
     *
     * @param player the player
     * @param name the name of the mutually exclusive activity
     * @param removeOnDisconnect whether the player is to be removed from the mutex activity
     * when they disconnect
     * @return true on success, false if the mutex activity was not found or if the
     * player already is in a mutex activity
     * */
    fun playerJoinMutexActivity(player: Player, name: String, removeOnDisconnect: Boolean): Boolean

    /**
     * Removes a player from a mutually exclusive activity
     *
     * @param player the player
     * @param name the name of the mutually exclusive activity
     * @return true if the removal was succesful, false if the mutex activity was not found
     * or if the player wasn't in it
     */
    fun removePlayerFromMutexActivity(player: Player, name: String): Boolean

    /**
     * Forcibly removes a player from the mutually exclusive activity they're currently in
     *
     * @param player the player
     * @param reason the reason for the removal
     * @return true if the player was removed from the activity, false if they weren't in one
     */
    fun forceRemovePlayerFromMutexActivity(player: Player, reason: String?): Boolean

    /**
     * Forcibly removes all players from a mutually exclusive activity
     *
     * @param name the name of the mutually exclusive activity
     * @param reason the reason for the removal
     */
    fun forceRemoveAllPlayersFromMutexActivity(name: String, reason: String?)

    /**
     * Forcibly removes all players from all mutually exclusive activities
     *
     * @param reason the reason for the removal
     */
    fun forceRemoveAllPlayersFromAllMutexActivities(reason: String?)

    /**
     * Makes Echelon aware of a new mutually exclusive activity players can join
     *
     * @param activity the listener for supervisor events
     * @return true on success, false if there already is a mutually exclusive activity with the same name
     * */
    fun registerMutexActivity(activity: MutexActivity): Boolean
}