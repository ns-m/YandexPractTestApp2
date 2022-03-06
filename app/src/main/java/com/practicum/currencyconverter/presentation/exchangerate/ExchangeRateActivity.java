package com.practicum.currencyconverter.presentation.exchangerate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.practicum.currencyconverter.R;
import com.practicum.currencyconverter.data.models.Currency;
import com.practicum.currencyconverter.presentation.currencies.CurrenciesActivity;
import com.practicum.currencyconverter.presentation.currencies.CurrenciesViewModel;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

public class ExchangeRateActivity extends AppCompatActivity implements ExchangeRateAdapter.OnItemClickListener {

    private static final String ARG_CURRENCY = "arg_currency";

    private ExchangeRateAdapter exchangeRateAdapter = new ExchangeRateAdapter(this);

    private ExchangeRateViewModel exchangeRateViewModel;

    private RecyclerView exchangeRateRecycler;

    public static ActivityResultContract<Currency, Currency> getContract(final Context packageContext) {
        return new ActivityResultContract<Currency, Currency>() {
            @NonNull
            @Override
            public Intent createIntent(@NonNull final Context context, final Currency input) {
                return new Intent(packageContext, ExchangeRateActivity.class);
            }

            @Override
            public Currency parseResult(final int resultCode, @Nullable final Intent intent) {
                if (resultCode == RESULT_OK && intent != null) {
                    return (Currency) intent.getExtras().get(ARG_CURRENCY);
                }

                return null;
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_rate);

        initRecycler();
        initViewModel();

        exchangeRateViewModel.loadCurrencies(Currency.RUB);
    }

    @Override
    public void onItemClick(final Currency currency) {

    }

    private void initRecycler() {
        exchangeRateRecycler = findViewById(R.id.exchangeRateRecycler);
        exchangeRateRecycler.setAdapter(exchangeRateAdapter);
    }

    private void initViewModel() {
        exchangeRateViewModel = new ViewModelProvider(this).get(ExchangeRateViewModel.class);
        exchangeRateViewModel.getExchangeRateVoLiveData().observe(this, exchangeRateAdapter::update);
    }
}