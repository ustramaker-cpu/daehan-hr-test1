#!/usr/bin/env bash
# Explicitly guarded: never auto-runs production config or nonisolated services.
set -euo pipefail
SOURCE_DIR="${1:?Usage: run_web_local.sh /absolute/source-root}"
if [ "${DFNE_ISOLATED_SANDBOX_ACK:-}" != "I_CONFIRM_NONPROD" ]; then
  echo 'BLOCKED: set DFNE_ISOLATED_SANDBOX_ACK=I_CONFIRM_NONPROD after verifying test-only external integrations' >&2
  exit 30
fi
if [ "${DFNE_EXTERNAL_ENDPOINTS_ISOLATED:-}" != "YES" ]; then
  echo 'BLOCKED: validate all outbound integration endpoints, scheduled tasks, files, mail and connectors are isolated' >&2
  exit 31
fi
if [ -z "${SPRING_DATASOURCE_URL:-}" ] || [ -z "${SPRING_DATASOURCE_USERNAME:-}" ] || [ -z "${SPRING_DATASOURCE_PASSWORD:-}" ]; then
  echo 'BLOCKED: supply isolated test DB connection in process environment; NEVER store credentials in scripts' >&2
  exit 32
fi
case "$SPRING_DATASOURCE_URL" in
  jdbc:*://localhost:*|jdbc:*://127.0.0.1:*) ;;
  *) echo 'BLOCKED: first launch only accepts localhost/127.0.0.1 test DB URL' >&2; exit 33;;
esac
if [ ! -f "$SOURCE_DIR/settings.gradle" ]; then echo 'BLOCKED: source root missing' >&2;exit 34;fi
cd "$SOURCE_DIR"
# Spring profiles are specified explicitly, but the custom application builder
# loads classpath *.yml. Verify effective settings before production use.
exec ./gradlew --no-daemon :ustra-hr-system-thymeleaf:bootRun --args='--spring.profiles.active=core,web,api,local'
