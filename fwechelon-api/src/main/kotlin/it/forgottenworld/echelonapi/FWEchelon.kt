package it.forgottenworld.echelonapi

import org.bukkit.Bukkit

object FWEchelon {

    /**
     * Convenience method for retrieving FWEchelonPlugin's API
     *
     * @return the plugin's API
     * @throws [IllegalStateException] if the plugin is not loaded
     */
    @JvmStatic
    val api get() = Bukkit
        .getPluginManager()
        .getPlugin("FWEchelon") as? FWEchelonApi
        ?: error("FWEchelon not loaded")
}