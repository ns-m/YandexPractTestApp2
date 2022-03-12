package com.practicum.currencyconverter.presentation.converter;

import com.practicum.currencyconverter.data.CurrenciesConverter;
import com.practicum.currencyconverter.data.cache.CurrencyCourseDataStore;
import com.practicum.currencyconverter.data.models.Currency;
import com.practicum.currencyconverter.data.models.CurrencyRate;
import com.practicum.currencyconverter.data.network.ResultCallback;
import com.practicum.currencyconverter.di.DI;
import com.practicum.currencyconverter.presentation.base.BaseViewModel;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ConverterViewModel extends BaseViewModel {

    private final CurrencyCourseDataStore currencyCourseDataStore = DI.currencyCourseDataStore();
    private final CurrenciesConverter currenciesConverter = DI.currenciesConverter();

    private final MutableLiveData<ConverterState> converterStateLiveData = new MutableLiveData<>(ConverterState.DEFAULT);

    public LiveData<ConverterState> getConverterStateLiveData() {
        return converterStateLiveData;
    }

    public void getCurrencyRate(final boolean forceUpdate) {
        isLoadingLiveData.setValue(true);

        currencyCourseDataStore.getCurrencyResult(forceUpdate, new ResultCallback<CurrencyRate>() {
            @Override
            public void onSuccess(final CurrencyRate data) {
                double currencyCourse = currenciesConverter.convert(currentState().getFromCurrency(), currentState().getToCurrency(), data);
                final ConverterState resultState = new ConverterState.Builder(currentState())
                        .setCurrencyCourse(currencyCourse)
                        .copy();

                converterStateLiveData.setValue(resultState);
                isLoadingLiveData.setValue(false);
                isErrorShownLiveData.setValue(false);
            }

            @Override
            public void onFailure(final Throwable error) {
                isLoadingLiveData.setValue(false);
                isErrorShownLiveData.setValue(true);
            }
        });
    }

    public void setFromCurrency(final Currency currency) {
        final ConverterState resultState = new ConverterState.Builder(currentState())
                .setFromCurrency(currency)
                .copy();

        converterStateLiveData.setValue(resultState);
        getCurrencyRate(false);
    }

    public void setToCurrency(final Currency currency) {
        final ConverterState resultState = new ConverterState.Builder(currentState())
                .setToCurrency(currency)
                .copy();

        converterStateLiveData.setValue(resultState);
        getCurrencyRate(false);
    }

    public void swapCurrencies(final String fromCurrencyInput) {
        final ConverterState resultState;

        if (currentState().getToCurrencyInput() == 0.0 && currentState().getFromCurrencyInput() == 0.0) {
            resultState = new ConverterState.Builder(currentState())
                    .setFromCurrency(currentState().getToCurrency())
                    .setToCurrency(currentState().getFromCurrency())
                    .copy();
        } else if (currentState().getToCurrencyInput() == 0.0) {
            try {
                final DecimalFormat decimalFormat = new DecimalFormat();
                final double result = Objects.requireNonNull(decimalFormat.parse(fromCurrencyInput)).doubleValue();

                resultState = new ConverterState.Builder(currentState())
                        .setFromCurrency(currentState().getToCurrency())
                        .setToCurrency(currentState().getFromCurrency())
                        .setToCurrencyInput(result)
                        .setFromCurrencyInput(0.0)
                        .copy();
            } catch (ParseException e) {
                isErrorShownLiveData.setValue(true);
                return;
            }
        } else {
            resultState = new ConverterState.Builder(currentState())
                    .setFromCurrency(currentState().getToCurrency())
                    .setToCurrency(currentState().getFromCurrency())
                    .setToCurrencyInput(currentState().getFromCurrencyInput())
                    .setFromCurrencyInput(currentState().getToCurrencyInput())
                    .copy();
        }
        converterStateLiveData.setValue(resultState);
        getCurrencyRate(false);
    }

    @NonNull
    private ConverterState currentState() {
        return Objects.requireNonNull(converterStateLiveData.getValue());
    }

    public void convertUserInput(final String fromCurrencyInput) {
        try {
            final DecimalFormat decimalFormat = new DecimalFormat();
            final double result = Objects.requireNonNull(decimalFormat.parse(fromCurrencyInput)).doubleValue();
            final double toCurrencyInput = result * currentState().getCurrencyCourse();

            final ConverterState resultState = new ConverterState.Builder(currentState())
                    .setFromCurrencyInput(result)
                    .setToCurrencyInput(toCurrencyInput)
                    .copy();

            converterStateLiveData.setValue(resultState);
        } catch (ParseException e) {
            isErrorShownLiveData.setValue(true);
        }
    }
}
