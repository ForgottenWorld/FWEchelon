@file:OptIn(KoinApiExtension::class)
package it.forgottenword.echelon.test

import be.seeseemelk.mockbukkit.MockBukkit
import be.seeseemelk.mockbukkit.ServerMock
import it.forgottenworld.echelon.FWEchelonPlugin
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.component.KoinApiExtension
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class DiscourseTests {

    private lateinit var server: ServerMock
    private lateinit var plugin: FWEchelonPlugin

    @Before
    fun setUp() {
        server = MockBukkit.mock()
        plugin = MockBukkit.load(FWEchelonPlugin::class.java) as FWEchelonPlugin
    }

    @Test
    fun getMessages() = runBlocking<Unit> {
        suspendCoroutine<String> { co ->
            plugin.discourseService.getPostsWithCustomNoticeTypeInTopic(
                4344,
                {
                    co.resume(it.toString())
                }
            ) {
                co.resume(it)
            }
        }
    }

    @After
    fun tearDown() {
        MockBukkit.unmock()
    }
}

