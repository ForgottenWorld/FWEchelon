package it.forgottenworld.echelonapi.minigames

import org.bukkit.entity.Player

interface Minigame {

    /**
     * Unique identifier of the minigame
     * */
    val id: String

    /**
     * Printable name of the minigame
     * */
    val name: String

    /**
     * Short description of the minigame
     * */
    val description: String

    /**
     * Minimum number of players required to start the event
     * */
    val minPlayers: Int

    /**
     * Maximum number of players who can join
     * */
    val maxPlayers: Int

    /**
     * Called whenever the minigame is picked in the rotation. The minigame
     * shall then prepare for the rotation being announced. Once the plugin
     * has made sure the event can start, it has to call
     * MinigamesService#notifyReadyForAnnouncement
     * */
    fun onPickedForRotation()

    /**
     * Called whenever the minigame is announced. After this is called, players
     * will be able to join the event.
     * */
    fun onAnnounced()

    /**
     * Called whenever a player joins the lobby
     *
     * @param player the Player who joined
     * @param players a list containing all the players in the lobby
     * */
    fun onPlayerJoinLobby(player: Player, players: List<Player>)

    /**
     * Called whenever a player leaves the lobby
     *
     * @param player the Player who left
     * @param players a list containing all the players in the lobby
     * */
    fun onPlayerLeaveLobby(player: Player, players: List<Player>)

    /**
     * Called whenever there are enough players to start the event.
     * The plugin should check whether any other conditions besides number
     * of players are met. If false is returned, this method will be
     * called again every time a player joins or leaves while the total
     * number of players stays above minimum
     *
     * @return true if the event can start safely, false otherwise.
     * */
    fun canStart(): Boolean

    /**
     * Called at the start of the event. When the event is finished, the
     * minigame plugin shall call MinigamesService#onFinish
     *
     * @param players the players in the lobby
     * */
    fun onStart(players: List<Player>)

    /**
     * Can be called at any time. Notifies the plugin Echelon considers
     * the event as abruptly stopped. The plugin shall handle cleanup
     *
     * @param players the players who were in the lobby at the time
     * the event was aborted
     * */
    fun onAborted(players: List<Player>)
}