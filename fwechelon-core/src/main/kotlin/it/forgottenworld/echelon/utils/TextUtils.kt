package it.forgottenworld.echelon.utils

import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.hover.content.Content

private val STRING_CHARACTERS = ('0'..'9') + ('a'..'z') + ('A'..'Z')

internal fun getRandomString(length: Int) = CharArray(length) { STRING_CHARACTERS.random() }.concatToString()

internal inline fun chatComponent(build: ComponentBuilder.() -> Unit): Array<BaseComponent> = ComponentBuilder().apply { build() }.create()

internal fun ComponentBuilder.clickEvent(action: ClickEvent.Action, value: String): ComponentBuilder = event(ClickEvent(action, value))

internal fun ComponentBuilder.hoverEvent(action: HoverEvent.Action, vararg content: Content): ComponentBuilder = event(HoverEvent(action, *content))

internal fun ComponentBuilder.hoverEvent(action: HoverEvent.Action, content: Iterable<Content>): ComponentBuilder = event(HoverEvent(action, content.toList()))

internal fun ComponentBuilder.append(text: String, color: ChatColor): ComponentBuilder = append(text).color(color)