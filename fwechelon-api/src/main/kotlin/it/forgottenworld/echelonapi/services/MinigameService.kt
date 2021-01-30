package it.forgottenworld.echelonapi.services

import it.forgottenworld.echelonapi.minigames.Minigame

interface MinigameService {

    fun registerMinigameForRotation(minigame: Minigame): Boolean

    fun notifyReadyForAnnouncement(minigame: Minigame): Boolean

    fun onFinish(minigame: Minigame): Boolean
}