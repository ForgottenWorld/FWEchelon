package it.forgottenworld.echelon.minigames

import it.forgottenworld.echelon.config.Config
import it.forgottenworld.echelon.utils.getPlayer
import it.forgottenworld.echelon.utils.launch
import it.forgottenworld.echelonapi.minigames.Minigame
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.random.Random

@KoinApiExtension
internal class MinigameScheduler : KoinComponent {

    private val config by inject<Config>()
    private val minigameAnnouncer by inject<MinigameAnnouncer>()

    init {
        config.addOnConfigChangedListener {
            scheduledTimes = it.minigameScheduledAt
            scheduleNextMinigame()
        }
    }

    private var schedulingJob: Job? = null

    private var scheduledTimes = config.minigameScheduledAt
    private val selectionWeights = mutableMapOf<String, Int>()
    private val minigamesInRotation = mutableMapOf<String, Minigame>()
    private var pendingLobbyMinigame: Minigame? = null
    private var pendingStartMinigame: Minigame? = null
    private var lobbyPlayers = mutableSetOf<UUID>()
    private var ongoingMinigame: Minigame? = null

    private val millisUntilNextEvent: Long
        get() {
            if (scheduledTimes.isEmpty()) return -1
            var isTomorrow = false
            val time = scheduledTimes.find { it.isAfter(LocalTime.now()) }
                ?: scheduledTimes.first().also { isTomorrow = true }
            var date = LocalDate.now()
            if (isTomorrow) date = date.plusDays(1)
            val dateTime = date.atTime(time)
            return LocalDateTime.now().until(dateTime, ChronoUnit.MILLIS)
        }

    fun addMinigameToRotation(minigame: Minigame): Boolean {
        if (minigamesInRotation.contains(minigame.id)) return false
        minigamesInRotation[minigame.id] = minigame
        for (key in selectionWeights.keys) selectionWeights[key] = BASE_WEIGHT
        selectionWeights[minigame.id] = BASE_WEIGHT
        scheduleNextMinigame()
        return true
    }

    fun onMinigameReadyForAnnouncement(minigame: Minigame): Boolean {
        if (pendingLobbyMinigame?.id != minigame.id) return false
        minigameAnnouncer.announceMinigame(minigame, Bukkit.getServer().onlinePlayers)
        minigame.onAnnounced()
        pendingLobbyMinigame = null
        pendingStartMinigame = minigame
        return true
    }

    private fun checkAndStart(minigame: Minigame, players: List<Player>) {
        if (lobbyPlayers.size < minigame.minPlayers || !minigame.canStart()) return
        minigame.onStart(players)
        pendingStartMinigame = null
        ongoingMinigame = minigame
    }

    fun onPlayerJoinLobby(player: Player): Boolean {
        val mg = pendingStartMinigame ?: return false
        if (lobbyPlayers.size >= mg.maxPlayers) return false
        lobbyPlayers.add(player.uniqueId)
        val players = lobbyPlayers.mapNotNull { getPlayer(it) }
        mg.onPlayerJoinLobby(player, players)
        checkAndStart(mg, players)
        return true
    }

    fun onPlayerLeaveLobby(player: Player) {
        val mg = pendingStartMinigame ?: return
        lobbyPlayers.remove(player.uniqueId)
        val players = lobbyPlayers.mapNotNull { getPlayer(it) }
        mg.onPlayerLeaveLobby(player, players)
        checkAndStart(mg, players)
    }

    fun onMinigameFinished(minigame: Minigame): Boolean {
        if (ongoingMinigame?.id != minigame.id) return false
        ongoingMinigame = null
        scheduleNextMinigame()
        return true
    }

    private fun notifyChosenForAnnouncement(minigame: Minigame) {
        minigame.onPickedForRotation()
        pendingLobbyMinigame = minigame
    }

    private fun scheduleNextMinigame() {
        schedulingJob?.cancel()
        schedulingJob = launch {
            delay(millisUntilNextEvent)
            notifyChosenForAnnouncement(pickMinigameForRotation())
            schedulingJob = null
        }
    }

    private fun pickMinigameForRotation(): Minigame {
        val candidates = selectionWeights.entries.toList()
        val rng = Random.nextInt(0, candidates.sumBy { it.value })
        var acc = 0
        var res: Minigame? = null
        for ((k, v) in candidates) {
            selectionWeights[k] = v + 1
            if (res == null) {
                acc += v
                if (rng < acc) {
                    res = minigamesInRotation[k]!!
                    selectionWeights[k] = BASE_WEIGHT
                }
            }
        }
        return res!!
    }

    companion object {
        private const val BASE_WEIGHT = 4
    }
}