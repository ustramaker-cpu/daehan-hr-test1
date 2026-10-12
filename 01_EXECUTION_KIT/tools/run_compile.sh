#!/usr/bin/env bash
# Compiles five shared framework modules first, then builds the actual HR web artifact.
# Does NOT start the application or connect to DB.
set -euo pipefail
SOURCE_DIR="${1:?Usage: run_compile.sh /absolute/source-root [output-dir]}"
OUT_DIR="${2:-$(pwd)/inc00_evidence}"
if [ ! -f "$SOURCE_DIR/settings.gradle" ] || [ ! -f "$SOURCE_DIR/gradlew" ]; then
  echo "SOURCE_DIR_INVALID" >&2; exit 3
fi
mkdir -p "$OUT_DIR"
cd "$SOURCE_DIR"
chmod u+x ./gradlew
java -version >"$OUT_DIR/java_version.log" 2>&1
set +e
./gradlew --version --no-daemon >"$OUT_DIR/gradle_version.raw.log" 2>&1
version_rc=$?
set -e
printf '%s\n' "$version_rc" > "$OUT_DIR/gradle_version.exitcode"
if [ "$version_rc" -ne 0 ]; then
  echo "GRADLE_WRAPPER_BLOCKED (exit=$version_rc). See redacted log; NOT a compile result."
  python3 "$(dirname "$0")/redact_logs.py" "$OUT_DIR/gradle_version.raw.log" "$OUT_DIR/gradle_version.REDACTED.log"
  exit 20
fi
set +e
./gradlew --no-daemon \
  :framework-core:compileJava \
  :framework-data:compileJava \
  :framework-security:compileJava \
  :framework-security-jwt:compileJava \
  :framework-web:compileJava >"$OUT_DIR/framework_five.raw.log" 2>&1
framework_rc=$?
set -e
printf '%s\n' "$framework_rc" > "$OUT_DIR/framework_five.exitcode"
python3 "$(dirname "$0")/redact_logs.py" "$OUT_DIR/framework_five.raw.log" "$OUT_DIR/framework_five.REDACTED.log"
if [ "$framework_rc" -ne 0 ]; then
  echo "FRAMEWORK_FIVE_FAIL_OR_BLOCKED (exit=$framework_rc). Distinguish dependencies from source error."
  exit 21
fi
set +e
./gradlew --no-daemon :ustra-hr-system-thymeleaf:bootWar >"$OUT_DIR/web_bootwar.raw.log" 2>&1
build_rc=$?
set -e
printf '%s\n' "$build_rc" > "$OUT_DIR/web_bootwar.exitcode"
python3 "$(dirname "$0")/redact_logs.py" "$OUT_DIR/web_bootwar.raw.log" "$OUT_DIR/web_bootwar.REDACTED.log"
if [ "$build_rc" -ne 0 ]; then
  echo "WEB_BOOTWAR_NOT_BUILT (exit=$build_rc)"; exit 22
fi
echo "COMPILE_FRAMEWORK_AND_WEB_ARTIFACT_PASS: verify .war/.jar output, tests still required."
