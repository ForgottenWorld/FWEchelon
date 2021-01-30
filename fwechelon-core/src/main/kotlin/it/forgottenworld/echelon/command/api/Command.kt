package it.forgottenworld.echelon.command.api

import org.bukkit.command.CommandSender

internal interface Command {

    fun walkExecute(sender: CommandSender, args: Array<out String>): Boolean
}