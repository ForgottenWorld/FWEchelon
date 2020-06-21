package it.forgottenworld.echelon

import org.bukkit.plugin.java.JavaPlugin

class FWEchelonPlugin : JavaPlugin() {
    companion object {
        lateinit var instance: FWEchelonPlugin
    }

    override fun onEnable() {
        logger.info("Enabling FWEchelon...")

        instance = this
    }

    override fun onDisable() {
        logger.info("Disabling FWEchelon...")
    }

}