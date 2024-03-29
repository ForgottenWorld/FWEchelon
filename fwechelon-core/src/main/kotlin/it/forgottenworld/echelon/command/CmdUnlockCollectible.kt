package it.forgottenworld.echelon.command

import it.forgottenworld.echelon.FWEchelonPlugin
import it.forgottenworld.echelon.command.api.CommandHandler
import it.forgottenworld.echelon.config.Strings
import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.command.BlockCommandSender
import org.bukkit.persistence.PersistentDataType
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CmdUnlockCollectible : CommandHandler<BlockCommandSender>, KoinComponent {

    private val plugin by inject<FWEchelonPlugin>()

    override fun command(sender: BlockCommandSender, args: Array<out String>): Boolean {
        val playerName = args.getOrNull(0) ?: return true
        val player = Bukkit.getPlayer(playerName) ?: return true
        val successName = args.getOrNull(1) ?: return true
        val failName = args.getOrNull(2) ?: return true
        val series = args.getOrNull(3) ?: return true
        val itemNo = args.getOrNull(4)?.toIntOrNull() ?: return true

        val nsk = NamespacedKey(plugin, "ECH_UNLOCKABLE_${series}")
        val pdc = player.persistentDataContainer
        val collectiblesUnlocked = pdc.getOrDefault(nsk, PersistentDataType.INTEGER, -1)
        if (collectiblesUnlocked == itemNo - 1) {
            pdc.set(nsk, PersistentDataType.INTEGER, itemNo)
            player.sendMessage("${ChatColor.DARK_GREEN}${Strings.ITEM_UNLOCKED.format(successName)}")
            return true
        }

        player.sendMessage("${ChatColor.RED}${Strings.YOU_CANT_UNLOCK_THIS_YET.format(failName)}")
        return true
    }

}