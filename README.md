# Kite2Server

Kite2Server is a Spring Boot (Java) backend project featuring REST APIs for data management and GPT integration.

Quick Overview
- Java: 17
- Spring Boot: 3.x (Parent in `pom.xml` 3.1.0)
- Main Class: `com.hhn.kite2server.Kite2ServerApplication`
- Build Tool: Maven (uses `./mvnw` / `mvnw.cmd` on Windows)

Table of Contents
- Prerequisites
- Build & Start
- Configuration
- Key Modules / Controllers
- Troubleshooting
- Development & Contribution
- Tests
- License

## Prerequisites
- JDK 17 installed and added to PATH
- Maven (or the provided wrapper script mvnw / mvnw.cmd)
- PostgreSQL (or a configured database) 

## Build & Start
On Windows (PowerShell):

```powershell
# Build
.\mvnw.cmd clean package

# Starten (erzeugte JAR im Ordner target, finalName = Kite2Server)
java -jar .\target\Kite2Server.jar

# Alternativ direkt mit dem Maven Spring Boot Plugin (zum Entwickeln)
.\mvnw.cmd spring-boot:run
```

On Unix/macOS:

```bash
./mvnw clean package
java -jar ./target/Kite2Server.jar
```

Running Tests:

```powershell
.\mvnw.cmd test
```

## Configuration
The central configuration is located in `src/main/resources/application.yml`. Key settings include:
- `server.port`: Default is 8080
- `spring.datasource.*`: JDBC URL, username, and password (current values are examples — must be updated)
- `openai.api.key` / Environment Variables: The project utilizes `spring-dotenv`. Sensitive keys should be stored in a `.env` file or set as environment variables. Essential variables:
  - `OPENAI_API_KEY` (API key for GPT)
  - `OPENAI_MODEL` (e.g., gpt-4o)
  - `API_USERNAME` / `API_PASSWORD` (if applicable)
- SSL Keystore: `src/main/resources/keystore/keystore.p12` (optional).

Important: The example passwords in the repository are placeholders. Never commit sensitive data to the repository.

## Key Modules / Controllers

The project is structured into packages under `com.hhn.kite2server`. Important sub-packages and controllers include:
- `data`: `DataController`, `DataService`, `DataRepository`
- `gpt`: `GptController`, `GptService`, `GptConfig`

The main entry point is `com.hhn.kite2server.Kite2ServerApplication`.

## Logging & Management
- Actuator: Enabled (exposed endpoints are configurable in `application.yml`)
- Access Logs: Configured in `application.yml` via Tomcat accesslog.

## Troubleshooting
- Database Connection Failed: Verify `spring.datasource.url`, username, password, and ensure PostgreSQL is reachable.
- TLS/SSL: To start with SSL, set `server.ssl.enabled`: true and configure `key-store` / `key-store-password`.

## Development & Contribution
- Branch Workflow: Use feature branches and submit Pull Requests (PRs) against `main`.
- Code Format: The project follows standard Java formatting and uses Lombok in several parts of the code.
- Security: Do not commit secrets — use `.env` files or a CI secret store instead.

## Tests
- Unit and integration tests can be executed using `./mvnw test` or `mvnw.cmd` test.

## License
The source code is released under the **MIT License**.
For details, see [LICENSE_SOFTWARE.txt](LICENSE_SOFTWARE.txt)

Summary: This README provides a quick start guide for building, running, and configuring the project. Ensure you adapt the `application.yml` and environment variables to your environment, particularly the database and API keys.

---

# Kite2Server

Kite2Server ist ein Spring Boot (Java) Backend-Projekt mit REST-APIs für Datenverwaltung und GPT-Integration.

Kurzüberblick
- Java: 17
- Spring Boot: 3.x (Parent im `pom.xml` 3.1.0)
- Hauptklasse: `com.hhn.kite2server.Kite2ServerApplication`
- Build Tool: Maven (`./mvnw` / `mvnw.cmd` auf Windows)

