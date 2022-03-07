package com.practicum.currencyconverter.presentation.converter;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Pair;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

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
        openKeyBoard();
    }

    private void initView() {
        binding.fromCurrencyIconImageView.setOnClickListener(v -> openCurrencies(CurrencyInput.FROM));
        binding.toCurrencyIconImageView.setOnClickListener(v -> openCurrencies(CurrencyInput.TO));
        binding.swapFab.setOnClickListener(v -> {
            converterViewModel.swapCurrencies();
            swapCurrencyInputs();
        });
        binding.refreshButton.setOnClickListener(v -> clearFields());
    }

    private void initViewModel() {
        converterViewModel = new ViewModelProvider(this).get(ConverterViewModel.class);
        converterViewModel.getPairConversationLiveData().observe(this, pairConversationDto -> {
        });

        converterViewModel.getFromCurrencyLiveData().observe(this, this::setFromCurrency);
        converterViewModel.getToCurrencyLiveData().observe(this, this::setToCurrency);
    }

    private void openKeyBoard() {
        binding.fromInputEditText.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    private void openCurrencies(final CurrencyInput currencyInput) {
        currencyScreenLauncher.launch(currencyInput);
    }

    private void handleCurrencyResult(final Pair<Currency, CurrencyInput> currencyResult) {
        final CurrencyInput currencyInput = currencyResult.second;
        final Currency currency = currencyResult.first;

        switch (currencyInput) {
            case FROM:
                converterViewModel.setFromCurrency(currency);
                break;
            case TO:
                converterViewModel.setToCurrency(currency);
                break;
        }
    }

    private void setFromCurrency(final Currency currency) {
        binding.fromCurrencyIconImageView.setImageDrawable(ContextCompat.getDrawable(this, currency.getIcon()));
        binding.fromCurrencyCodeTextView.setText(currency.getCode());
    }

    private void setToCurrency(final Currency currency) {
        binding.toCurrencyIconImageView.setImageDrawable(ContextCompat.getDrawable(this, currency.getIcon()));
        binding.toCurrencyCodeTextView.setText(currency.getCode());
    }

    private void swapCurrencyInputs() {
        final Editable from = binding.fromInputEditText.getText();
        final Editable to = binding.toInputEditText.getText();

        binding.fromInputEditText.setText(to);
        binding.toInputEditText.setText(from);
    }

    private void clearFields() {
        binding.fromInputEditText.setText("");
        binding.toInputEditText.setText("");
    }
}