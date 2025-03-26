package com.eup.fms.server.lib.api.crm.object;

import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TemperatureBarcode {

    private final String barcode;
    private final List<Double> coefficients;

    public TemperatureBarcode(String barcode, String coefficientStr) {
        this.barcode = barcode;
        List<Double> coefficients = new ArrayList<>();
        if (!Strings.isNullOrEmpty(coefficientStr)) {
            String[] coefficientArray = coefficientStr.split(",");
            coefficients.add(Double.parseDouble(coefficientArray[0]));
            coefficients.add(Double.parseDouble(coefficientArray[1]));
            coefficients.add(Double.parseDouble(coefficientArray[2]));
        }
        this.coefficients = Collections.unmodifiableList(coefficients);
    }

    public String getBarcode() {
        return barcode;
    }

    public List<Double> getCoefficients() {
        return coefficients;
    }
}
