package it.forgottenworld.echelon.command.api

import org.bukkit.command.CommandSender

internal class SenderCommand(private val handler: CommandHandler<CommandSender>) : Command {

    override fun walkExecute(sender: CommandSender, args: Array<out String>): Boolean {
        handler.command(sender, args)
        return true
    }
}