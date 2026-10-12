param(
 [Parameter(Mandatory=$true)][string]$SourceDir,
 [string]$OutputDir = "$PWD\inc00_evidence"
)
$ErrorActionPreference = 'Stop'
if (-not (Test-Path (Join-Path $SourceDir 'settings.gradle'))) { Write-Error 'SOURCE_DIR_INVALID'; exit 3 }
New-Item -Path $OutputDir -ItemType Directory -Force | Out-Null
Push-Location $SourceDir
try {
  & java -version 2>&1 | Out-File -FilePath (Join-Path $OutputDir 'java_version.log')
  & .\gradlew.bat --version --no-daemon *> (Join-Path $OutputDir 'gradle_version.raw.log')
  $versionRc = $LASTEXITCODE
  Set-Content -Path (Join-Path $OutputDir 'gradle_version.exitcode') -Value "$versionRc"
  if ($versionRc -ne 0) { Write-Output 'GRADLE_WRAPPER_BLOCKED (NOT a compile result)'; exit 20 }
  & .\gradlew.bat --no-daemon ':framework-core:compileJava' ':framework-data:compileJava' ':framework-security:compileJava' ':framework-security-jwt:compileJava' ':framework-web:compileJava' *> (Join-Path $OutputDir 'framework_five.raw.log')
  $frameworkRc=$LASTEXITCODE
  Set-Content -Path (Join-Path $OutputDir 'framework_five.exitcode') -Value "$frameworkRc"
  if ($frameworkRc -ne 0) { Write-Output 'FRAMEWORK_FIVE_FAILED_OR_BLOCKED'; exit 21 }
  & .\gradlew.bat --no-daemon ':ustra-hr-system-thymeleaf:bootWar' *> (Join-Path $OutputDir 'web_bootwar.raw.log')
  $webRc=$LASTEXITCODE
  Set-Content -Path (Join-Path $OutputDir 'web_bootwar.exitcode') -Value "$webRc"
  if ($webRc -ne 0) { Write-Output 'WEB_BOOTWAR_NOT_BUILT'; exit 22 }
  Write-Output 'WEB_BOOTWAR_BUILT (runtime and security tests still pending)'
} finally { Pop-Location }
