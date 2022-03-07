package com.practicum.currencyconverter.di;

import com.practicum.currencyconverter.data.network.ApiProvider;
import com.practicum.currencyconverter.data.network.ExchangerRateService;
import com.practicum.currencyconverter.data.network.ExchangerRateUrlProvider;

public class DI {

    private static ExchangerRateService exchangerRateService;

    public static ExchangerRateService exchangerRateService() {
        if (exchangerRateService == null) {
            exchangerRateService = createExchangerRateService();
        }

        return exchangerRateService;
    }

    private static ExchangerRateService createExchangerRateService() {
        final ExchangerRateUrlProvider urlProvider = new ExchangerRateUrlProvider();
        final ApiProvider apiProvider = new ApiProvider(urlProvider);

        return apiProvider.getExchangerRateService();
    }
}
