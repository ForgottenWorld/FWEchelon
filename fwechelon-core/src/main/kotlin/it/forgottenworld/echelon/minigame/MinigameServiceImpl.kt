package it.forgottenworld.echelon.minigame

import it.forgottenworld.echelonapi.minigame.Minigame
import it.forgottenworld.echelonapi.minigame.MinigameService
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinApiExtension
internal class MinigameServiceImpl : MinigameService, KoinComponent {

    internal val minigameScheduler by inject<MinigameScheduler>()

    override fun registerMinigameForRotation(minigame: Minigame) = minigameScheduler.addMinigameToRotation(minigame)

    override fun notifyReadyForAnnouncement(minigame: Minigame) = minigameScheduler.onMinigameReadyForAnnouncement(minigame)

    override fun onFinish(minigame: Minigame) = minigameScheduler.onMinigameFinished(minigame)

}