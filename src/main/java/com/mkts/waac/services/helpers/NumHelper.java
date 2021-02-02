package com.mkts.waac.services.helpers;

public class NumHelper {

    private NumHelper() {
        throw new InstantiationError("Static methods only! No need to create instance of!");
    }

    public static Double round (Double value) {
        return (double) Math.round(value*1000000)/1000000;
    }

    public static Double isNull (Double value) {
        return value == null ? 0d : value;
    }
}
