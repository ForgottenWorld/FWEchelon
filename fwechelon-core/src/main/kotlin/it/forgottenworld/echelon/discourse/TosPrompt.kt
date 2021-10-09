package it.forgottenworld.echelon.discourse

import it.forgottenworld.echelon.FWEchelonPlugin
import it.forgottenworld.echelon.config.Config
import it.forgottenworld.echelon.config.Strings
import it.forgottenworld.echelon.utils.append
import it.forgottenworld.echelon.utils.chatComponent
import it.forgottenworld.echelon.utils.clickEvent
import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.ClickEvent
import org.bukkit.conversations.*
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffectType
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class TosPrompt : ConversationAbandonedListener, KoinComponent {

    private val plugin by inject<FWEchelonPlugin>()
    private val config by inject<Config>()
    private val codeMessageSender by inject<CodeMessageSender>()
    private val forumActivationManager by inject<ForumActivationManager>()

    override fun conversationAbandoned(p0: ConversationAbandonedEvent) =
        if (p0.gracefulExit())
            p0.context.forWhom
                .sendRawMessage("${ChatColor.GREEN}${Strings.THANKS_HAVE_FUN}")
        else
            p0.context.forWhom
                .sendRawMessage("${ChatColor.RED}${Strings.YOU_MUST_ACCEPT_TOS_IOT_ACCESS}")

    fun startConversationForPlayer(player: Player) = ConversationFactory(plugin)
        .withFirstPrompt(TOSConfirmationPrompt())
        .withModality(true)
        .thatExcludesNonPlayersWithMessage("Only players can run this conversation")
        .addConversationAbandonedListener(this).buildConversation(player).begin()

    private inner class TOSConfirmationPrompt : FixedSetPrompt() {

        override fun getPromptText(context: ConversationContext): String {
            (context.forWhom as Player).spigot().sendMessage(*chatComponent {
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
            } else ForumUsernamePrompt()

        override fun getFailedValidationText(context: ConversationContext, invalidInput: String) =
            Strings.POSSIBLE_ANSWERS_ARE_YES_OR_NO
    }

    private inner class ForumUsernamePrompt : StringPrompt() {

        override fun getPromptText(context: ConversationContext): String {
            (context.forWhom as? Player)?.spigot()?.sendMessage(*chatComponent {
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
            codeMessageSender.sendMessage(input.trim(), key)
            return ForumCodePrompt()
        }

    }

    private inner class ForumCodePrompt : StringPrompt() {

        override fun getPromptText(context: ConversationContext) =
            "\n\n${Strings.CHECK_FORUM_NOTIFICATIONS}"

        override fun acceptInput(context: ConversationContext, input: String?): Prompt? {
            input ?: return this
            val player = context.forWhom as Player
            with (forumActivationManager) {
                if (matchActivationDataAndRemoveIfTrue(player.uniqueId, input)) {
                    player.removePotionEffect(PotionEffectType.JUMP)
                    player.removePotionEffect(PotionEffectType.SLOW)
                    player.hasAcceptedTos = true
                    return Prompt.END_OF_CONVERSATION
                }
            }
            player.sendMessage("${ChatColor.RED}${Strings.WRONG_CODE}")
            return ForumUsernamePrompt()
        }
    }
}