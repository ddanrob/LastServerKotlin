package dev.velocity.lastserver

import com.google.inject.Inject
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.ProxyServer
import org.slf4j.Logger
import java.nio.file.Path

@Plugin(
    id = "lastserver",
    name = "LastServer",
    version = "1.0.0",
    description = "Intelligent last server reconnection system for Velocity networks",
    url = "https://github.com/ddanrob/LastServerKotlin",
    authors = ["ddanrob"]
)
class LastServerPlugin @Inject constructor(
    private val server: ProxyServer,
    private val logger: Logger,
    @DataDirectory private val dataDirectory: Path
) {
    private lateinit var config: LastServerConfig
    private lateinit var lastServerManager: LastServerManager

    @Subscribe
    fun onProxyInitialization(event: ProxyInitializeEvent) {
        // Initialize configuration
        config = LastServerConfig(dataDirectory, logger)
        
        // Initialize the manager with config
        lastServerManager = LastServerManager(server, logger, dataDirectory, config)
        
        // Register event listeners
        server.eventManager.register(this, lastServerManager)
        
        // Register commands
        registerCommands()
        
        logger.info("LastServer v${javaClass.getPackage().implementationVersion ?: "1.0.0"} initialized successfully")
    }

    @Subscribe
    fun onProxyShutdown(event: ProxyShutdownEvent) {
        // Save data on shutdown
        lastServerManager.shutdown()
        logger.info("LastServer: Shutdown complete, player data saved")
    }

    private fun registerCommands() {
        val commandMeta = server.commandManager.metaBuilder("lastserver")
            .aliases("ls")
            .plugin(this)
            .build()
        
        server.commandManager.register(commandMeta, LastServerCommand(lastServerManager, config))
    }
}