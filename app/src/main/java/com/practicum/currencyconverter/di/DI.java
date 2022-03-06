package com.practicum.currencyconverter.di;

import com.practicum.currencyconverter.data.network.ExchangerRateService;

public class DI {

    public static ExchangerRateService exchangerRateService() {
        return NetworkModule.getExchangerRateService();
    }
}
