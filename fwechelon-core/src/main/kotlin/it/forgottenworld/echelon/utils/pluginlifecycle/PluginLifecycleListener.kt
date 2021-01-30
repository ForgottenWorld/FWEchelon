package it.forgottenworld.echelon.utils.pluginlifecycle

interface PluginLifecycleListener {

    fun onEnabled() {}

    fun onDisabled() {}
}