@file:Suppress("unused")

package it.forgottenworld.echelon.utils

import com.okkero.skedule.BukkitDispatcher
import it.forgottenworld.echelon.FWEchelonPlugin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.qualifier.named
import org.koin.dsl.module

val coroutineUtilsModule = module {
    single { CoroutineScope(get<BukkitDispatcher>(named(Dispatchers.MAIN))) }
    single(named(Dispatchers.MAIN)) { BukkitDispatcher(get<FWEchelonPlugin>()) }
    single(named(Dispatchers.ASYNC)) { BukkitDispatcher(get<FWEchelonPlugin>(), async = true) }
}

enum class Dispatchers { MAIN, ASYNC }

fun KoinComponent.launch(
    dispatcher: Dispatchers = Dispatchers.MAIN,
    block: suspend CoroutineScope.() -> Unit
): Job {
    val scope = get<CoroutineScope>()
    val context = get<BukkitDispatcher>(named(dispatcher))
    return scope.launch(context, block = block)
}

fun KoinComponent.launchAsync(block: suspend CoroutineScope.() -> Unit) = launch(Dispatchers.ASYNC, block)

val KoinComponent.mainDispatcher get() = get<BukkitDispatcher>(named(Dispatchers.MAIN))
val KoinComponent.asyncDispatcher get() = get<BukkitDispatcher>(named(Dispatchers.ASYNC))