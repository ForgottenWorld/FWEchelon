package it.forgottenworld.echelonapi.minigame

interface MinigameService {

    fun registerMinigameForRotation(minigame: Minigame): Boolean

    fun notifyReadyForAnnouncement(minigame: Minigame): Boolean

    fun onFinish(minigame: Minigame): Boolean
}