package it.forgottenworld.echelon.event

import it.forgottenworld.echelon.FWEchelonPlugin
import it.forgottenworld.echelon.gui.TosPrompt
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.persistence.PersistentDataType
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType


class PlayerJoinListener: Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent?) {
        val player = event?.player ?: return
        val nsk = NamespacedKey(FWEchelonPlugin.instance, "echHasAcceptedTOS")
        if (player.persistentDataContainer.getOrDefault(nsk, PersistentDataType.SHORT, 0) == 1.toShort()) return
        player.walkSpeed = 0f
        player.addPotionEffect(PotionEffect(
                PotionEffectType.JUMP,
                2000000,
                100000
        ))
        TosPrompt().startConversationForPlayer(player)
    }
}