Inhalt dieser README
- Voraussetzungen
- Build & Start
- Konfiguration
- Wichtige Module / Controller
- Troubleshooting

## Voraussetzungen
- JDK 17 installiert und in PATH
- Maven (oder das mitgelieferte Wrapper-Skript `mvnw` / `mvnw.cmd`)
- PostgreSQL (oder eine konfigurierte Datenbank)

## Build & Start
Auf Windows (Powershell):

```powershell
# Build
.\mvnw.cmd clean package

# Starten (erzeugte JAR im Ordner target, finalName = Kite2Server)
java -jar .\target\Kite2Server.jar

# Alternativ direkt mit dem Maven Spring Boot Plugin (zum Entwickeln)
.\mvnw.cmd spring-boot:run
```

Auf Unix/macOS:

```bash
./mvnw clean package
java -jar ./target/Kite2Server.jar
```

Für Tests:

```powershell
.\mvnw.cmd test
```

## Konfiguration
Zentrale Konfiguration liegt in `src/main/resources/application.yml`. Wichtige Punkte:
- `server.port`: Standard 8080
- `spring.datasource.*`: JDBC URL, Nutzer und Passwort (aktuell Beispielwerte — unbedingt anpassen)
- `openai.api.key` bzw. Umgebungsvariablen: Das Projekt nutzt `spring-dotenv` — sensitive Keys sollten in einer `.env` liegen oder als Umgebungsvariablen gesetzt werden. Wichtige Variablen:
  - `OPENAI_API_KEY` (API-Key für GPT)
  - `OPENAI_MODEL` (z. B. `gpt-4o`)
  - `API_USERNAME` / `API_PASSWORD` (falls genutzt)
- SSL-Keystore: `src/main/resources/keystore/keystore.p12` (optional; im `application.yml` als Kommentar vorhanden)

Wichtig: Die im Repository vorhandenen Beispielpasswörter sind Platzhalter. Niemals sensible Daten ins Repo committen.

## Wichtige Module / Controller
Das Projekt ist in Pakete unter `com.hhn.kite2server` strukturiert. Wichtige Subpakete und Controller (nicht vollständig):
- `data` — `DataController`, `DataService`, `DataRepository`
- `gpt` — `GptController`, `GptService`, `GptConfig`

Die Hauptklasse ist `com.hhn.kite2server.Kite2ServerApplication`.

## Logging & Management
- Actuator ist aktiviert (exposed endpoints konfigurierbar in `application.yml`)
- Access-Logs sind in `application.yml` konfiguriert (Tomcat accesslog)

## Troubleshooting
- Datenbankverbindung schlägt fehl: Prüfe `spring.datasource.url`, Nutzer, Passwort und ob Postgres erreichbar ist.
- TLS/SSL: Falls mit SSL gestartet werden soll, `server.ssl.enabled: true` setzen und `key-store`/`key-store-password` konfigurieren.

## Entwicklung & Contribution
- Branch-Workflow: Feature-Branches, PRs gegen `main`.
- Code-Format: Projekt nutzt Standard-Java-Formatierung; Nutzt Lombok in Teilen des Codes.
- Bitte keine Secrets committen — stattdessen `.env` oder CI-Secret-Store verwenden.

## Tests
- Unit- und Integrationstests lassen sich mit `./mvnw test` bzw. `mvnw.cmd test` ausführen.

## Lizenz
Der Quellcode steht unter der **MIT-Lizenz**.
Details siehe [LICENSE_SOFTWARE.txt](LICENSE_SOFTWARE.txt)

Kurzzusammenfassung: Diese README bietet einen schnellen Einstieg zum Bauen, Starten und Konfigurieren des Projekts. Passe `application.yml` und Umgebungsvariablen an deine Umgebung an, insbesondere DB- und API-Keys.
