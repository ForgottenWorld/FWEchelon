package it.forgottenworld.echelon.utils

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

inline fun <reified T: JavaPlugin> getPlugin() = JavaPlugin.getPlugin(T::class.java)

fun getPlayer(uuid: UUID) = Bukkit.getPlayer(uuid)