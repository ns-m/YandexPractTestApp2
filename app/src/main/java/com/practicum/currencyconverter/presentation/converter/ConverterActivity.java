package com.practicum.currencyconverter.presentation.converter;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;

import com.practicum.currencyconverter.R;
import com.practicum.currencyconverter.data.models.Currency;
import com.practicum.currencyconverter.databinding.ActivityConverterBinding;
import com.practicum.currencyconverter.presentation.currencies.CurrenciesActivity;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Objects;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

public class ConverterActivity extends AppCompatActivity {

    private ConverterViewModel viewModel;
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
        viewModel.getCurrencyRate();
    }

    private void initView() {
        setFromInput();

        binding.fromCurrencyIconImageView.setOnClickListener(v -> openCurrencies(CurrencyInput.FROM));
        binding.toCurrencyIconImageView.setOnClickListener(v -> openCurrencies(CurrencyInput.TO));
        binding.refreshButton.setOnClickListener(v -> clearFields());
        binding.convertButton.setOnClickListener(v -> viewModel.convertUserInput(binding.fromInputEditText.getText().toString()));
        binding.swapFab.setOnClickListener(v -> viewModel.swapCurrencies());
    }

    private void setFromInput() {
        binding.fromInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {

            }

            @Override
            public void afterTextChanged(final Editable s) {
                formatNumber(s, this);
            }
        });
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(ConverterViewModel.class);
        viewModel.getConverterStateLiveData().observe(this, this::render);
    }

    private void render(final ConverterState state) {
        setFromCurrency(state.getFromCurrency());
        setToCurrency(state.getToCurrency());
        setCurrencyCourse(state);
        setFromCurrencyInput(state);
        setToCurrencyInput(state);
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
                viewModel.setFromCurrency(currency);
                break;
            case TO:
                viewModel.setToCurrency(currency);
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

    private void setCurrencyCourse(final ConverterState state) {
        if (state.getCurrencyCourse() != 0.0) {
            final String currencyCourseText = getString(
                    R.string.converter_currency_cource,
                    state.getFromCurrency().getCode(),
                    state.getCurrencyCourse(),
                    state.getToCurrency().getCode()
            );

            binding.currencyCourseTextView.setVisibility(View.VISIBLE);
            binding.currencyCourseTextView.setText(currencyCourseText);
        } else {
            binding.currencyCourseTextView.setVisibility(View.GONE);
            binding.currencyCourseTextView.setText("");
        }
    }

    private void setFromCurrencyInput(final ConverterState state) {
        final double fromCurrencyInput = state.getFromCurrencyInput();

        if (fromCurrencyInput == 0.0) {
            binding.fromInputEditText.setText("");
        } else {
            final DecimalFormat decimalFormat = new DecimalFormat();
            decimalFormat.setMaximumFractionDigits(2);
            binding.fromInputEditText.setText(decimalFormat.format(fromCurrencyInput));
        }
    }

    private void setToCurrencyInput(final ConverterState state) {
        final double toCurrencyInput = state.getToCurrencyInput();

        if (toCurrencyInput == 0.0) {
            binding.toResultTextView.setText("");
        } else {
            final DecimalFormat decimalFormat = new DecimalFormat();
            decimalFormat.setMaximumFractionDigits(2);
            binding.toResultTextView.setText(decimalFormat.format(toCurrencyInput));
        }
    }

    private void clearFields() {
        binding.fromInputEditText.setText("");
        binding.toResultTextView.setText("");
    }

    private void formatNumber(final Editable s, final TextWatcher textWatcher) {
        try {
            final DecimalFormat decimalFormat = new DecimalFormat();
            final double result = Objects.requireNonNull(decimalFormat.parse(s.toString())).doubleValue();
            decimalFormat.setMaximumFractionDigits(2);

            binding.fromInputEditText.removeTextChangedListener(textWatcher);
            binding.fromInputEditText.setText(decimalFormat.format(result));
            binding.fromInputEditText.addTextChangedListener(textWatcher);
        } catch (ParseException e) {
            // TODO show error
        }
    }
}