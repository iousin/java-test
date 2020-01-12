package com.henry.grocery.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtils {

    public static double round(double value) {
        return new BigDecimal(value).setScale(2, RoundingMode.CEILING).doubleValue();
    }
}
