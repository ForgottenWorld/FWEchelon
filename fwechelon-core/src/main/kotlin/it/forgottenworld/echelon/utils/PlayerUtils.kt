package it.forgottenworld.echelon.utils

import it.forgottenworld.echelon.FWEchelonPlugin
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataType

private val tosNamespacedKey get() = NamespacedKey(getPlugin<FWEchelonPlugin>(), "echHasAcceptedTOS")

internal var Player.hasAcceptedTos
    get() = persistentDataContainer.getOrDefault(tosNamespacedKey, PersistentDataType.INTEGER, 0) == 1
    set(value) {
        if (value) persistentDataContainer.set(tosNamespacedKey, PersistentDataType.INTEGER, 1)
        else persistentDataContainer.remove(tosNamespacedKey)
    }