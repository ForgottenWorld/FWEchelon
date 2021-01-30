package it.forgottenworld.echelon.discourse

import it.forgottenworld.echelon.FWEchelonPlugin
import it.forgottenworld.echelon.config.Config
import it.forgottenworld.echelon.utils.getRandomString
import it.forgottenworld.echelon.utils.register
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.persistence.PersistentDataType
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

    private val plugin by inject<FWEchelonPlugin>()
    private val config by inject<Config>()
    private val tosPrompt by inject<TosPrompt>()
    private val tosNamespacedKey get() = NamespacedKey(plugin, "echHasAcceptedTOS")

    init {
        config.addOnConfigChangedListener {
            removeExpiredActivationData()
        }
    }

    fun registerListeners() {
        PlayerJoinListener().register(plugin)
    }

    internal var Player.hasAcceptedTos
        get() = persistentDataContainer.getOrDefault(tosNamespacedKey, PersistentDataType.INTEGER, 0) == 1
        set(value) {
            if (value) persistentDataContainer.set(tosNamespacedKey, PersistentDataType.INTEGER, 1)
            else persistentDataContainer.remove(tosNamespacedKey)
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

    internal inner class PlayerJoinListener : Listener {

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