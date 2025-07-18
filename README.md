# LastServer for Velocity

> A production-ready Velocity plugin that intelligently reconnects players to their last visited server

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Velocity](https://img.shields.io/badge/Velocity-3.3.0+-blue.svg)](https://velocitypowered.com/)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.1.0-purple.svg)](https://kotlinlang.org/)
[![Java](https://img.shields.io/badge/Java-21+-orange.svg)](https://adoptium.net/)

## Overview

LastServer is a high-performance Velocity plugin designed to enhance player experience by automatically reconnecting them to their last visited server. Built with Kotlin and leveraging modern asynchronous programming patterns, it provides a seamless and efficient solution for multi-server Minecraft networks.

### Key Features

- **🔄 Smart Reconnection** - Automatically routes players to their last server on join
- **💾 Persistent Storage** - Survives proxy restarts with efficient data serialization
- **🚫 Flexible Configuration** - Exclude auth, lobby, or any servers via JSON config
- **⚡ Async Architecture** - Built on Kotlin coroutines for non-blocking operations
- **🛡️ Thread-Safe Design** - Concurrent data structures ensure reliability at scale
- **📊 Administrative Tools** - Comprehensive command suite for server management
- **🔍 Debug Support** - Built-in diagnostic mode for troubleshooting
- **⏱️ Intelligent Saving** - Configurable auto-save with write batching

## Requirements

- Velocity 3.3.0 or higher
- Java 21 or higher
- Minecraft 1.21.7 compatible servers

## Installation

### Quick Start
1. Download the latest `LastServer-Kotlin-*.jar` from [releases](https://github.com/ddanrob/LastServerKotlin/releases)
2. Place the JAR in your Velocity server's `plugins/` directory
3. Restart your Velocity proxy
4. (Optional) Customize settings in `plugins/lastserver/config.json`

### First Run
On initial startup, LastServer will:
- Create the configuration directory at `plugins/lastserver/`
- Generate a default `config.json` with sensible defaults
- Initialize the player database at `lastservers.dat`
- Begin tracking player movements immediately

## Configuration

On first run, the plugin creates a `config.json` file:

```json
{
  "excludedServers": [
    "auth",
    "login",
    "lobby"
  ],
  "saveIntervalSeconds": 300,
  "debugMode": false
}
```

### Configuration Options

| Option | Type | Default | Description |
|--------|------|---------|-------------|
| `excludedServers` | String[] | `["auth", "login", "lobby"]` | Servers to exclude from tracking (case-insensitive) |
| `saveIntervalSeconds` | Integer | `300` | Auto-save interval in seconds (0 disables periodic saves) |
| `debugMode` | Boolean | `false` | Enable verbose logging for troubleshooting |

### Configuration Examples

**For networks with custom hub names:**
```json
{
  "excludedServers": ["hub", "auth", "queue", "limbo"],
  "saveIntervalSeconds": 600,
  "debugMode": false
}
```

**For high-traffic networks (reduce I/O):**
```json
{
  "excludedServers": ["lobby"],
  "saveIntervalSeconds": 900,
  "debugMode": false
}
```

## Commands

| Command | Permission | Description |
|---------|------------|-------------|
| `/lastserver` | None | Show help menu |
| `/lastserver clear` | None | Clear your own last server data |
| `/lastserver clear <player>` | `lastserver.admin` | Clear another player's data |
| `/lastserver reload` | `lastserver.admin` | Reload configuration and data |
| `/lastserver list` | `lastserver.admin` | Show count of stored entries |
| `/lastserver check <player>` | `lastserver.admin` | Check a player's last server |
| `/lastserver excluded` | `lastserver.admin` | Show excluded servers list |

### Command Aliases
- `/ls` - Alias for `/lastserver`

## Permissions

- `lastserver.admin` - Access to administrative commands

## Building from Source

### Prerequisites
- JDK 21 or higher
- Git

### Build Instructions
```bash
# Clone the repository
git clone https://github.com/ddanrob/LastServerKotlin.git
cd LastServerKotlin

# Build the plugin (includes all dependencies)
./gradlew clean shadowJar

# Output location
ls -la build/libs/LastServer-Kotlin-*.jar
```

### Development Setup
For IntelliJ IDEA:
1. Import as Gradle project
2. Set project SDK to Java 21
3. Run `./gradlew build` to download dependencies

For VS Code:
1. Install Kotlin and Gradle extensions
2. Open the project folder
3. Run build task from command palette

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request. For major changes, please open an issue first to discuss what you would like to change.

### Development Guidelines
- Follow Kotlin coding conventions
- Add tests for new features
- Update documentation as needed
- Ensure compatibility with latest Velocity API

## Architecture & Technical Details

### Core Technologies
- **Language**: Kotlin 2.1.0 with stdlib
- **Async Framework**: Kotlin Coroutines 1.7.3
- **Build System**: Gradle 8.10.2 with Shadow plugin
- **Target Platform**: Velocity 3.3.0+ API

### Design Patterns
- **Event-Driven Architecture**: Leverages Velocity's event system for player tracking
- **Repository Pattern**: Centralized data management through `LastServerManager`
- **Command Pattern**: Modular command handling with tab completion
- **Dependency Injection**: Via Velocity's Guice integration

### Data Storage
- **Format**: Java serialization (migrating to JSON planned)
- **Location**: `plugins/lastserver/lastservers.dat`
- **Structure**: `ConcurrentHashMap<UUID, String>` for thread safety
- **Persistence**: Automatic saves on changes with configurable intervals

### Performance Optimizations
- **Write Batching**: 5-second delay to group multiple changes
- **Async I/O**: All disk operations run on coroutine IO dispatcher
- **Memory Efficiency**: Minimal footprint with only UUID-to-string mappings
- **Event Priority**: `PostOrder.FIRST` ensures early intervention

## Security

See [SECURITY.md](SECURITY.md) for:
- Security policy and vulnerability reporting
- Best practices for server administrators
- Known security considerations
- Hardening recommendations

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Support & Community

- **Issues**: [GitHub Issue Tracker](https://github.com/ddanrob/LastServerKotlin/issues)
- **Discussions**: [GitHub Discussions](https://github.com/ddanrob/LastServerKotlin/discussions)
- **Security**: Report via GitHub Security Advisories

## Acknowledgments

- Built with ❤️ by [ddanrob](https://github.com/ddanrob)
- Inspired by the needs of the Velocity community
- Special thanks to the Velocity team for their excellent proxy platform

---

<p align="center">
  <i>LastServer - Seamless server reconnection for Velocity networks</i>
</p>