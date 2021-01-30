package it.forgottenworld.echelon.mutex

import it.forgottenworld.echelonapi.services.MutexActivityService
import org.koin.core.component.KoinApiExtension
import org.koin.dsl.module

@OptIn(KoinApiExtension::class)
val mutexActivityModule = module {
    single<MutexActivityService>(createdAtStart = true) { MutexActivityServiceImpl() }
}