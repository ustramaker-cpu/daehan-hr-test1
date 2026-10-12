package com.gsitm.framework.security.jwt.store;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class JwtStoreDto{
    private String token;
    private String rtoken;
    private Date expireDate;
}
