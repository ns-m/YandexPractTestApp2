package com.practicum.currencyconverter.presentation.converter;

import android.os.Bundle;
import android.util.Pair;

import com.practicum.currencyconverter.data.models.Currency;
import com.practicum.currencyconverter.databinding.ActivityConverterBinding;
import com.practicum.currencyconverter.presentation.currencies.CurrenciesActivity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

public class ConverterActivity extends AppCompatActivity {

    private ConverterViewModel converterViewModel;
    private final ActivityResultLauncher<CurrencyInput> currencyScreenLauncher =
            registerForActivityResult(CurrenciesActivity.getContract(this), this::handleCurrencyResult);

    private ActivityConverterBinding binding;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConverterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
        initViewModel();

        converterViewModel.getCurrencyRate(Currency.USD, Currency.RUB);
    }

    private void initView() {
        binding.fromCurrencyIconImageView.setOnClickListener(v -> openCurrencies(CurrencyInput.FROM));
        binding.toCurrencyIconImageView.setOnClickListener(v -> openCurrencies(CurrencyInput.TO));
    }

    private void initViewModel() {
        converterViewModel = new ViewModelProvider(this).get(ConverterViewModel.class);
        converterViewModel.getPairConversationLiveData().observe(this, pairConversationDto -> {
        });
    }

    private void openCurrencies(final CurrencyInput currencyInput) {
        currencyScreenLauncher.launch(currencyInput);
    }

    private void handleCurrencyResult(final Pair<Currency, CurrencyInput> currencyResult) {
        final CurrencyInput currencyInput = currencyResult.second;
        final Currency currency = currencyResult.first;

        switch (currencyInput) {
            case FROM:
                binding.fromCurrencyIconImageView.setImageDrawable(ContextCompat.getDrawable(this, currency.getIcon()));
                binding.fromCurrencyCodeTextView.setText(currency.getCode());
                break;
            case TO:
                binding.toCurrencyIconImageView.setImageDrawable(ContextCompat.getDrawable(this, currency.getIcon()));
                binding.toCurrencyCodeTextView.setText(currency.getCode());
                break;
        }
    }
}