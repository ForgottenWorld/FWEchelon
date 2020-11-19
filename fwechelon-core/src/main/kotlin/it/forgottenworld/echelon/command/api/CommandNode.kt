package it.forgottenworld.echelon.command.api

import org.bukkit.command.CommandSender

interface CommandNode {

    fun walkExecute(sender: CommandSender, args: Array<out String>): Boolean
}