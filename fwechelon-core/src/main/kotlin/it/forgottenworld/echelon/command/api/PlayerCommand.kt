package it.forgottenworld.echelon.command.api

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

internal class PlayerCommand(private val handler: (Player, Array<out String>) -> Unit): TerminalCommand {

    override fun walkExecute(sender: CommandSender, args: Array<out String>): Boolean {
        if (sender is Player)
            handler(sender, args)
        else
            sender.sendMessage("This command may only be executed by players")

        return true
    }
}