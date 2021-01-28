package it.forgottenworld.echelon.utils

import it.forgottenworld.echelon.FWEchelonPlugin
import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

internal inline fun <reified T: JavaPlugin> getPlugin() = JavaPlugin.getPlugin(T::class.java)

internal val echelon get() = getPlugin<FWEchelonPlugin>()

internal fun getPlayer(uuid: UUID) = Bukkit.getPlayer(uuid)

internal fun Listener.register(plugin: JavaPlugin) = plugin.server.pluginManager.registerEvents(this, plugin)