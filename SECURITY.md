# Security Policy

The **JavaPlayground** project takes the security, privacy, and integrity of its repository, source code, and CI automation seriously.

> [!NOTE]
> **Maintainer Notice**: This project is developed and maintained by [**Gabriel Martin G. Guillergan** (@gmguillergan)](https://github.com/gmguillergan). As a solo-maintained educational repository, please allow reasonable time for issue review, verification, and patch releases.

We sincerely appreciate the efforts of security researchers, fellow students, and the community in keeping JavaPlayground safe and reliable.

---

## Supported Versions

Only the active `main` branch receives security updates and fixes.

| Version / Branch | Supported          | Notes |
| :---             | :---:              | :---  |
| `main`           | :white_check_mark: | Actively maintained with the latest fixes |
| Historical tags  | :x:                | Archived classroom snapshots; no longer supported |

---

## Scope & Threat Model

JavaPlayground contains Java implementations, worksheets, and automated verification workflows for Senior High School Computer Programming coursework.

### In-Scope
- Flaws, script injection, or misconfigurations in GitHub Actions workflows (`.github/workflows/ci.yml`).
- Arbitrary code execution vectors, insecure deserialization, or severe input handling flaws in utility and worksheet implementations.
- Inadvertent exposure of sensitive credentials, tokens, or private keys within repository commits.

### Out-of-Scope
- Physical compromise, social engineering, or phishing targeting the maintainer.
- Flaws purely within upstream environments (OpenJDK, Oracle JDK, Apache NetBeans, Apache Ant) unless a direct, exploitable vector exists within JavaPlayground.
- Intentional denial-of-service against personal developer machines running standard console input (`java.util.Scanner`).

---

## Reporting a Vulnerability

If you discover a security vulnerability or potential issue in JavaPlayground, please disclose it responsibly.

> [!WARNING]
> **Please do NOT open public GitHub issues, discussions, or pull requests for undisclosed vulnerabilities.**

### Contact Channels

- **Primary Email**: [riel@gmguillergan.com](mailto:riel@gmguillergan.com)
- **Keybase**: [gmguillergan](https://keybase.io/gmguillergan)
- **PGP Key ID**: `CC6EFF965CC439DD`
- **PGP Key URL**: [https://github.com/gmguillergan.gpg](https://github.com/gmguillergan.gpg)

### Encrypting Your Report (Recommended)

To protect sensitive vulnerability data in transit, you are encouraged to encrypt your email using the maintainer's PGP key:

```bash
# Import key via GitHub
curl -s https://github.com/gmguillergan.gpg | gpg --import

# Or import via Keybase
curl -s https://keybase.io/gmguillergan/pgp_keys.asc | gpg --import
```

Verify the imported key ID:

```bash
gpg --list-keys CC6EFF965CC439DD
```

---

## What to Include in Your Report

To help triage and address the issue quickly, please provide:

1. A concise description and potential impact.
2. Specific source files (e.g., `Quarter-2/2Q_Guillergan_ICT 1/src/...` or `.github/workflows/ci.yml`).
3. Step-by-step instructions or a minimal Proof of Concept (PoC).
4. Java version, operating system, and build tools used during testing.
5. A proposed patch or mitigation (if available).

---

## Response & Disclosure Process

1. You will receive an initial response within **48 to 72 hours**.
2. The issue will be investigated and verified.
3. A patch will be developed, tested, and reviewed.
4. Once merged into `main`, a security advisory and release notes will be published.
5. You will be credited in the release notes (unless you prefer to remain anonymous).

---

## Responsible Research & Safe Harbor

We consider security research conducted in accordance with this policy to be authorized and lawful.

We will not initiate or pursue legal action under applicable cybersecurity and data protection laws, including the **Philippine Cybercrime Prevention Act of 2012 (Republic Act No. 10175)** and the **Data Privacy Act of 2012 (Republic Act No. 10173)**, against security researchers who:

- Act in good faith to avoid privacy violations, data destruction, and service degradation.
- Provide the maintainer reasonable time for investigation and remediation before disclosing findings publicly.
- Limit testing strictly to demonstrating proof-of-concept without exploiting the vulnerability.
- Comply with ethical disclosure principles and applicable laws within the jurisdiction of the Republic of the Philippines.
