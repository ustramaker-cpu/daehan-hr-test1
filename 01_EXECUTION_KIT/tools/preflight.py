#!/usr/bin/env python3
"""Reproducible, read-only source SHA + clean-copy setup for DFNE INC-00.
No DB access, no network access, no application execution.
"""
from __future__ import annotations
import argparse
import datetime as dt
import hashlib
import json
import os
import re
import shutil
import stat
import subprocess
import sys
import zipfile
from pathlib import Path, PurePosixPath

EXPECTED_SOURCE_SHA256 = "0681abdd0a320315e0a3eea49468bd524f0faa131f47680eff952c9538a4b026"
EXPECTED_ROOT = "saas-ustra-hr-master"
REQUIRED = (
    "gradlew",
    "gradlew.bat",
    "settings.gradle",
    "build.gradle",
    "gradle/wrapper/gradle-wrapper.jar",
    "gradle/wrapper/gradle-wrapper.properties",
    "ustra-hr-system-thymeleaf/src/main/java/hr/system/thymeleaf/FrameworkSystemThymeleafApplication.java",
    "ustra-hr-system-restapi/src/main/java/hr/system/restapi/FrameworkSystemRestAPIApplication.java",
    "ustra-hr-system-core/src/main/resources/application-api.yml",
)
FRAMEWORK = (
    "framework-core",
    "framework-data",
    "framework-security",
    "framework-security-jwt",
    "framework-web",
)

def sha256_file(path: Path) -> str:
    h = hashlib.sha256()
    with path.open("rb") as f:
        for chunk in iter(lambda: f.read(2 * 1024 * 1024), b""):
            h.update(chunk)
    return h.hexdigest()

def validate_members(infos: list[zipfile.ZipInfo]) -> list[str]:
    errors = []
    seen = set()
    total = 0
    for x in infos:
        name = x.filename
        parts = PurePosixPath(name).parts
        if not parts or parts[0] != EXPECTED_ROOT or ".." in parts or name.startswith("/") or "\\" in name or ":" in name:
            errors.append("UNSAFE_PATH")
            continue
        if stat.S_ISLNK(x.external_attr >> 16):
            errors.append("SYMLINK_MEMBER")
        if name in seen:
            errors.append("DUPLICATE_MEMBER")
        seen.add(name)
        total += x.file_size
    if len(infos) > 20000 or total > 1_000_000_000:
        errors.append("ARCHIVE_LIMIT_EXCEEDED")
    return sorted(set(errors))

def readme_report(z: zipfile.ZipFile, names: set[str]) -> dict:
    root = EXPECTED_ROOT + "/"
    missing = [p for p in REQUIRED if root + p not in names]
    modules = sorted({p.split("/")[1] for p in names if p.startswith(root) and p.count("/") == 2 and p.endswith("/build.gradle")})
    root_gradle = z.read(root + "build.gradle").decode("utf-8", "replace") if root+"build.gradle" in names else ""
    settings = z.read(root + "settings.gradle").decode("utf-8", "replace") if root+"settings.gradle" in names else ""
    wrapper = z.read(root + "gradle/wrapper/gradle-wrapper.properties").decode("utf-8", "replace") if root+"gradle/wrapper/gradle-wrapper.properties" in names else ""
    versions = re.findall(r"springBootVersion\s*=\s*['\"]([^'\"]+)", root_gradle)
    distros = re.findall(r"gradle-(\d+\.\d+(?:\.\d+)?)-bin\.zip", wrapper)
    expected_modules = re.findall(r"^\s*include\s+['\"]([^'\"]+)['\"]", settings, re.M)
    secrets = []
    for n in names:
        low = n.lower()
        if low.endswith("/.env") or low.endswith(".p12") or low.endswith(".pfx") or low.endswith(".jks") or low.endswith(".pem") or low.endswith(".key"):
            secrets.append(n[len(root):] if n.startswith(root) else "<REDACTED>")
    return {
        "required_files_missing": missing,
        "boot_version_declared": versions[0] if versions else "UNKNOWN",
        "gradle_distribution_version": distros[0] if distros else "UNKNOWN",
        "included_modules_count": len(expected_modules),
        "module_gradle_count": len(modules),
        "module_gradle_names": modules,
        "needs_secure_config_review": bool(secrets),
        "sensitive_filename_candidates_count": len(secrets),
        "external_dependencies_not_bundled": True,
        "db_schema_not_provided_or_verified": True,
        "hr3_candidate_not_auto_merged": True,
    }

def extract_safe(z: zipfile.ZipFile, dest: Path):
    if dest.exists() and any(dest.iterdir()):
        raise RuntimeError("Destination is not empty; refusing to overwrite")
    dest.mkdir(parents=True, exist_ok=True)
    for info in z.infolist():
        relative = PurePosixPath(info.filename)
        target = dest.joinpath(*relative.parts)
        if info.is_dir():
            target.mkdir(parents=True, exist_ok=True)
        else:
            target.parent.mkdir(parents=True, exist_ok=True)
            with z.open(info) as src, target.open("wb") as dst:
                shutil.copyfileobj(src, dst, 1024 * 1024)
    gradlew = dest / EXPECTED_ROOT / "gradlew"
    if gradlew.is_file():
        gradlew.chmod(gradlew.stat().st_mode | stat.S_IXUSR)

def main() -> int:
    pa=argparse.ArgumentParser(description=__doc__)
    pa.add_argument("--source-zip",required=True,type=Path)
    pa.add_argument("--extract-to",type=Path,default=None)
    pa.add_argument("--report",type=Path,default=None)
    args=pa.parse_args()
    p=args.source_zip.resolve()
    if not p.is_file():
        print("SOURCE_MISSING: place the original source zip in the expected folder",file=sys.stderr)
        return 3
    sha=sha256_file(p)
    result={"work":"DFNE-HR INC-00", "time_utc":dt.datetime.now(dt.timezone.utc).isoformat(timespec="seconds"),
            "source_name":p.name,"source_sha256":sha,"source_sha_matches_baseline":sha==EXPECTED_SOURCE_SHA256,
            "actions":{"compile":"NOT_RUN", "boot":"NOT_RUN","db":"NOT_RUN"}}
    if sha != EXPECTED_SOURCE_SHA256:
        print("SOURCE_SHA_MISMATCH: stop and verify Baseline",file=sys.stderr)
        if args.report:
            args.report.parent.mkdir(parents=True,exist_ok=True);args.report.write_text(json.dumps(result,ensure_ascii=False,indent=2),encoding="utf-8")
        return 4
    with zipfile.ZipFile(p) as z:
        names=set(z.namelist()); violations=validate_members(z.infolist())
        result["archive_errors"]=violations
        result["zip_members_total"]=len(z.infolist())
        result["source_inventory"]=readme_report(z,names)
        if violations or result["source_inventory"]["required_files_missing"]:
            print("ARCHIVE_INVALID: missing required paths or unsafe archive",file=sys.stderr)
            return 5
        if args.extract_to:
            extract_safe(z,args.extract_to.resolve())
            result["extraction"]="COPY_EXTRACTED_SOURCE_UNMODIFIED"
    if args.report:
        args.report.parent.mkdir(parents=True,exist_ok=True)
        args.report.write_text(json.dumps(result,ensure_ascii=False,indent=2),encoding="utf-8")
    print("PASS: source SHA and structural inventory checked; compile, DB and boot NOT RUN")
    return 0
if __name__ == "__main__":
    raise SystemExit(main())
