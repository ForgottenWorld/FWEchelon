package it.forgottenworld.echelon.command.subcommands

import it.forgottenworld.echelon.FWEchelonPlugin
import it.forgottenworld.echelon.config.Strings
import org.bukkit.command.CommandSender

fun cmdReload(sender: CommandSender, args: Array<out String>): Boolean {

    FWEchelonPlugin.reloadConfig()
    sender.sendMessage(Strings.CONFIG_RELOADED)

    return true
}