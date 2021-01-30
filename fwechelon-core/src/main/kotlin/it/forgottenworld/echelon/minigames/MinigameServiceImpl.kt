package it.forgottenworld.echelon.minigames

import it.forgottenworld.echelonapi.minigames.Minigame
import it.forgottenworld.echelonapi.services.MinigameService
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinApiExtension
internal class MinigameServiceImpl : MinigameService, KoinComponent {

    private val minigameScheduler by inject<MinigameScheduler>()

    override fun registerMinigameForRotation(minigame: Minigame) = minigameScheduler.addMinigameToRotation(minigame)

    override fun notifyReadyForAnnouncement(minigame: Minigame) = minigameScheduler.onMinigameReadyForAnnouncement(minigame)

    override fun onFinish(minigame: Minigame) = minigameScheduler.onMinigameFinished(minigame)

}