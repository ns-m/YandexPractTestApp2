package com.practicum.currencyconverter.presentation.converter;

import com.practicum.currencyconverter.data.models.Currency;
import com.practicum.currencyconverter.data.models.PairConversation;
import com.practicum.currencyconverter.data.network.ExchangerRateService;
import com.practicum.currencyconverter.di.Dependencies;
import com.practicum.currencyconverter.presentation.base.BaseViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConverterViewModel extends BaseViewModel {

    private final ExchangerRateService exchangerRateService = Dependencies.getExchangerRateService();

    private final MutableLiveData<PairConversation> pairConversationLiveData = new MutableLiveData<>();

    public LiveData<PairConversation> getPairConversationLiveData() {
        return pairConversationLiveData;
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
}
