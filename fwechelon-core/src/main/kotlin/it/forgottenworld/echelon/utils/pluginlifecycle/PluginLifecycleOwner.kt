package it.forgottenworld.echelon.utils.pluginlifecycle

interface PluginLifecycleOwner {

    val lifecycleListeners: MutableList<PluginLifecycleListener>

    fun onPluginEnabled() = lifecycleListeners.forEach { it.onEnabled() }

    fun onPluginDisabled() = lifecycleListeners.forEach { it.onDisabled() }

    fun addListener(listener: PluginLifecycleListener) = lifecycleListeners.add(listener)

    fun removeListener(listener: PluginLifecycleListener) = lifecycleListeners.remove(listener)
}