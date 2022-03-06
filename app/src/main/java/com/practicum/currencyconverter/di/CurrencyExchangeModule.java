package com.practicum.currencyconverter.di;

import com.practicum.currencyconverter.data.mappers.CurrencyExchangeMapper;

class CurrencyExchangeModule {

    CurrencyExchangeMapper getCurrencyExchangeMapper() {
        return new CurrencyExchangeMapper();
    }
}
