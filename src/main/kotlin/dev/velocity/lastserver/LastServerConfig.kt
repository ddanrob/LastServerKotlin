package dev.velocity.lastserver

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.slf4j.Logger
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.nio.file.Path

data class Config(
    val excludedServers: Set<String> = setOf("auth", "login", "lobby"),
    val saveIntervalSeconds: Int = 300,
    val debugMode: Boolean = false
)

class LastServerConfig(
    private val dataDirectory: Path,
    private val logger: Logger
) {
    private val configFile = File(dataDirectory.toFile(), "config.json")
    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()
    var config: Config = Config()
        private set

    init {
        loadConfig()
    }

    fun loadConfig() {
        if (!configFile.exists()) {
            saveDefaultConfig()
        }

        try {
            FileReader(configFile).use { reader ->
                config = gson.fromJson(reader, Config::class.java) ?: Config()
                logger.info("Configuration loaded successfully")
                if (config.debugMode) {
                    logger.info("Debug mode enabled")
                    logger.info("Excluded servers: ${config.excludedServers.joinToString(", ")}")
                }
            }
        } catch (e: Exception) {
            logger.error("Failed to load configuration, using defaults", e)
            config = Config()
        }
    }

    private fun saveDefaultConfig() {
        try {
            configFile.parentFile.mkdirs()
            FileWriter(configFile).use { writer ->
                gson.toJson(Config(), writer)
            }
            logger.info("Default configuration saved to ${configFile.absolutePath}")
        } catch (e: Exception) {
            logger.error("Failed to save default configuration", e)
        }
    }

    fun reload() {
        loadConfig()
    }
}