package com.hefu.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.DecimalFormat;

public class DoubleSerialize extends JsonSerializer<Double> {
    private DecimalFormat df = new DecimalFormat("0.0");

    @Override
    public void serialize(Double arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException {
        if (arg0 != null) {
            arg1.writeString(df.format(arg0));
        }
    }
}