import importlib.util
import pathlib
import stat
import unittest
import zipfile
p=pathlib.Path(__file__).with_name('preflight.py'); spec=importlib.util.spec_from_file_location('inc00_preflight',p)
m=importlib.util.module_from_spec(spec);spec.loader.exec_module(m)
class Tests(unittest.TestCase):
 def test_traversal_rejected(self):
  z=zipfile.ZipInfo('saas-ustra-hr-master/../../bad');self.assertIn('UNSAFE_PATH',m.validate_members([z]))
 def test_foreign_root_rejected(self):
  z=zipfile.ZipInfo('other/gradlew');self.assertIn('UNSAFE_PATH',m.validate_members([z]))
 def test_duplicate_rejected(self):
  z=zipfile.ZipInfo('saas-ustra-hr-master/a');self.assertIn('DUPLICATE_MEMBER',m.validate_members([z,z]))
 def test_symlink_rejected(self):
  z=zipfile.ZipInfo('saas-ustra-hr-master/link');z.external_attr=(stat.S_IFLNK|0o777)<<16
  self.assertIn('SYMLINK_MEMBER',m.validate_members([z]))
if __name__=='__main__':unittest.main(verbosity=2)
