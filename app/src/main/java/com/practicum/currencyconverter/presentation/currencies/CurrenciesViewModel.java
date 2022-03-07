package com.practicum.currencyconverter.presentation.currencies;

import com.practicum.currencyconverter.data.models.Currency;
import com.practicum.currencyconverter.presentation.base.BaseViewModel;

import java.util.Arrays;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class CurrenciesViewModel extends BaseViewModel {

    private final MutableLiveData<List<Currency>> currenciesLiveData = new MutableLiveData<>();

    public LiveData<List<Currency>> getCurrenciesLiveData() {
        return currenciesLiveData;
    }

    public void loadCurrencies() {
        currenciesLiveData.postValue(Arrays.asList(Currency.values()));
    }
}
