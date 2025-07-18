# LastServer Quick Start Guide

## 5-Minute Setup

### 1. Download & Install
```bash
# Download the latest release
wget https://github.com/ddanrob/LastServerKotlin/releases/latest/download/LastServer-Kotlin-1.0.0.jar

# Move to your Velocity plugins folder
mv LastServer-Kotlin-1.0.0.jar /path/to/velocity/plugins/

# Restart Velocity
```

### 2. Verify Installation
After restart, you should see in console:
```
[LastServer] LastServer v1.0.0 initialized successfully
```

### 3. Test Basic Functionality
1. Join your network
2. Connect to any non-lobby server
3. Disconnect
4. Rejoin - you should automatically connect to your last server!

### 4. Common Configurations

**Default (works for most networks):**
```json
{
  "excludedServers": ["auth", "login", "lobby"],
  "saveIntervalSeconds": 300,
  "debugMode": false
}
```

**For BungeeCord-style networks:**
```json
{
  "excludedServers": ["hub", "hub-1", "hub-2", "auth"],
  "saveIntervalSeconds": 300,
  "debugMode": false
}
```

**For debugging issues:**
```json
{
  "excludedServers": ["lobby"],
  "saveIntervalSeconds": 60,
  "debugMode": true
}
```

### 5. Verify It's Working
Enable debug mode and watch console:
```
[LastServer] [DEBUG] Tracking server change: PlayerName -> survival
[LastServer] Redirecting player 'PlayerName' to server 'survival'
```

## Troubleshooting

### Players not being redirected?
1. Check if the last server is in excluded list
2. Enable debug mode to see what's happening
3. Ensure the player has permission to join the target server

### Configuration not loading?
- Check file permissions on `plugins/lastserver/config.json`
- Validate JSON syntax (use jsonlint.com)
- Check console for error messages

### High memory usage?
- Increase save interval to reduce I/O
- Check player count with `/lastserver list`
- Consider clearing old data periodically

## Need Help?
- Check [README.md](README.md) for detailed documentation
- Visit [GitHub Issues](https://github.com/ddanrob/LastServerKotlin/issues)
- Enable debug mode for diagnostic information