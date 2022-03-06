package com.practicum.currencyconverter.data.network;

import com.practicum.currencyconverter.data.models.CurrencyExchange;
import com.practicum.currencyconverter.data.models.PairConversation;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ExchangerRateService {

    @GET("96311518397ffbe6a59716a8/pair/{from}/{to}")
    Call<PairConversation> getPairConversation(@Path("from") String from, @Path("to") String to);

    @GET("96311518397ffbe6a59716a8/latest/{currency}")
    Call<CurrencyExchange> getLatestCurrencyRate(@Path("currency") String currency);
}
