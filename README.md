## Kite2Server

Kite2Server ist ein Spring Boot (Java) Backend-Projekt mit REST-APIs für Datenverwaltung und GPT-Integration.

Kurzüberblick
- Java: 17
- Spring Boot: 3.x (Parent im `pom.xml` 3.1.0)
- Hauptklasse: `com.hhn.kite2server.Kite2ServerApplication`
- Build: Maven (`./mvnw` / `mvnw.cmd` auf Windows)

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
- Mailversand: Konfiguration unter `spring.mail` in `application.yml` prüfen (Host, Port, Credentials)
- TLS/SSL: Falls mit SSL gestartet werden soll, `server.ssl.enabled: true` setzen und `key-store`/`key-store-password` konfigurieren.

## Entwicklung & Contribution
- Branch-Workflow: Feature-Branches, PRs gegen `main`.
- Code-Format: Projekt nutzt Standard-Java-Formatierung; Nutzt Lombok in Teilen des Codes.
- Bitte keine Secrets committen — stattdessen `.env` oder CI-Secret-Store verwenden.

## Tests
- Unit- und Integrationstests lassen sich mit `./mvnw test` bzw. `mvnw.cmd test` ausführen.

## Lizenz
MIT-Lizenz

## Kontakt
Bei Fragen: Repository-Maintainer oder Projektverantwortliche im Projekt kontaktieren.

---
Kurzzusammenfassung: Diese README bietet einen schnellen Einstieg zum Bauen, Starten und Konfigurieren des Projekts. Passe `application.yml` und Umgebungsvariablen an deine Umgebung an, insbesondere DB- und API-Keys.
