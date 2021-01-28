@file:Suppress("unused")

package it.forgottenworld.echelon.utils

import com.github.shynixn.mccoroutine.asyncDispatcher
import com.github.shynixn.mccoroutine.launch
import com.github.shynixn.mccoroutine.launchAsync
import com.github.shynixn.mccoroutine.minecraftDispatcher
import it.forgottenworld.echelon.FWEchelonPlugin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.bukkit.plugin.java.JavaPlugin
import kotlin.coroutines.CoroutineContext

internal fun launch(f: suspend CoroutineScope.() -> Unit) = JavaPlugin.getPlugin(FWEchelonPlugin::class.java).launch(f)

internal fun launchAsync(f: suspend CoroutineScope.() -> Unit) = JavaPlugin.getPlugin(FWEchelonPlugin::class.java).launchAsync(f)

internal val Dispatchers.minecraft: CoroutineContext
    get() = JavaPlugin.getPlugin(FWEchelonPlugin::class.java).minecraftDispatcher

internal val Dispatchers.async: CoroutineContext
    get() = JavaPlugin.getPlugin(FWEchelonPlugin::class.java).asyncDispatcher