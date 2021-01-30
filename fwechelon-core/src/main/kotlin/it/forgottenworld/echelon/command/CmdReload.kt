package it.forgottenworld.echelon.command

import it.forgottenworld.echelon.command.api.CommandHandler
import it.forgottenworld.echelon.config.Config
import it.forgottenworld.echelon.config.Strings
import org.bukkit.command.CommandSender
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinApiExtension
class CmdReload : CommandHandler<CommandSender>, KoinComponent {

    private val config by inject<Config>()

    override fun command(sender: CommandSender, args: Array<out String>): Boolean {
        config.reloadConfig()
        sender.sendMessage(Strings.CONFIG_RELOADED)
        return true
    }

}