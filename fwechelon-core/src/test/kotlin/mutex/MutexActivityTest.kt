package mutex

import BaseTest
import it.forgottenworld.echelonapi.mutexactivity.MutexActivity
import org.bukkit.entity.Player
import org.junit.After
import org.junit.Before
import org.junit.Test

open class MutexActivityTest : BaseTest() {

    class DummyMutexActivity1 : MutexActivity {
        override val id = "dummy1"
        override fun onPlayerForceRemoved(player: Player, reason: String?) {}
        override fun onAllPlayersForceRemoved(reason: String?) {}
    }

    class DummyMutexActivity2 : MutexActivity {
        override val id = "dummy2"
        override fun onPlayerForceRemoved(player: Player, reason: String?) {}
        override fun onAllPlayersForceRemoved(reason: String?) {}
    }

    private var player: Player? = null

    private val dummy1 = DummyMutexActivity1()
    private val dummy2 = DummyMutexActivity2()

    @Before
    override fun setUp() {
        super.setUp()
        player = server!!.addPlayer("tester")
        api.mutexActivityService.run {
            registerMutexActivity(dummy1)
            registerMutexActivity(dummy2)
            playerJoinMutexActivity(player!!, "dummy1", true)
        }
    }

    @Test
    fun leaveActivityAndJoinAnother() {
        api.mutexActivityService.removePlayerFromMutexActivity(player!!, "dummy1")
        assert(api.mutexActivityService.playerJoinMutexActivity(player!!, "dummy2", true))
    }

    @Test
    fun tryJoinWhileInActivity() {
        assert(!api.mutexActivityService.playerJoinMutexActivity(player!!, "dummy2", true))
    }

    /*@Test
    fun disconnectWhileInActivity() {
        // Doesn't work - kickPlayer is not yet implemented
        player!!.kickPlayer("")
        assert(!api.mutexActivityService.isPlayerInMutexActivity(player!!))
    }*/

    @After
    override fun tearDown() {
        player = null
        super.tearDown()
    }
}