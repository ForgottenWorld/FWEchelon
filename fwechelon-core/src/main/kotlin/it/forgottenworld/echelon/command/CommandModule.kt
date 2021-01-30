package it.forgottenworld.echelon.command

import org.koin.core.component.KoinApiExtension
import org.koin.dsl.module

@OptIn(KoinApiExtension::class)
val commandModule = module {
    factory { CmdReload() }
    factory { CmdUnlockCollectible() }
    factory { FWEchelonCommand() }
}