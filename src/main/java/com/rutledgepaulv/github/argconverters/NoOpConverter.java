package com.rutledgepaulv.github.argconverters;

import com.rutledgepaulv.github.structs.ConversionInfo;
import com.rutledgepaulv.github.structs.Lazy;

public class NoOpConverter implements StringToQueryValueConverter {

    @Override
    public Lazy<Object> convert(ConversionInfo info) {
        return Lazy.fromValue(info.getArgument());
    }

}
