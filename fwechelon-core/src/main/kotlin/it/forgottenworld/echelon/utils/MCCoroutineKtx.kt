@file:Suppress("unused")
@file:OptIn(KoinApiExtension::class)

package it.forgottenworld.echelon.utils

import com.github.shynixn.mccoroutine.asyncDispatcher
import com.github.shynixn.mccoroutine.launch
import com.github.shynixn.mccoroutine.launchAsync
import com.github.shynixn.mccoroutine.minecraftDispatcher
import it.forgottenworld.echelon.FWEchelonPlugin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import kotlin.coroutines.CoroutineContext

object MCCoroutineKtx : KoinComponent {

    lateinit var plugin: FWEchelonPlugin

    internal fun launch(f: suspend CoroutineScope.() -> Unit) = plugin.launch(f)

    internal fun launchAsync(f: suspend CoroutineScope.() -> Unit) = plugin.launchAsync(f)

    internal val Dispatchers.minecraft: CoroutineContext
        get() = plugin.minecraftDispatcher

    internal val Dispatchers.async: CoroutineContext
        get() = plugin.asyncDispatcher
}