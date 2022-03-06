package com.practicum.currencyconverter.presentation.currencies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.practicum.currencyconverter.R;
import com.practicum.currencyconverter.data.models.Currency;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

public class CurrenciesActivity extends AppCompatActivity implements CurrenciesAdapter.OnItemClickListener {

    private static final String ARG_CURRENCY = "arg_currency";

    private final CurrenciesAdapter currenciesAdapter = new CurrenciesAdapter(this);

    private CurrenciesViewModel currenciesViewModel;

    private Toolbar toolbar;
    private EditText searchEditText;
    private RecyclerView currenciesRecyclerView;

    public static ActivityResultContract<Object, Currency> getContract(final Context packageContext) {
        return new ActivityResultContract<Object, Currency>() {
            @NonNull
            @Override
            public Intent createIntent(@NonNull final Context context, final Object input) {
                return new Intent(packageContext, CurrenciesActivity.class);
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
        setContentView(R.layout.activity_currencies);

        initViews();
        currenciesViewModel = new ViewModelProvider(this).get(CurrenciesViewModel.class);
        currenciesViewModel.getCurrenciesLiveData().observe(this, currenciesAdapter::update);
        currenciesViewModel.loadCurrencies();
    }

    @Override
    public void onItemClick(final Currency currency) {
        final Intent intent = new Intent();
        intent.putExtra(ARG_CURRENCY, currency);

        setResult(RESULT_OK, intent);
        finish();
    }

    private void initViews() {
        initToolbar();
        initSearchEditText();
        initRecycler();
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.currencies_title);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initSearchEditText() {
        searchEditText = findViewById(R.id.searchEditText);
    }

    private void initRecycler() {
        currenciesRecyclerView = findViewById(R.id.currenciesRecyclerView);
        currenciesRecyclerView.setAdapter(currenciesAdapter);
    }
}