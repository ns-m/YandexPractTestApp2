package com.practicum.currencyconverter.presentation.converter;

import android.os.Bundle;
import android.widget.TextView;

import com.practicum.currencyconverter.R;
import com.practicum.currencyconverter.data.models.Currency;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class ConverterActivity extends AppCompatActivity {

    private ConverterViewModel converterViewModel;

    private TextView responseTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);

        initViews();

        converterViewModel = new ViewModelProvider(this).get(ConverterViewModel.class);
        converterViewModel.getPairConversationLiveData().observe(this, pairConversationDto -> {
            responseTextView.setText(String.valueOf(pairConversationDto.getConversionRate()));
        });

        converterViewModel.getCurrencyRate(Currency.USD, Currency.RUB);
    }

    private void initViews() {
        responseTextView = findViewById(R.id.responseTextView);
    }
}