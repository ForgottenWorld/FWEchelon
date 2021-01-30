package it.forgottenworld.echelon.command.api

import org.bukkit.command.BlockCommandSender
import org.bukkit.command.CommandSender

internal class BlockCommand(private val handler: CommandFunction<BlockCommandSender>) : Command {

    override fun walkExecute(sender: CommandSender, args: Array<out String>): Boolean {
        if (sender is BlockCommandSender)
            handler.command(sender, args)
        else
            sender.sendMessage("This command may only be executed by command blocks")

        return true
    }
}