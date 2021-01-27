package it.forgottenworld.echelon.services

import it.forgottenworld.echelonapi.minigames.Minigame
import it.forgottenworld.echelonapi.services.MinigamesService

class MinigamesServiceImpl : MinigamesService {

    private val minigames = mutableMapOf<String, Minigame>()

    override fun registerMinigameForRotation(minigame: Minigame): Boolean {
        if (minigames.contains(minigame.id)) return false
        minigames[minigame.id] = minigame
        return true
    }

    override fun notifyReadyForAnnouncement(minigame: Minigame) {
        TODO("Not yet implemented")
    }

    override fun onFinish(minigame: Minigame) {
        TODO("Not yet implemented")
    }

}