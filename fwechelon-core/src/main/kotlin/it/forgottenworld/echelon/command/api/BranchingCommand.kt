package it.forgottenworld.echelon.command.api

import org.bukkit.command.CommandSender

internal class BranchingCommand(vararg bindings: Pair<String, CommandNode>) : CommandNode {

    val bindings = bindings.toMap()

    override fun walkExecute(sender: CommandSender, args: Array<out String>) =
            args.isNotEmpty() && bindings[args[0]]?.walkExecute(sender, args.drop(1).toTypedArray()) ?: false
}