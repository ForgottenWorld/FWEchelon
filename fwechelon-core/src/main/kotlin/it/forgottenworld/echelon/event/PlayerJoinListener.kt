package it.forgottenworld.echelon.event

import it.forgottenworld.echelon.config.Config
import it.forgottenworld.echelon.gui.TosPrompt
import it.forgottenworld.echelon.utils.hasAcceptedTos
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType


class PlayerJoinListener: Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        if (!Config.enableTos || player.hasAcceptedTos) return

        player.addPotionEffect(PotionEffect(
                PotionEffectType.SLOW,
                2000000,
                127
        ))

        player.addPotionEffect(PotionEffect(
                PotionEffectType.JUMP,
                2000000,
                100000
        ))

        TosPrompt().startConversationForPlayer(player)
    }
}