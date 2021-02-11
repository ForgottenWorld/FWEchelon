package it.forgottenworld.echelonapi

import it.forgottenworld.echelonapi.discourse.DiscourseService
import it.forgottenworld.echelonapi.minigame.MinigameService
import it.forgottenworld.echelonapi.mutexactivity.MutexActivityService

interface FWEchelonApi {
    val discourseService: DiscourseService
    val mutexActivityService: MutexActivityService
    val minigameService: MinigameService
}
