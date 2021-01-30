package it.forgottenworld.echelon.command.api

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

internal class PlayerCommand(private val handler: CommandFunction<Player>) : TerminalCommand {

    override fun walkExecute(sender: CommandSender, args: Array<out String>): Boolean {
        if (sender is Player)
            handler.command(sender, args)
        else
            sender.sendMessage("This command may only be executed by players")

        return true
    }
}