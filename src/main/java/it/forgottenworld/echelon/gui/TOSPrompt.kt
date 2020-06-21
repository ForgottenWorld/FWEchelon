package it.forgottenworld.echelon.gui

import it.forgottenworld.echelon.FWEchelonPlugin
import it.forgottenworld.echelon.state.TOSState
import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.conversations.*
import org.bukkit.entity.Player


class TOSPrompt : ConversationAbandonedListener {

    private val conversationFactory: ConversationFactory = ConversationFactory(FWEchelonPlugin.instance)
            .withFirstPrompt(TOSConfirmationPrompt())
            .thatExcludesNonPlayersWithMessage("Only players can run this conversation")
            .addConversationAbandonedListener(this)

    override fun conversationAbandoned(p0: ConversationAbandonedEvent) {
        if (p0.gracefulExit()) {
            (p0.context.forWhom.sendRawMessage("Grazie, buon divertimento!"))
        } else {
            (p0.context.forWhom.sendRawMessage("Devi accettare i termini di servizio per poter accedere al server."))
        }
    }

    fun startConversationForPlayer(player: Player) {
        conversationFactory.buildConversation(player).begin()
    }

    private class TOSConfirmationPrompt : FixedSetPrompt() {

        override fun getPromptText(context: ConversationContext): String {
            (context.forWhom as Player).spigot().sendMessage(
                    TextComponent("${ChatColor.GREEN}Benvenuto/a!\n\n${ChatColor.WHITE}Per accedere al server devi accettare i ").apply {
                        addExtra(
                                TextComponent("${ChatColor.AQUA}termini di servizio\n\n\n").apply {
                                    clickEvent = ClickEvent(ClickEvent.Action.OPEN_URL, "https://wiki.forgottenworld.it/main/Termini")
                                }
                        )
                        addExtra(TextComponent("[${ChatColor.GREEN}ACCETTO${ChatColor.WHITE}]  ").apply {
                            clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, "yes")
                        })
                        addExtra(TextComponent("[${ChatColor.RED}RIFIUTO${ChatColor.WHITE}]").apply {
                            clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, "no")
                        })
                    }

            )
            return ""
        }

        override fun isInputValid(context: ConversationContext, input: String): Boolean {
            return setOf("yes", "no").contains(input)
        }

        override fun acceptValidatedInput(context: ConversationContext, input: String): Prompt? {
            if (input != "yes") {
                (context.forWhom as Player).kickPlayer("Devi accettare i termini di servizio per poter accedere al server.")
            } else {
                TOSState.playerAcceptTos(context.forWhom as Player)
            }
            return Prompt.END_OF_CONVERSATION
        }

        override fun getFailedValidationText(context: ConversationContext, invalidInput: String): String? {
            return "Only 'yes' or 'no' options are allowed here!"
        }
    }

}