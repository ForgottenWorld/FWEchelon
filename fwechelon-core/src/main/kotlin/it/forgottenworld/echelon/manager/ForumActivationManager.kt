package it.forgottenworld.echelon.manager

import it.forgottenworld.echelon.config.Config
import it.forgottenworld.echelon.utils.getRandomString
import java.time.Duration
import java.time.LocalDateTime
import java.util.*

object ForumActivationManager {

    data class ActivationData(val key: String, val timestamp: LocalDateTime)

    private val playerActivationData = mutableMapOf<UUID, ActivationData>()

    private fun removeExpiredActivationData() {
        val timeout = Config.forumActivationTimeout
        playerActivationData
                .values
                .removeIf { (Duration.between(LocalDateTime.now(), it.timestamp).seconds >= timeout) }
    }

    fun addActivationDataForPlayer(uuid: UUID) = getRandomString(10)
            .also { playerActivationData[uuid] = ActivationData(it, LocalDateTime.now()) }

    fun matchActivationDataAndRemoveIfTrue(uuid: UUID, key: String): Boolean {
        removeExpiredActivationData()

        return if (playerActivationData[uuid]?.key == key) {
            playerActivationData.remove(uuid)
            true
        } else false
    }
}