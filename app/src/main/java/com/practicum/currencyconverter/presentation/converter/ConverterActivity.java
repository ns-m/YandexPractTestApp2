package com.practicum.currencyconverter.presentation.converter;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.practicum.currencyconverter.R;
import com.practicum.currencyconverter.data.models.Currency;
import com.practicum.currencyconverter.presentation.currencies.CurrenciesActivity;
import com.practicum.currencyconverter.presentation.exchangerate.ExchangeRateActivity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class ConverterActivity extends AppCompatActivity {

    private ConverterViewModel converterViewModel;
    private final ActivityResultLauncher<Object> currencyScreenLauncher =
            registerForActivityResult(CurrenciesActivity.getContract(this), this::handleCurrencyResult);
    private final ActivityResultLauncher<Currency> exchangeRateScreenLauncher =
            registerForActivityResult(ExchangeRateActivity.getContract(this), this::handleExchangeRateResult);

    private TextView fromTextView;
    private TextView toTextView;
    private TextView fromCurrencyTextView;
    private EditText amountEditText;
    private Button convertButton;
    private TextView resultCurrencyTextView;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);

        initViews();

        converterViewModel = new ViewModelProvider(this).get(ConverterViewModel.class);
        converterViewModel.getPairConversationLiveData().observe(this, pairConversationDto -> {
        });

        converterViewModel.getCurrencyRate(Currency.USD, Currency.RUB);
    }

    private void initViews() {
        fromTextView = findViewById(R.id.fromTextView);
        fromTextView.setOnClickListener(v -> openCurrencies());

        toTextView = findViewById(R.id.toTextView);
        toTextView.setOnClickListener(v -> openExchangeRate());

        fromCurrencyTextView = findViewById(R.id.fromCurrencyTextView);
        amountEditText = findViewById(R.id.amountEditText);
        convertButton = findViewById(R.id.convertButton);
        resultCurrencyTextView = findViewById(R.id.resultCurrencyTextView);
        resultTextView = findViewById(R.id.resultTextView);
    }

    private void openCurrencies() {
        currencyScreenLauncher.launch(null);
    }

    private void openExchangeRate() {
        exchangeRateScreenLauncher.launch(Currency.RUB);
    }

    private void handleCurrencyResult(final Currency currency) {
        fromTextView.setText(currency.getName());
    }

    private void handleExchangeRateResult(final Currency currency) {
        toTextView.setText(currency.getName());
    }
}