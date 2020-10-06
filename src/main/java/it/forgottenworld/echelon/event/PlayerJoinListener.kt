package it.forgottenworld.echelon.event

import it.forgottenworld.echelon.FWEchelonPlugin
import it.forgottenworld.echelon.gui.TOSPrompt
import it.forgottenworld.echelon.state.ForumActivationState
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.persistence.PersistentDataType
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType


class PlayerJoinListener: Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent?) {

        event?.run {
            if (player.persistentDataContainer.getOrDefault(
                            NamespacedKey(FWEchelonPlugin.instance, "echHasAcceptedTOS"),
                            PersistentDataType.SHORT,0) != 1.toShort()) {
                player.walkSpeed = 0f
                player.addPotionEffect(PotionEffect(PotionEffectType.JUMP, 2000000, 100000))
                TOSPrompt().startConversationForPlayer(player)
            }
        }
    }
}