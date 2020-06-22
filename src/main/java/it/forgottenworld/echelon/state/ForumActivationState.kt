package it.forgottenworld.echelon.state

import it.forgottenworld.echelon.FWEchelonPlugin
import it.forgottenworld.echelon.utils.getRandomString
import java.time.Duration
import java.time.LocalDateTime
import java.util.*

object ForumActivationState {

    data class ActivationData(
            val key: String,
            val timestamp: LocalDateTime
        )

    private val playerActivationData = mutableMapOf<UUID, ActivationData>()
    //private val activatedPlayers = mutableSetOf<UUID>()

    private fun removeExpiredActivationData() {
        val now = LocalDateTime.now()
        val timeout = FWEchelonPlugin.instance.config.getInt("forumActivationTimeout")
        playerActivationData
                .values
                .removeIf {
                    (Duration.between(now, it.timestamp).seconds >= timeout)
                }
    }

//    fun addActivatedPlayer(uuid: UUID) {
//        activatedPlayers.add(uuid)
//    }

    //fun isPlayerActivated(uuid: UUID) = activatedPlayers.contains(uuid)

    fun addActivationDataForPlayer(uuid: UUID) =
            getRandomString(10).also {
                playerActivationData[uuid] = ActivationData(
                        it,
                        LocalDateTime.now()
                )
            }

    fun matchActivationDataAndRemoveIfTrue(uuid: UUID, key: String): Boolean {
        removeExpiredActivationData()

        return if (playerActivationData[uuid]?.key == key) {
            playerActivationData.remove(uuid)
            //addActivatedPlayer(uuid)
            true
        } else false
    }

}