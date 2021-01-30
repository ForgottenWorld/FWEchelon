package it.forgottenworld.echelon.command.api

import org.bukkit.command.CommandSender

fun interface CommandFunction<T : CommandSender> {
    fun command(sender: T, args: Array<out String>): Boolean
}