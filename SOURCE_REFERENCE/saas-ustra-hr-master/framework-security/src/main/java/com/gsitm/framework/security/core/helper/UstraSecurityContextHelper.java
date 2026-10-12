package com.gsitm.framework.security.core.helper;


import com.gsitm.framework.security.core.authentication.UstraAuthentication;
import com.gsitm.framework.security.core.user.UstraUserDetails;

public abstract class UstraSecurityContextHelper {

    abstract UstraAuthentication getAuthentication();

    abstract UstraUserDetails getUserDetails();

}
