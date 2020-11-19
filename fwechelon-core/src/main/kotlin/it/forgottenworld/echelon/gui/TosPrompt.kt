package it.forgottenworld.echelon.gui

import it.forgottenworld.echelon.FWEchelonPlugin
import it.forgottenworld.echelon.config.Config
import it.forgottenworld.echelon.discourse.CodeMessageSender
import it.forgottenworld.echelon.manager.ForumActivationManager
import it.forgottenworld.echelon.utils.*
import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.ClickEvent
import org.bukkit.conversations.*
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffectType


class TosPrompt : ConversationAbandonedListener {

    override fun conversationAbandoned(p0: ConversationAbandonedEvent) =
            if (p0.gracefulExit())
                (p0.context.forWhom
                        .sendRawMessage("${ChatColor.GREEN}Grazie, buon divertimento!"))
            else
                (p0.context.forWhom
                        .sendRawMessage("${ChatColor.RED}Devi accettare i termini di servizio per poter accedere al server."))

    fun startConversationForPlayer(player: Player) = ConversationFactory(getPlugin<FWEchelonPlugin>())
            .withFirstPrompt(TOSConfirmationPrompt())
            .withModality(true)
            .thatExcludesNonPlayersWithMessage("Only players can run this conversation")
            .addConversationAbandonedListener(this).buildConversation(player).begin()

    private class TOSConfirmationPrompt : FixedSetPrompt() {

        override fun getPromptText(context: ConversationContext): String {
            (context.forWhom as Player).spigot().sendMessage(*component {
                append("§aBenvenuto/a!\n\n§fPer accedere al server devi accettare i ")

                append("termini di servizio", ChatColor.AQUA)
                clickEvent(ClickEvent.Action.OPEN_URL, Config.tosUrl)

                append("\n\n\n")

                append ("§f[ §aACCETTO §f]")
                clickEvent(ClickEvent.Action.RUN_COMMAND, "yes")

                append("    ")

                append ("§f[ §cRIFIUTO §f]")
                clickEvent(ClickEvent.Action.RUN_COMMAND, "no")
            })
            return ""
        }

        override fun isInputValid(context: ConversationContext, input: String) = input == "yes" || input == "no"

        override fun acceptValidatedInput(context: ConversationContext, input: String): Prompt? =
                if (input != "yes") {
                    (context.forWhom as Player)
                            .kickPlayer("Devi accettare i termini di servizio per poter accedere al server.")
                    Prompt.END_OF_CONVERSATION
                } else ForumUsernamePrompt()

        override fun getFailedValidationText(context: ConversationContext, invalidInput: String) =
                "Le risposte possibili sono yes o no."
    }

    private class ForumUsernamePrompt : StringPrompt() {

        override fun getPromptText(context: ConversationContext): String {
            (context.forWhom as? Player)?.spigot()?.sendMessage(*component {
                append("\n\nSe non lo hai già fatto, crea un account sul ")
                append("forum", ChatColor.GOLD)
                clickEvent(ClickEvent.Action.OPEN_URL, Config.discourseUrl)
                append(", poi inserisci il tuo username in chat qui sotto.", ChatColor.WHITE)
            })

            return ""
        }

        override fun acceptInput(context: ConversationContext, input: String?): Prompt? {
            input ?: return this
            CodeMessageSender.sendMessage((context.forWhom as Player).uniqueId, input.trim())
            return ForumCodePrompt()
        }

    }

    private class ForumCodePrompt : StringPrompt() {

        override fun getPromptText(context: ConversationContext) =
                "\n\nControlla le tue notifiche sul forum, dovresti aver ricevuto un codice di verifica. Inseriscilo in chat qui sotto."

        override fun acceptInput(context: ConversationContext, input: String?): Prompt? {
            input ?: return this
            val player = context.forWhom as Player
            if (ForumActivationManager.matchActivationDataAndRemoveIfTrue(player.uniqueId, input)) {
                player.removePotionEffect(PotionEffectType.JUMP)
                player.removePotionEffect(PotionEffectType.SLOW)
                player.hasAcceptedTos = true
                return Prompt.END_OF_CONVERSATION
            }
            player.sendMessage("${ChatColor.RED}CODICE ERRATO")
            return ForumUsernamePrompt()
        }
    }
}