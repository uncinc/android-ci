# Security Policy

1. [Reporting security problems to Unc Inc](#reporting)
2. [Security Point of Contact](#contact)
3. [Vulnerability Management Plans](#vulnerability-management)

<a name="reporting"></a>
## Reporting security issues

**DO NOT CREATE AN ISSUE** to report a security problem. Instead, kindly send an email to security@uncinc.nl

<a name="contact"></a>
## Security Point of Contact

The security point of contact is [Christiaan de Die le Clercq](https://github.com/techwolf12). 

In case Christiaan does not respond within a reasonable time, the secondary point
of contact is [Roderik de Langen](https://github.com/rarothedude). 

<a name="vulnerability-management"></a>
## Vulnerability Management Plans


### Secure accounts with access

All users are required to use 2FA within the [Unc Inc](https://github.com/uncinc) Github organisation.

### Separation of Duties and Authorization
* Developers will only be granted access to repositories they manage.
* Administrative repo tasks will always be done by administrative users.

### Password Policies


#### Don't Use Passwords

We should opt for alternative authentication methods when possible:

* Asymmetric keys for connecting to servers.
* Delegated authentication (SAML, OAuth2, etc).
* Opaque access tokens.

#### SSH Keys

SSH keys should be rolled out selectively, providing developers access to only the servers that they require access to.


### Critical Updates And Security Notices

We learn about critical software updates and security threats from these sources

1. GitHub Security Alerts
2. GitHub: https://status.github.com/ & [@githubstatus](https://twitter.com/githubstatus)


## Credit & License

This Security Policy is based on [npm’s Security Policy](https://www.npmjs.com/policies/security).


This document may be reused under a [Creative Commons Attribution-ShareAlike License](https://creativecommons.org/licenses/by-sa/4.0/).
