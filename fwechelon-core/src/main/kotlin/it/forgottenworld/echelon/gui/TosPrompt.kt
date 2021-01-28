package it.forgottenworld.echelon.gui

import it.forgottenworld.echelon.config.Config
import it.forgottenworld.echelon.config.Strings
import it.forgottenworld.echelon.discourse.CodeMessageSender
import it.forgottenworld.echelon.manager.ForumActivationManager
import it.forgottenworld.echelon.utils.*
import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.ClickEvent
import org.bukkit.conversations.*
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffectType


internal class TosPrompt(
    private val config: Config,
    private val codeMessageSender: CodeMessageSender,
    private val forumActivationManager: ForumActivationManager
) : ConversationAbandonedListener {

    override fun conversationAbandoned(p0: ConversationAbandonedEvent) =
        if (p0.gracefulExit())
            p0.context.forWhom
                .sendRawMessage("${ChatColor.GREEN}${Strings.THANKS_HAVE_FUN}")
        else
            p0.context.forWhom
                .sendRawMessage("${ChatColor.RED}${Strings.YOU_MUST_ACCEPT_TOS_IOT_ACCESS}")

    fun startConversationForPlayer(player: Player) = ConversationFactory(echelon)
        .withFirstPrompt(TOSConfirmationPrompt(config, codeMessageSender, forumActivationManager))
        .withModality(true)
        .thatExcludesNonPlayersWithMessage("Only players can run this conversation")
        .addConversationAbandonedListener(this).buildConversation(player).begin()

    private class TOSConfirmationPrompt(
        private val config: Config,
        private val codeMessageSender: CodeMessageSender,
        private val forumActivationManager: ForumActivationManager
    ) : FixedSetPrompt() {

        override fun getPromptText(context: ConversationContext): String {
            (context.forWhom as Player).spigot().sendMessage(*component {
                append("§a${Strings.WELCOME}/a!\n\n§f${Strings.TO_ACCESS_YOU_MUST} ")

                append(Strings.TOS, ChatColor.AQUA)
                clickEvent(ClickEvent.Action.OPEN_URL, config.tosUrl)

                append("\n\n\n")

                append("§f[ §a${Strings.ACCEPT} §f]")
                clickEvent(ClickEvent.Action.RUN_COMMAND, "yes")

                append("    ")

                append("§f[ §c${Strings.DECLINE} §f]")
                clickEvent(ClickEvent.Action.RUN_COMMAND, "no")
            })
            return ""
        }

        override fun isInputValid(context: ConversationContext, input: String) = input == "yes" || input == "no"

        override fun acceptValidatedInput(context: ConversationContext, input: String): Prompt? =
            if (input != "yes") {
                (context.forWhom as Player)
                    .kickPlayer(Strings.YOU_MUST_ACCEPT_TOS_IOT_ACCESS)
                Prompt.END_OF_CONVERSATION
            } else ForumUsernamePrompt(config, codeMessageSender, forumActivationManager)

        override fun getFailedValidationText(context: ConversationContext, invalidInput: String) =
            Strings.POSSIBLE_ANSWERS_ARE_YES_OR_NO
    }

    private class ForumUsernamePrompt(
        private val config: Config,
        private val codeMessageSender: CodeMessageSender,
        private val forumActivationManager: ForumActivationManager
    ) : StringPrompt() {

        override fun getPromptText(context: ConversationContext): String {
            (context.forWhom as? Player)?.spigot()?.sendMessage(*component {
                append("\n\n${Strings.FORUM_USERNAME_REQUEST_1}")
                append(Strings.FORUM_USERNAME_REQUEST_2, ChatColor.GOLD)
                clickEvent(ClickEvent.Action.OPEN_URL, config.discourseUrl)
                append(Strings.FORUM_USERNAME_REQUEST_3, ChatColor.WHITE)
            })

            return ""
        }

        override fun acceptInput(context: ConversationContext, input: String?): Prompt {
            input ?: return this
            val key = forumActivationManager.addActivationDataForPlayer((context.forWhom as Player).uniqueId)
            codeMessageSender.sendMessage(key, input.trim())
            return ForumCodePrompt(config, codeMessageSender, forumActivationManager)
        }

    }

    private class ForumCodePrompt(
        private val config: Config,
        private val codeMessageSender: CodeMessageSender,
        private val forumActivationManager: ForumActivationManager
    ) : StringPrompt() {

        override fun getPromptText(context: ConversationContext) =
            "\n\n${Strings.CHECK_FORUM_NOTIFICATIONS}"

        override fun acceptInput(context: ConversationContext, input: String?): Prompt? {
            input ?: return this
            val player = context.forWhom as Player
            if (forumActivationManager.matchActivationDataAndRemoveIfTrue(player.uniqueId, input)) {
                player.removePotionEffect(PotionEffectType.JUMP)
                player.removePotionEffect(PotionEffectType.SLOW)
                player.hasAcceptedTos = true
                return Prompt.END_OF_CONVERSATION
            }
            player.sendMessage("${ChatColor.RED}${Strings.WRONG_CODE}")
            return ForumUsernamePrompt(config, codeMessageSender, forumActivationManager)
        }
    }
}