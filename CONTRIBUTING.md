# Contributing to LastServer

First off, thank you for considering contributing to LastServer! It's people like you that make LastServer such a great tool for the Velocity community.

## Code of Conduct

By participating in this project, you are expected to uphold our values:
- Be respectful and inclusive
- Welcome newcomers and help them get started
- Focus on what is best for the community
- Show empathy towards other community members

## How Can I Contribute?

### Reporting Bugs

Before creating bug reports, please check existing issues to avoid duplicates. When creating a bug report, include:

- **Clear and descriptive title**
- **Velocity version** and **Java version**
- **Steps to reproduce** the issue
- **Expected behavior** vs **actual behavior**
- **Stack traces** or error logs if applicable
- **Configuration file** (with sensitive data removed)

### Suggesting Enhancements

Enhancement suggestions are tracked as GitHub issues. When creating an enhancement suggestion, include:

- **Use case** - Why is this enhancement useful?
- **Proposed solution** - How should it work?
- **Alternative solutions** - What other approaches did you consider?
- **Additional context** - Screenshots, mockups, or examples

### Pull Requests

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Make your changes
4. Write or update tests as needed
5. Ensure the project builds (`./gradlew clean build`)
6. Commit your changes using clear, descriptive messages
7. Push to your fork (`git push origin feature/amazing-feature`)
8. Open a Pull Request

## Development Setup

### Prerequisites
- JDK 21 or higher
- IntelliJ IDEA (recommended) or VS Code with Kotlin support
- Git

### Local Development
```bash
# Clone your fork
git clone https://github.com/your-username/LastServerKotlin.git
cd LastServerKotlin

# Build the project
./gradlew build

# Run tests (when available)
./gradlew test
```

## Coding Standards

### Kotlin Style Guide
- Follow [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Use meaningful variable and function names
- Add comments for complex logic
- Keep functions focused and small
- Use Kotlin idioms and features appropriately

### Code Structure
```
src/main/kotlin/dev/velocity/lastserver/
├── LastServerPlugin.kt      # Main plugin class
├── LastServerManager.kt     # Core business logic
├── LastServerCommand.kt     # Command handling
└── LastServerConfig.kt      # Configuration management
```

### Best Practices
- **Immutability**: Prefer `val` over `var`
- **Null Safety**: Use Kotlin's null safety features
- **Coroutines**: Use for async operations
- **Error Handling**: Handle exceptions gracefully
- **Logging**: Use appropriate log levels

### Example Code Style
```kotlin
class ExampleManager(
    private val server: ProxyServer,
    private val logger: Logger
) {
    fun processPlayer(player: Player) {
        // Use descriptive names and Kotlin idioms
        player.currentServer.ifPresent { server ->
            logger.info("[LastServer] Processing ${player.username}")
            // Implementation here
        }
    }
}
```

## Testing

- Write unit tests for new functionality
- Ensure existing tests pass
- Test with multiple Velocity versions
- Test with various network configurations

## Documentation

- Update README.md if adding new features
- Add KDoc comments to public APIs
- Update configuration examples if needed
- Include usage examples for new commands

## Commit Messages

Follow these guidelines for commit messages:

- Use present tense ("Add feature" not "Added feature")
- Use imperative mood ("Move cursor to..." not "Moves cursor to...")
- Limit first line to 72 characters
- Reference issues and pull requests when relevant

### Examples:
```
Add support for wildcard server exclusions

- Implement pattern matching for server names
- Update configuration documentation
- Add unit tests for wildcard matching

Fixes #123
```

## Release Process

1. Update version in `build.gradle.kts` and `velocity-plugin.json`
2. Update CHANGELOG.md with release notes
3. Create a GitHub release with:
   - Version tag (e.g., `v1.1.0`)
   - Release notes highlighting changes
   - Attached JAR file from `build/libs/`

## Questions?

Feel free to:
- Open an issue for clarification
- Start a discussion in GitHub Discussions
- Reach out to maintainers

Thank you for contributing! 🎉