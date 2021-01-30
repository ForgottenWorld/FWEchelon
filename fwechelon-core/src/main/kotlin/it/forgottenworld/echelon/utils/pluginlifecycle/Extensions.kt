package it.forgottenworld.echelon.utils.pluginlifecycle

inline fun PluginLifecycleOwner.addOnPluginEnabledListener(crossinline listener: () -> Unit) {
    addListener(
        object : PluginLifecycleListener {
            override fun onEnabled() {
                listener()
            }
        }
    )
}

inline fun PluginLifecycleOwner.addOnPluginDisabledListener(crossinline listener: () -> Unit) {
    addListener(
        object : PluginLifecycleListener {
            override fun onDisabled() {
                listener()
            }
        }
    )
}