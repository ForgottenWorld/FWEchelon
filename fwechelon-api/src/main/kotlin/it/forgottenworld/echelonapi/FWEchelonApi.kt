package it.forgottenworld.echelonapi

import it.forgottenworld.echelonapi.services.DiscourseService
import it.forgottenworld.echelonapi.services.MutexActivityService

interface FWEchelonApi {

    fun getDiscourseService(): DiscourseService

    fun getMutexActivityService(): MutexActivityService
}
