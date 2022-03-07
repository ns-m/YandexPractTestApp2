package com.practicum.currencyconverter.presentation.converter;

import com.practicum.currencyconverter.data.models.Currency;
import com.practicum.currencyconverter.data.models.PairConversation;
import com.practicum.currencyconverter.data.network.ExchangerRateService;
import com.practicum.currencyconverter.di.DI;
import com.practicum.currencyconverter.presentation.base.BaseViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConverterViewModel extends BaseViewModel {

    private final ExchangerRateService exchangerRateService = DI.exchangerRateService();

    private final MutableLiveData<PairConversation> pairConversationLiveData = new MutableLiveData<>();
    private final MutableLiveData<Currency> fromCurrencyLiveData = new MutableLiveData<>(Currency.USD);
    private final MutableLiveData<Currency> toCurrencyLiveData = new MutableLiveData<>(Currency.RUB);

    public LiveData<PairConversation> getPairConversationLiveData() {
        return pairConversationLiveData;
    }

    public LiveData<Currency> getFromCurrencyLiveData() {
        return fromCurrencyLiveData;
    }

    public LiveData<Currency> getToCurrencyLiveData() {
        return toCurrencyLiveData;
    }

    public void getCurrencyRate(final Currency from, final Currency to) {
        exchangerRateService.getPairConversation(from.getCode(), to.getCode()).enqueue(new Callback<PairConversation>() {
            @Override
            public void onResponse(final Call<PairConversation> call, final Response<PairConversation> response) {
                pairConversationLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(final Call<PairConversation> call, final Throwable t) {
            }
        });
    }

    public void setFromCurrency(final Currency currency) {
        fromCurrencyLiveData.postValue(currency);
    }

    public void setToCurrency(final Currency currency) {
        toCurrencyLiveData.postValue(currency);
    }

    public void swapCurrencies() {
        final Currency from = fromCurrencyLiveData.getValue();
        final Currency to = toCurrencyLiveData.getValue();

        fromCurrencyLiveData.postValue(to);
        toCurrencyLiveData.postValue(from);
    }
}
