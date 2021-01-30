package it.forgottenworld.echelon.command

import it.forgottenworld.echelon.FWEchelonPlugin
import it.forgottenworld.echelon.command.api.CommandFunction
import it.forgottenworld.echelon.config.Strings
import org.bukkit.command.CommandSender
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinApiExtension
class CmdReload : CommandFunction<CommandSender>, KoinComponent {

    private val plugin by inject<FWEchelonPlugin>()

    override fun command(sender: CommandSender, args: Array<out String>): Boolean {
        plugin.reloadConfig()
        sender.sendMessage(Strings.CONFIG_RELOADED)
        return true
    }

}