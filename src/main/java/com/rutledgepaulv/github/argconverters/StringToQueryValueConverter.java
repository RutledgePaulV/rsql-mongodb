package com.rutledgepaulv.github.argconverters;

import com.rutledgepaulv.github.structs.ConversionInfo;
import com.rutledgepaulv.github.structs.Lazy;

public interface StringToQueryValueConverter {

    Lazy<Object> convert(ConversionInfo info);

}
