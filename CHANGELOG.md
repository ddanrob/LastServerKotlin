# Changelog

All notable changes to LastServer will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.0] - 2025-07-18

### Added
- Initial release of LastServer for Velocity
- Automatic player reconnection to last visited server
- Configurable server exclusion list via JSON configuration
- Comprehensive admin command system with tab completion
- Debug mode for troubleshooting
- Configurable auto-save intervals with intelligent batching
- Thread-safe data persistence using Kotlin coroutines
- Professional logging with consistent formatting
- Security documentation and best practices guide

### Technical Features
- Built with Kotlin 2.1.0 for modern, safe code
- Leverages Kotlin coroutines for non-blocking I/O
- Uses Velocity 3.3.0+ event system with proper priorities
- Implements concurrent data structures for thread safety
- Includes Gson for future JSON migration path

### Commands
- `/lastserver` - Display help menu
- `/lastserver clear` - Clear personal data
- `/lastserver clear <player>` - Admin: Clear specific player data
- `/lastserver reload` - Admin: Reload configuration
- `/lastserver list` - Admin: Show statistics
- `/lastserver check <player>` - Admin: Check player's last server
- `/lastserver excluded` - Admin: List excluded servers

### Security
- No external network connections
- Local data storage only
- Permission-based command access
- Comprehensive security documentation

[1.0.0]: https://github.com/ddanrob/LastServerKotlin/releases/tag/v1.0.0