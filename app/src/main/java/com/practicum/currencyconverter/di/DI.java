package com.practicum.currencyconverter.di;

import com.practicum.currencyconverter.data.mappers.CurrencyExchangeMapper;
import com.practicum.currencyconverter.data.network.ExchangerRateService;

public class DI {

    public static ExchangerRateService exchangerRateService() {
        return new NetworkModule().getExchangerRateService();
    }

    public static CurrencyExchangeMapper getCurrencyExchangeMapper() {
        return new CurrencyExchangeModule().getCurrencyExchangeMapper();
    }
}
