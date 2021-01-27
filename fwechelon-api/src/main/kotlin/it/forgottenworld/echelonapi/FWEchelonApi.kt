package it.forgottenworld.echelonapi

import it.forgottenworld.echelonapi.services.DiscourseService
import it.forgottenworld.echelonapi.services.MinigamesService
import it.forgottenworld.echelonapi.services.MutexActivityService

interface FWEchelonApi {
    val discourseService: DiscourseService
    val mutexActivityService: MutexActivityService
    val minigamesService: MinigamesService
}
