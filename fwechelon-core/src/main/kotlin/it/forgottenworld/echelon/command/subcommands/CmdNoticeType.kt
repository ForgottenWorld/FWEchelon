package it.forgottenworld.echelon.command.subcommands

import it.forgottenworld.echelon.services.DiscourseServiceImpl
import org.bukkit.command.CommandSender

fun cmdNoticeType(sender: CommandSender, args: Array<out String>): Boolean {

    val topicId = args.getOrNull(0)?.toIntOrNull() ?: run {
        sender.sendMessage("Invalid arguments, provide an integer topic ID")
        return true
    }

    DiscourseServiceImpl.getPostsWithCustomNoticeTypeInTopic(topicId, {
        for (p in it) sender.sendMessage(p.toString())
    }) { sender.sendMessage(it) }

    return true
}