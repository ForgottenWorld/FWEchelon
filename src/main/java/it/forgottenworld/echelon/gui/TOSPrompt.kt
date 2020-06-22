package it.forgottenworld.echelon.gui

import it.forgottenworld.echelon.FWEchelonPlugin
import it.forgottenworld.echelon.discourse.CodeMessageSender
import it.forgottenworld.echelon.state.ForumActivationState
import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.NamespacedKey
import org.bukkit.conversations.*
import org.bukkit.entity.Player
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.persistence.PersistentDataType
import org.bukkit.potion.PotionEffectType
import java.io.File


class TOSPrompt : ConversationAbandonedListener {

    private val conversationFactory: ConversationFactory = ConversationFactory(FWEchelonPlugin.instance)
            .withFirstPrompt(TOSConfirmationPrompt())
            .thatExcludesNonPlayersWithMessage("Only players can run this conversation")
            .addConversationAbandonedListener(this)

    override fun conversationAbandoned(p0: ConversationAbandonedEvent) {
        if (p0.gracefulExit()) {
            (p0.context.forWhom.sendRawMessage("${ChatColor.GREEN}Grazie, buon divertimento!"))
        } else {
            (p0.context.forWhom.sendRawMessage("${ChatColor.RED}Devi accettare i termini di servizio per poter accedere al server."))
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
            val player = (context.forWhom as Player)

            return if (input != "yes") {
                player.kickPlayer("Devi accettare i termini di servizio per poter accedere al server.")
                Prompt.END_OF_CONVERSATION
            } else {
                ForumUsernamePrompt()
            }
        }

        override fun getFailedValidationText(context: ConversationContext, invalidInput: String): String? {
            return "Le risposte possibili sono yes o no."
        }
    }

    private class ForumUsernamePrompt : StringPrompt() {

        override fun getPromptText(context: ConversationContext): String {
            (context.forWhom as Player).spigot().sendMessage(
                    TextComponent("\n\nSe non lo hai già fatto, crea un account sul ")
                            .apply {
                                addExtra(
                                        TextComponent("${ChatColor.GOLD}forum")
                                                .apply {
                                                    clickEvent = ClickEvent(ClickEvent.Action.OPEN_URL, "https://forum.forgottenworld.it")
                                                })
                                addExtra(TextComponent("${ChatColor.WHITE}, poi inserisci il tuo username."))
                            }
            )

            return ""
        }

        override fun acceptInput(context: ConversationContext, input: String?): Prompt? {
            input ?: return this
            CodeMessageSender.sendMessage((context.forWhom as Player).uniqueId, input.trim())
            return ForumCodePrompt()
        }
    }

    private class ForumCodePrompt : StringPrompt() {

        override fun getPromptText(context: ConversationContext): String {
            return "\n\nControlla le tue notifiche sul forum, dovresti aver ricevuto un codice di verifica. Inseriscilo sotto."
        }

        override fun acceptInput(context: ConversationContext, input: String?): Prompt? {
            input ?: return this
            val player = context.forWhom as Player
            if (ForumActivationState
                            .matchActivationDataAndRemoveIfTrue(
                                    player.uniqueId,
                                    input
                            )) {
                player.walkSpeed = 0.2f
                player.removePotionEffect(PotionEffectType.JUMP)
                player.persistentDataContainer.set(NamespacedKey(FWEchelonPlugin.instance, "echHasAcceptedTOS"), PersistentDataType.SHORT, 1)
                //FWEchelonPlugin.storage.set(player.uniqueId.toString(), true)
                //FWEchelonPlugin.storage.save(File(FWEchelonPlugin.pluginDataFolder, "confirmed.yml"))
                return Prompt.END_OF_CONVERSATION
            }
            player.sendMessage("${ChatColor.RED}CODICE ERRATO")
            return ForumUsernamePrompt()
        }
    }
}