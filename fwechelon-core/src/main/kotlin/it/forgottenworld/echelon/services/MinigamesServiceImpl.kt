package it.forgottenworld.echelon.services

import it.forgottenworld.echelon.minigames.MinigameScheduler
import it.forgottenworld.echelonapi.minigames.Minigame
import it.forgottenworld.echelonapi.services.MinigamesService

class MinigamesServiceImpl(
    private val minigameScheduler: MinigameScheduler
) : MinigamesService {

    override fun registerMinigameForRotation(minigame: Minigame) = minigameScheduler.addMinigameToRotation(minigame)

    override fun notifyReadyForAnnouncement(minigame: Minigame) = minigameScheduler.onMinigameReadyForAnnouncement(minigame)

    override fun onFinish(minigame: Minigame) = minigameScheduler.onMinigameFinished(minigame)

}