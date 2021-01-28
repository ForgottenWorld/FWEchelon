package it.forgottenworld.echelon.command.subcommands

import it.forgottenworld.echelon.config.Strings
import it.forgottenworld.echelon.utils.echelon
import org.bukkit.command.CommandSender

internal fun cmdReload(sender: CommandSender, args: Array<out String>): Boolean {

    echelon.reloadConfig()
    sender.sendMessage(Strings.CONFIG_RELOADED)

    return true
}