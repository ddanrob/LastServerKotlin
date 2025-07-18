package dev.velocity.lastserver

import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.PostOrder
import com.velocitypowered.api.event.connection.DisconnectEvent
import com.velocitypowered.api.event.player.PlayerChooseInitialServerEvent
import com.velocitypowered.api.event.player.ServerConnectedEvent
import com.velocitypowered.api.proxy.Player
import com.velocitypowered.api.proxy.ProxyServer
import com.velocitypowered.api.proxy.server.RegisteredServer
import kotlinx.coroutines.*
import org.slf4j.Logger
import java.io.*
import java.nio.file.Files
import java.nio.file.Path
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class LastServerManager(
    private val server: ProxyServer,
    private val logger: Logger,
    dataDirectory: Path,
    private val config: LastServerConfig
) {
    private val dataFile: Path = dataDirectory.resolve("lastservers.dat")
    private val lastServers = ConcurrentHashMap<UUID, String>()
    private val saveScope = CoroutineScope(Dispatchers.IO)
    private var saveJob: Job? = null

    init {
        loadData()
        // Schedule periodic saves based on config
        if (config.config.saveIntervalSeconds > 0) {
            saveScope.launch {
                while (isActive) {
                    delay(config.config.saveIntervalSeconds * 1000L)
                    saveData()
                }
            }
        }
    }

    @Subscribe
    fun onServerConnected(event: ServerConnectedEvent) {
        val player = event.player
        val serverName = event.server.serverInfo.name
        
        // Don't save if it's an excluded server (case-insensitive check)
        if (config.config.excludedServers.none { it.equals(serverName, ignoreCase = true) }) {
            lastServers[player.uniqueId] = serverName
            
            if (config.config.debugMode) {
                logger.info("[LastServer] [DEBUG] Tracking server change: ${player.username} -> $serverName")
            }
            
            // Cancel previous save job and schedule new one
            saveJob?.cancel()
            saveJob = saveScope.launch {
                delay(5000) // Wait 5 seconds before saving to batch changes
                saveData()
            }
        } else if (config.config.debugMode) {
            logger.info("[LastServer] [DEBUG] Ignoring server change: ${player.username} -> $serverName (excluded)")
        }
    }

    @Subscribe
    fun onPlayerDisconnect(event: DisconnectEvent) {
        // Optional: Additional logic on disconnect
    }

    @Subscribe(order = PostOrder.FIRST)
    fun onInitialServer(event: PlayerChooseInitialServerEvent) {
        val player = event.player
        val playerId = player.uniqueId
        
        lastServers[playerId]?.let { lastServerName ->
            server.getServer(lastServerName).ifPresent { lastServer ->
                // Set the initial server immediately
                event.setInitialServer(lastServer)
                logger.info("[LastServer] Redirecting player '${player.username}' to server '$lastServerName'")
            }
        }
    }

    fun clearPlayerData(playerId: UUID) {
        lastServers.remove(playerId)
        saveScope.launch { saveData() }
    }

    fun getLastServer(playerId: UUID): String? = lastServers[playerId]

    fun getAllLastServers(): Map<UUID, String> = lastServers.toMap()
    
    fun getExcludedServers(): Set<String> = config.config.excludedServers

    fun loadData() {
        try {
            if (Files.exists(dataFile)) {
                ObjectInputStream(FileInputStream(dataFile.toFile())).use { ois ->
                    @Suppress("UNCHECKED_CAST")
                    val loaded = ois.readObject() as Map<UUID, String>
                    lastServers.putAll(loaded)
                    logger.info("[LastServer] Successfully loaded ${lastServers.size} player entries from disk")
                }
            }
        } catch (e: Exception) {
            logger.error("[LastServer] Failed to load player data from disk", e)
        }
    }

    fun saveData() {
        try {
            Files.createDirectories(dataFile.parent)
            ObjectOutputStream(FileOutputStream(dataFile.toFile())).use { oos ->
                oos.writeObject(lastServers.toMap())
            }
            if (config.config.debugMode) {
                logger.info("[LastServer] [DEBUG] Data persistence complete: ${lastServers.size} entries saved")
            }
        } catch (e: IOException) {
            logger.error("[LastServer] Failed to persist player data to disk", e)
        }
    }

    fun shutdown() {
        runBlocking {
            saveJob?.cancelAndJoin()
            saveScope.coroutineContext.cancelChildren()
            saveData()
        }
    }
}