package it.forgottenworld.echelon.command

import org.koin.dsl.module

val commandModule = module {
    factory { CmdReload() }
    factory { CmdUnlockCollectible() }
    factory { FWEchelonCommand() }
}