package it.forgottenworld.echelon.mutexactivity

import it.forgottenworld.echelonapi.mutexactivity.MutexActivityService
import org.koin.core.component.KoinApiExtension
import org.koin.dsl.module

@OptIn(KoinApiExtension::class)
val mutexActivityModule = module {
    single<MutexActivityService>(createdAtStart = true) { MutexActivityServiceImpl() }
}