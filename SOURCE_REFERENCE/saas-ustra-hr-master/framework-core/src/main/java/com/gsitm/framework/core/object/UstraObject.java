package com.gsitm.framework.core.object;

import com.gsitm.framework.core.utils.ModelMapperUtils;

public abstract class UstraObject {

    public abstract <D> UstraObject of(UstraObject source);

    protected <D> D of(UstraObject source, Class<D> destinationType) {
        if (source==null) {
            return null;
        }
        return ModelMapperUtils.getModelMapper().map(source, destinationType);
    }
}
