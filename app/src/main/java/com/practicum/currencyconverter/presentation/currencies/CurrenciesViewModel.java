package com.practicum.currencyconverter.presentation.currencies;

import android.util.Log;

import com.practicum.currencyconverter.data.cache.CurrencyCourseDataStore;
import com.practicum.currencyconverter.data.models.Currency;
import com.practicum.currencyconverter.data.models.CurrencyRate;
import com.practicum.currencyconverter.data.network.ResultCallback;
import com.practicum.currencyconverter.di.DI;
import com.practicum.currencyconverter.presentation.base.BaseViewModel;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class CurrenciesViewModel extends BaseViewModel {

    private final CurrencyCourseDataStore currencyCourseDataStore = DI.currencyCourseDataStore();

    private final MutableLiveData<List<Currency>> currenciesLiveData = new MutableLiveData<>();

    public LiveData<List<Currency>> getCurrenciesLiveData() {
        return currenciesLiveData;
    }

    public void loadCurrencies() {
        currencyCourseDataStore.getCurrencyResult(false, new ResultCallback<CurrencyRate>() {
            @Override
            public void onSuccess(final CurrencyRate data) {
                currenciesLiveData.postValue(CurrenciesFactory.getAvailableCurrencies(data));
            }

            @Override
            public void onFailure(final Throwable error) {
                Log.d("CurrenciesViewModel", error.getMessage());
            }
        });

    }
}
