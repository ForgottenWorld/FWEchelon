package it.forgottenworld.echelon.mutexactivity

import it.forgottenworld.echelonapi.mutexactivity.MutexActivityService
import org.koin.dsl.module

val mutexActivityModule = module {
    single<MutexActivityService>(createdAtStart = true) { MutexActivityServiceImpl() }
}