package com.gsitm.framework.core.dto;

import com.gsitm.framework.core.object.UstraObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class UstraDto extends UstraObject {
    private String loginUserId;
}
