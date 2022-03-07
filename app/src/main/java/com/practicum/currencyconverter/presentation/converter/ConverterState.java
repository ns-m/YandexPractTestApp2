package com.practicum.currencyconverter.presentation.converter;

import com.practicum.currencyconverter.data.models.Currency;

public class ConverterState {

    private final Currency fromCurrency;
    private final Currency toCurrency;
    private final double currencyCourse;
    private final double fromCurrencyInput;
    private final double toCurrencyInput;

    public static ConverterState DEFAULT = new ConverterState(Currency.USD, Currency.RUB, 0.0, 0.0, 0.0);

    public ConverterState(
            final Currency fromCurrency,
            final Currency toCurrency,
            final double currencyCourse,
            final double fromCurrencyInput,
            final double toCurrencyInput
    ) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.currencyCourse = currencyCourse;
        this.fromCurrencyInput = fromCurrencyInput;
        this.toCurrencyInput = toCurrencyInput;
    }

    public Currency getFromCurrency() {
        return fromCurrency;
    }

    public Currency getToCurrency() {
        return toCurrency;
    }

    public double getCurrencyCourse() {
        return currencyCourse;
    }

    public double getFromCurrencyInput() {
        return fromCurrencyInput;
    }

    public double getToCurrencyInput() {
        return toCurrencyInput;
    }

    static class Builder {

        private Currency fromCurrency;
        private Currency toCurrency;
        private double currencyCourse;
        private double fromCurrencyInput;
        private double toCurrencyInput;

        public Builder(final ConverterState state) {
            this.fromCurrency = state.fromCurrency;
            this.toCurrency = state.toCurrency;
            this.currencyCourse = state.currencyCourse;
            this.fromCurrencyInput = state.fromCurrencyInput;
            this.toCurrencyInput = state.toCurrencyInput;
        }

        public Builder setFromCurrency(final Currency fromCurrency) {
            this.fromCurrency = fromCurrency;
            return this;
        }

        public Builder setToCurrency(final Currency toCurrency) {
            this.toCurrency = toCurrency;
            return this;
        }

        public Builder setCurrencyCourse(final double currencyCourse) {
            this.currencyCourse = currencyCourse;
            return this;
        }

        public Builder setFromCurrencyInput(final double fromCurrencyInput) {
            this.fromCurrencyInput = fromCurrencyInput;
            return this;
        }

        public Builder setToCurrencyInput(final double toCurrencyInput) {
            this.toCurrencyInput = toCurrencyInput;
            return this;
        }

        public ConverterState copy() {
            return new ConverterState(fromCurrency, toCurrency, currencyCourse, fromCurrencyInput, toCurrencyInput);
        }
    }
}
