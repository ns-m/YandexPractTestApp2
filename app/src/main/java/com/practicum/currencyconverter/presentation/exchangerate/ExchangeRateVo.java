package com.practicum.currencyconverter.presentation.exchangerate;

import com.practicum.currencyconverter.data.models.Currency;

import java.util.Objects;

public class ExchangeRateVo {

    private final Currency from;
    private final Currency to;

    private final String fromCurrencyRate;
    private final String courseMovement;

    public ExchangeRateVo(final Currency from, final Currency to, final String fromCurrencyRate, final String courseMovement) {
        this.from = from;
        this.to = to;
        this.fromCurrencyRate = fromCurrencyRate;
        this.courseMovement = courseMovement;
    }

    public Currency getFrom() {
        return from;
    }

    public Currency getTo() {
        return to;
    }

    public String getFromCurrencyRate() {
        return fromCurrencyRate;
    }

    public String getCourseMovement() {
        return courseMovement;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ExchangeRateVo that = (ExchangeRateVo) o;
        return from == that.from && to == that.to && fromCurrencyRate.equals(that.fromCurrencyRate) && courseMovement.equals(that.courseMovement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, fromCurrencyRate, courseMovement);
    }
}
