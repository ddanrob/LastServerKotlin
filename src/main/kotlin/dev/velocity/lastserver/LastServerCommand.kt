package dev.velocity.lastserver

import com.velocitypowered.api.command.CommandSource
import com.velocitypowered.api.command.SimpleCommand
import com.velocitypowered.api.proxy.Player
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import java.util.concurrent.CompletableFuture

class LastServerCommand(
    private val manager: LastServerManager,
    private val config: LastServerConfig
) : SimpleCommand {
    
    override fun execute(invocation: SimpleCommand.Invocation) {
        val source = invocation.source()
        val args = invocation.arguments()

        when {
            args.isEmpty() -> showHelp(source)
            else -> handleSubcommand(source, args)
        }
    }

    private fun showHelp(source: CommandSource) {
        source.sendMessage(Component.text("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", NamedTextColor.DARK_GRAY))
        source.sendMessage(Component.text("LastServer Plugin Commands", NamedTextColor.GOLD))
        source.sendMessage(Component.text("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", NamedTextColor.DARK_GRAY))
        source.sendMessage(Component.text("/lastserver clear", NamedTextColor.AQUA)
            .append(Component.text(" - Remove your last server record", NamedTextColor.GRAY)))
        
        if (source.hasPermission("lastserver.admin")) {
            source.sendMessage(Component.text("\nAdministrative Commands:", NamedTextColor.GOLD))
            source.sendMessage(Component.text("/lastserver clear <player>", NamedTextColor.AQUA)
                .append(Component.text(" - Clear specific player data", NamedTextColor.GRAY)))
            source.sendMessage(Component.text("/lastserver reload", NamedTextColor.AQUA)
                .append(Component.text(" - Reload configuration from disk", NamedTextColor.GRAY)))
            source.sendMessage(Component.text("/lastserver list", NamedTextColor.AQUA)
                .append(Component.text(" - Display total tracked players", NamedTextColor.GRAY)))
            source.sendMessage(Component.text("/lastserver check <player>", NamedTextColor.AQUA)
                .append(Component.text(" - View player's last server", NamedTextColor.GRAY)))
            source.sendMessage(Component.text("/lastserver excluded", NamedTextColor.AQUA)
                .append(Component.text(" - List excluded server names", NamedTextColor.GRAY)))
        }
        source.sendMessage(Component.text("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", NamedTextColor.DARK_GRAY))
    }

    private fun handleSubcommand(source: CommandSource, args: Array<String>) {
        when (args[0].lowercase()) {
            "clear" -> handleClear(source, args)
            "reload" -> handleReload(source)
            "list" -> handleList(source)
            "check" -> handleCheck(source, args)
            "excluded" -> handleExcluded(source)
            else -> source.sendMessage(Component.text("⚠ ", NamedTextColor.YELLOW)
                .append(Component.text("Unknown command. Use ", NamedTextColor.RED))
                .append(Component.text("/lastserver", NamedTextColor.AQUA))
                .append(Component.text(" for help.", NamedTextColor.RED)))
        }
    }

    private fun handleClear(source: CommandSource, args: Array<String>) {
        when {
            args.size == 1 && source is Player -> {
                manager.clearPlayerData(source.uniqueId)
                source.sendMessage(Component.text("✓ ", NamedTextColor.GREEN)
                    .append(Component.text("Your last server record has been cleared.", NamedTextColor.GREEN)))
            }
            args.size == 2 && source.hasPermission("lastserver.admin") -> {
                // In a real implementation, you'd look up the player by name
                source.sendMessage(Component.text("Player lookup not implemented yet.", NamedTextColor.RED))
            }
            else -> {
                source.sendMessage(Component.text("⚠ ", NamedTextColor.YELLOW)
                    .append(Component.text("Invalid usage. See ", NamedTextColor.RED))
                    .append(Component.text("/lastserver", NamedTextColor.AQUA))
                    .append(Component.text(" for help.", NamedTextColor.RED)))
            }
        }
    }

    private fun handleReload(source: CommandSource) {
        if (source.hasPermission("lastserver.admin")) {
            config.reload()
            manager.loadData()
            source.sendMessage(Component.text("✓ ", NamedTextColor.GREEN)
                .append(Component.text("Configuration and player data reloaded successfully.", NamedTextColor.GREEN)))
        } else {
            source.sendMessage(Component.text("✗ ", NamedTextColor.RED)
                .append(Component.text("Insufficient permissions. This command requires ", NamedTextColor.RED))
                .append(Component.text("lastserver.admin", NamedTextColor.YELLOW)))
        }
    }

    private fun handleList(source: CommandSource) {
        if (source.hasPermission("lastserver.admin")) {
            val count = manager.getAllLastServers().size
            source.sendMessage(Component.text("📊 ", NamedTextColor.AQUA)
                .append(Component.text("Database Statistics", NamedTextColor.GOLD)))
            source.sendMessage(Component.text("  • ", NamedTextColor.DARK_GRAY)
                .append(Component.text("Total tracked players: ", NamedTextColor.GRAY))
                .append(Component.text(count.toString(), NamedTextColor.AQUA)))
        } else {
            source.sendMessage(Component.text("✗ ", NamedTextColor.RED)
                .append(Component.text("Insufficient permissions. This command requires ", NamedTextColor.RED))
                .append(Component.text("lastserver.admin", NamedTextColor.YELLOW)))
        }
    }

    private fun handleCheck(source: CommandSource, args: Array<String>) {
        if (!source.hasPermission("lastserver.admin")) {
            source.sendMessage(Component.text("✗ ", NamedTextColor.RED)
                .append(Component.text("Insufficient permissions. This command requires ", NamedTextColor.RED))
                .append(Component.text("lastserver.admin", NamedTextColor.YELLOW)))
            return
        }
        
        if (args.size < 2) {
            source.sendMessage(Component.text("⚠ ", NamedTextColor.YELLOW)
                .append(Component.text("Usage: ", NamedTextColor.RED))
                .append(Component.text("/lastserver check <player>", NamedTextColor.AQUA)))
            return
        }
        
        // In a real implementation, you'd look up the player
        source.sendMessage(Component.text("Player lookup not implemented yet.", NamedTextColor.RED))
    }
    
    private fun handleExcluded(source: CommandSource) {
        if (source.hasPermission("lastserver.admin")) {
            val excluded = manager.getExcludedServers().joinToString(", ")
            source.sendMessage(Component.text("🚫 ", NamedTextColor.GOLD)
                .append(Component.text("Excluded Servers", NamedTextColor.GOLD)))
            source.sendMessage(Component.text("  ", NamedTextColor.DARK_GRAY)
                .append(Component.text(excluded, NamedTextColor.YELLOW)))
        } else {
            source.sendMessage(Component.text("✗ ", NamedTextColor.RED)
                .append(Component.text("Insufficient permissions. This command requires ", NamedTextColor.RED))
                .append(Component.text("lastserver.admin", NamedTextColor.YELLOW)))
        }
    }

    override fun suggestAsync(invocation: SimpleCommand.Invocation): CompletableFuture<List<String>> {
        val args = invocation.arguments()
        
        return CompletableFuture.completedFuture(
            when {
                args.isEmpty() || args.size == 1 -> {
                    val suggestions = mutableListOf("clear")
                    if (invocation.source().hasPermission("lastserver.admin")) {
                        suggestions.addAll(listOf("reload", "list", "check", "excluded"))
                    }
                    suggestions.filter { it.startsWith(args.getOrNull(0) ?: "", ignoreCase = true) }
                }
                args.size == 2 && args[0].equals("clear", ignoreCase = true) && 
                    invocation.source().hasPermission("lastserver.admin") -> {
                    // Could return online player names here
                    emptyList()
                }
                else -> emptyList()
            }
        )
    }

    override fun hasPermission(invocation: SimpleCommand.Invocation): Boolean = true
}