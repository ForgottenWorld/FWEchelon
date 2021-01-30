package it.forgottenworld.echelon.utils

import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

internal fun getPlayer(uuid: UUID) = Bukkit.getPlayer(uuid)

internal fun Listener.register(plugin: JavaPlugin) = plugin.server.pluginManager.registerEvents(this, plugin)