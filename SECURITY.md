# Security Policy

## Supported Versions

We release patches for security vulnerabilities in the following versions:

| Version | Supported          |
| ------- | ------------------ |
| 1.0.x   | :white_check_mark: |
| < 1.0   | :x:                |

## Reporting a Vulnerability

We take the security of LastServer seriously. If you believe you have found a security vulnerability, please report it to us as described below.

### Please do NOT:
- Open a public GitHub issue
- Disclose the vulnerability publicly before a fix is available

### Please DO:
- Report security vulnerabilities via GitHub Security Advisories
- Include the following information:
  - Type of vulnerability
  - Full paths of source file(s) related to the vulnerability
  - Location of the affected source code (tag/branch/commit or direct URL)
  - Step-by-step instructions to reproduce the issue
  - Proof-of-concept or exploit code (if possible)
  - Impact of the issue

### What to expect:
- **Initial Response**: Within 48 hours, we will acknowledge receipt of your report
- **Assessment**: Within 7 days, we will confirm the vulnerability and determine its severity
- **Fix Development**: We will develop a fix and prepare a security update
- **Disclosure**: Once the fix is released, we will publicly disclose the vulnerability

## Security Best Practices for Server Administrators

### File Permissions
- Ensure the `plugins/lastserver/` directory has restricted permissions
- Only the server process should have write access to `lastservers.dat`
- Configuration files should be readable but not writable by other processes

### Network Security
- This plugin does not make any external network connections
- All data is stored locally on the proxy server
- No player data is transmitted to third parties

### Data Privacy
- The plugin only stores UUID-to-server mappings
- No personal player information is collected or stored
- Consider your data retention policies when using this plugin

### Configuration Security
```json
{
  "excludedServers": ["auth", "login", "lobby"],
  "saveIntervalSeconds": 300,
  "debugMode": false
}
```
- Keep `debugMode` disabled in production to avoid verbose logging
- Review excluded servers to ensure auth/hub servers aren't tracked
- Adjust save intervals based on your server's I/O capacity

## Known Security Considerations

### Java Serialization
The plugin uses Java's built-in serialization for data persistence. While convenient, this has known security implications:
- Never manually edit the `lastservers.dat` file
- Ensure the file cannot be replaced by untrusted sources
- Consider implementing JSON-based storage in future versions for better security

### Permission System
- The plugin uses Velocity's permission system
- Ensure `lastserver.admin` permission is only granted to trusted staff
- Regular players only have access to clear their own data

## Security Hardening Checklist

- [ ] Set appropriate file permissions on plugin directory
- [ ] Disable debug mode in production
- [ ] Regularly backup player data
- [ ] Monitor plugin logs for suspicious activity
- [ ] Keep Velocity and the plugin updated
- [ ] Review and audit admin permissions regularly

## Reporting

For security concerns:
- Use GitHub Security Advisories (private by default)
- Open a confidential issue

For general support:
- Use the GitHub issue tracker
- Check existing issues first