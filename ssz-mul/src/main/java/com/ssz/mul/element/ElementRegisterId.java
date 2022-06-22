package com.ssz.mul.element;

import com.ssz.mul.constants.MulConstant;

import java.util.Collection;
import java.util.Map;

public class ElementRegisterId implements Element{

    @Override
    public String value(Map<String, Collection<String>> headers) {
        return MulConstant.DEFAULT_REGISTER_ID;
    }

}
