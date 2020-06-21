package it.forgottenworld.echelon.state

import org.bukkit.entity.Player
import java.util.*

object TOSState {
    private val playersWhoHaveAcceptedTos = mutableSetOf<UUID>()

    fun playerAcceptTos(player: Player) {
        playersWhoHaveAcceptedTos.add(player.uniqueId)
    }

    fun hasPlayerAcceptedTos(player: Player) = playersWhoHaveAcceptedTos.contains(player.uniqueId)
}