package com.practicum.currencyconverter.data.models;

import com.google.gson.annotations.SerializedName;

public class PairConversation {

    @SerializedName("conversion_rate")
    private double conversionRate;

    public double getConversionRate() {
        return conversionRate;
    }
}