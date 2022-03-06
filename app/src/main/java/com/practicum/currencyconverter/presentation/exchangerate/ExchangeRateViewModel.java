package com.practicum.currencyconverter.presentation.exchangerate;

import android.util.Log;

import com.practicum.currencyconverter.data.mappers.CurrencyExchangeMapper;
import com.practicum.currencyconverter.data.models.Currency;
import com.practicum.currencyconverter.data.models.CurrencyExchange;
import com.practicum.currencyconverter.data.network.ExchangerRateService;
import com.practicum.currencyconverter.di.DI;
import com.practicum.currencyconverter.presentation.base.BaseViewModel;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExchangeRateViewModel extends BaseViewModel {

    private final ExchangerRateService exchangerRateService = DI.exchangerRateService();
    private final CurrencyExchangeMapper currencyExchangeMapper = DI.getCurrencyExchangeMapper();

    private final MutableLiveData<List<ExchangeRateVo>> exchangeRateVoLiveData = new MutableLiveData<>();

    public LiveData<List<ExchangeRateVo>> getExchangeRateVoLiveData() {
        return exchangeRateVoLiveData;
    }

    public void loadCurrencies(final Currency currentCurrency) {
        exchangerRateService.getLatestCurrencyRate(currentCurrency.getCode()).enqueue(new Callback<CurrencyExchange>() {
            @Override
            public void onResponse(final Call<CurrencyExchange> call, final Response<CurrencyExchange> response) {
                final List<ExchangeRateVo> exchangeRateVos = currencyExchangeMapper.map(currentCurrency, response.body());

                exchangeRateVoLiveData.postValue(exchangeRateVos);
            }

            @Override
            public void onFailure(final Call<CurrencyExchange> call, final Throwable t) {

            }
        });
    }
}
