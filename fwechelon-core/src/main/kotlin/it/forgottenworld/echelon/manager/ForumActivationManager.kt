package it.forgottenworld.echelon.manager

import it.forgottenworld.echelon.config.Config
import it.forgottenworld.echelon.gui.TosPrompt
import it.forgottenworld.echelon.utils.getRandomString
import it.forgottenworld.echelon.utils.hasAcceptedTos
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.time.Duration
import java.time.LocalDateTime
import java.util.*

@KoinApiExtension
internal class ForumActivationManager : KoinComponent {

    private val config by inject<Config>()
    private val tosPrompt by inject<TosPrompt>()

    init {
        config.addOnConfigChangedListener {
            removeExpiredActivationData()
        }
    }

    data class ActivationData(val key: String, val timestamp: LocalDateTime)

    private val playerActivationData = mutableMapOf<UUID, ActivationData>()

    private fun removeExpiredActivationData() {
        val timeout = config.forumActivationTimeout
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

    internal inner class PlayerJoinListener: Listener {

        @EventHandler
        fun onPlayerJoin(event: PlayerJoinEvent) {
            val player = event.player
            if (!config.enableTos || player.hasAcceptedTos) return

            player.addPotionEffect(
                PotionEffect(
                    PotionEffectType.SLOW,
                    2000000,
                    127
                )
            )

            player.addPotionEffect(
                PotionEffect(
                    PotionEffectType.JUMP,
                    2000000,
                    100000
                )
            )

            tosPrompt.startConversationForPlayer(player)
        }
    }
}