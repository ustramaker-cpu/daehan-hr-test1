package com.gsitm.framework.security.core.authority;

import com.gsitm.framework.core.object.UstraObject;
import com.gsitm.framework.security.core.authority.role.UstraAccessType;
import lombok.Getter;
import org.springframework.http.HttpMethod;

@Getter
public class UstraAuthority extends UstraObject {
    private String url;
    private UstraAccessType ustraAccessType;
    private String role;
    private HttpMethod httpMethod;

    public UstraAuthority() {

    }

    public UstraAuthority(String url, UstraAccessType ustraAccessType) {
        this.url = url;
        this.ustraAccessType = ustraAccessType;
    }

    public UstraAuthority(String url, UstraAccessType ustraAccessType, String role) {
        this.url = url;
        this.ustraAccessType = ustraAccessType;
        this.role = role;
    }

    public UstraAuthority(String url, UstraAccessType ustraAccessType, HttpMethod httpMethod) {
        this.url = url;
        this.ustraAccessType = ustraAccessType;
        this.httpMethod = httpMethod;
    }

    public UstraAuthority(String url, UstraAccessType ustraAccessType, String role, HttpMethod httpMethod) {
        this.url = url;
        this.ustraAccessType = ustraAccessType;
        this.role = role;
        this.httpMethod = httpMethod;
    }

    @Override
    public <D> UstraObject of(UstraObject source) {
        return super.of(source, this.getClass());
    }
}
