package com.practicum.currencyconverter.data.mappers;

import com.practicum.currencyconverter.data.models.Currency;
import com.practicum.currencyconverter.data.models.CurrencyExchange;
import com.practicum.currencyconverter.presentation.exchangerate.ExchangeRateVo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class CurrencyExchangeMapper {

    public List<ExchangeRateVo> map(final Currency currentCurrency, final CurrencyExchange currencyExchange) {
        final Currency[] currencies = Currency.values();
        final List<ExchangeRateVo> exchangeRateVos = new ArrayList<>(currencies.length);

        for (final Currency currency : currencies) {
            double currencyRate = 0.0;
            try {
                final Method method = currencyExchange.getConversionRates().getClass().getMethod(currency.getCode());
                currencyRate = (double) method.invoke(currencyExchange.getConversionRates());
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }

            final ExchangeRateVo exchangeRateVo = new ExchangeRateVo(
                    currentCurrency,
                    currency,
                    String.valueOf(currencyRate),
                    "0"
            );

            exchangeRateVos.add(exchangeRateVo);
        }
        return exchangeRateVos;
    }

}
