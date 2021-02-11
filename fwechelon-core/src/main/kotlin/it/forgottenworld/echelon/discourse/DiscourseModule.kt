package it.forgottenworld.echelon.discourse

import it.forgottenworld.echelonapi.discourse.DiscourseService
import org.koin.core.component.KoinApiExtension
import org.koin.dsl.module

@OptIn(KoinApiExtension::class)
val discourseModule = module {
    single(createdAtStart = true) { ForumActivationManager() }
    factory { TosPrompt() }
    single { CodeMessageSender() }
    single<DiscourseService>(createdAtStart = true) { DiscourseServiceImpl() }
}