# Contributing to Kite2Server

Thanks for your interest in contributing! This guide covers the setup, conventions and workflow used in this repository.

## Prerequisites

- JDK 17
- Maven (wrapper included: `mvnw` / `mvnw.cmd`)
- PostgreSQL 16 — only needed to run the full test suite locally; CI provides its own database

## Workflow

### Branches & Commits

- Work on short-lived branches named after the change type: `fix/...`, `chore/...`, `refactor/...`, `test/...`, `docs/...`.
- Commit messages follow [Conventional Commits](https://www.conventionalcommits.org/): `fix:`, `feat:`, `chore:`, `refactor:`, `test:`, `docs:`.
- Open a pull request against `main`. CI must be green before merging (squash merge).

### Tests

- Unit tests run without a database; `@SpringBootTest` context tests use the `test` profile.
- The authoritative gate is **CI** (GitHub Actions, PostgreSQL 16 service, all 39 tests). A failing local run caused by a sleeping local database VM is an environment issue, not a code signal.
- Every security-relevant change (auth, filters, rate limiting, validation) must come with tests.

### Configuration & Secrets

- Never commit secrets, credentials, API keys or log files. Sensitive values live in `.env` (loaded via spring-dotenv) or in environment variables.
- `.env` is gitignored and must stay that way.
- New properties go into `application.yml` with safe defaults; test overrides go into `application-test.yml`.

### Security Rules (non-negotiable)

- All data endpoints (`/data/**`) require authentication.
- New endpoints that need protection must be registered in the security chain (`WebSecurityConfig`, `TokenFilter`).
- Rate limiting is configured via Bucket4j in `application.yml`. Be aware: the filters are **silently inactive** without a cache resolver — the setup requires `@EnableCaching` and the JCache backend (see `CacheConfig`).
- Outbound OpenAI calls go through the circuit breaker in `GptService`; keep the kill-switch (`openai.enabled`) functional.

## Releases & Deployment

1. Bump the version in `pom.xml` (semantic versioning; the build artifact stays `Kite2Server.jar` via `finalName`).
2. After the release PR is merged and CI on `main` is green, tag the release:
   `git tag -a vX.Y.Z -m "Release vX.Y.Z: <short summary>"` → `git push origin vX.Y.Z`
3. Deployment is manual: `mvnw -B clean package -DskipTests` → `scp target/Kite2Server.jar <server>` → `sudo systemctl restart kite2server`.
4. Post-deploy checks: `/start` → 200 · `/data` and `/ai` without credentials → 401 · startup log clean.

## Where Things Live

- `security/` — auth, HMAC validation, token filter, security config
- `gpt/` — OpenAI integration (Spring RestClient + circuit breaker)
- `analytics/` — playthrough and scene-hit metrics
- `data/` — data objects, repository, export
- `config/` — cache setup required by the rate-limiting filters

## License

By contributing to this repository, you agree that your contributions will be
licensed under the MIT License that governs this project
(see [LICENSE_SOFTWARE.txt](https://github.com/HHN/Kite/blob/main/LICENSE_SOFTWARE.txt)).