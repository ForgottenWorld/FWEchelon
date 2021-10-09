import be.seeseemelk.mockbukkit.MockBukkit
import be.seeseemelk.mockbukkit.ServerMock
import it.forgottenworld.echelon.FWEchelonPlugin
import it.forgottenworld.echelonapi.FWEchelonApi

abstract class BaseTest {

    protected var server: ServerMock? = null
    private var plugin: FWEchelonPlugin? = null
    protected val api get() = plugin as FWEchelonApi

    open fun setUp() {
        server = MockBukkit.mock()
        plugin = MockBukkit.load(FWEchelonPlugin::class.java)
    }

    open fun tearDown() {
        server = null
        plugin = null
        MockBukkit.unmock()
    }
}