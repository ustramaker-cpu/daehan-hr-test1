#!/usr/bin/env python3
"""Best-effort log sanitization. Review manually before sharing externally."""
import re,sys,pathlib
PATTERNS = [
 (r'(?i)(authorization\s*:\s*(?:bearer|basic)\s+)\S+',r'\1<REDACTED>'),
 (r'(?i)(password|secret|access[_-]?token|api[_-]?key|client[_-]?secret|jasypt\.encryptor\.password)(\s*[:=]\s*)[^\s,;]+',r'\1\2<REDACTED>'),
 (r'(?i)(jdbc:[^\s"\']+)', '<JDBC_URL_REDACTED>'),
 (r'\b[A-Za-z0-9_-]{15,}\.[A-Za-z0-9_-]{15,}\.[A-Za-z0-9_-]{10,}\b', '<JWT_REDACTED>'),
 (r'(?i)(https?://)([^/\s:]+)',r'\1<HOST_REDACTED>'),
 (r'\b[^\s@]+@[^\s@]+\.[^\s@]+\b','<EMAIL_REDACTED>'),
]
def redact(s):
 for a,b in PATTERNS:s=re.sub(a,b,s)
 return s
if __name__=='__main__':
 if len(sys.argv)!=3: raise SystemExit('Usage: redact_logs.py RAW_LOG REDACTED_LOG')
 src=pathlib.Path(sys.argv[1]); dst=pathlib.Path(sys.argv[2]);dst.parent.mkdir(parents=True,exist_ok=True)
 dst.write_text(redact(src.read_text(encoding='utf-8',errors='replace')),encoding='utf-8')
 print('REDACTED_LOG_CREATED (manual secure review still required)')
