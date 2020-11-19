package it.forgottenworld.echelon.utils

import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.hover.content.Content

private val STRING_CHARACTERS = ('0'..'z')

fun getRandomString(length: Int) =
        (1..length).map { STRING_CHARACTERS.random() }.joinToString("")

inline fun component(build: ComponentBuilder.() -> Unit): Array<BaseComponent> = ComponentBuilder().apply { build() }.create()

fun ComponentBuilder.clickEvent(action: ClickEvent.Action, value: String): ComponentBuilder = event(ClickEvent(action, value))

fun ComponentBuilder.hoverEvent(action: HoverEvent.Action, vararg content: Content): ComponentBuilder = event(HoverEvent(action, *content))

fun ComponentBuilder.hoverEvent(action: HoverEvent.Action, content: Iterable<Content>): ComponentBuilder = event(HoverEvent(action, content.toList()))

fun ComponentBuilder.append(text: String, color: ChatColor): ComponentBuilder = append(text).color(color)