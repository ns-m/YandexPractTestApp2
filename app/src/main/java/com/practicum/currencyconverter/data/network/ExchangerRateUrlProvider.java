package com.practicum.currencyconverter.data.network;

public class ExchangerRateUrlProvider {

    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";

    public String provideBaseUrl() {
        return BASE_URL;
    }
}
