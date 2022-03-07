package com.practicum.currencyconverter.presentation.currencies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.MenuItem;

import com.practicum.currencyconverter.R;
import com.practicum.currencyconverter.data.models.Currency;
import com.practicum.currencyconverter.databinding.ActivityCurrenciesBinding;
import com.practicum.currencyconverter.presentation.converter.CurrencyInput;
import com.practicum.currencyconverter.ui.decorators.HorizontalLineItemDecorator;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class CurrenciesActivity extends AppCompatActivity implements CurrenciesAdapter.OnItemClickListener {

    private static final String ARG_CURRENCY = "arg_currency";
    private static final String ARG_CURRENCY_INPUT = "arg_currency_input";

    private final CurrenciesAdapter currenciesAdapter = new CurrenciesAdapter(this);

    private ActivityCurrenciesBinding binding;
    private CurrenciesViewModel currenciesViewModel;

    public static ActivityResultContract<CurrencyInput, Pair<Currency, CurrencyInput>> getContract(final Context packageContext) {
        return new ActivityResultContract<CurrencyInput, Pair<Currency, CurrencyInput>>() {
            @NonNull
            @Override
            public Intent createIntent(@NonNull final Context context, final CurrencyInput input) {
                final Intent intent = new Intent(packageContext, CurrenciesActivity.class);
                intent.putExtra(ARG_CURRENCY_INPUT, input);
                return intent;
            }

            @Override
            public Pair<Currency, CurrencyInput> parseResult(final int resultCode, @Nullable final Intent intent) {
                if (resultCode == RESULT_OK && intent != null) {
                    final Currency currency = (Currency) intent.getExtras().get(ARG_CURRENCY);
                    final CurrencyInput currencyInput = (CurrencyInput) intent.getExtras().get(ARG_CURRENCY_INPUT);

                    return Pair.create(currency, currencyInput);
                }

                return null;
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCurrenciesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
        initViewModel();

        currenciesViewModel.loadCurrencies();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull final MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(final Currency currency) {
        final CurrencyInput currencyInput = (CurrencyInput) getIntent().getExtras().get(ARG_CURRENCY_INPUT);
        final Intent intent = new Intent();

        intent.putExtra(ARG_CURRENCY, currency);
        intent.putExtra(ARG_CURRENCY_INPUT, currencyInput);

        setResult(RESULT_OK, intent);
        finish();
    }

    private void initViews() {
        initToolbar();
        initRecycler();
    }

    private void initToolbar() {
        binding.toolbar.setTitle(R.string.currencies_title);
        setSupportActionBar(binding.toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        }
    }

    private void initRecycler() {
        binding.currenciesRecyclerView.setAdapter(currenciesAdapter);

        final HorizontalLineItemDecorator itemDecoration = new HorizontalLineItemDecorator();
        itemDecoration.setColor(getResources().getColor(R.color.text_gray, null));
        itemDecoration.setLeftPadding((int) getResources().getDimension(R.dimen.currency_decorator_padding));

        binding.currenciesRecyclerView.addItemDecoration(itemDecoration);
    }

    private void initViewModel() {
        currenciesViewModel = new ViewModelProvider(this).get(CurrenciesViewModel.class);
        currenciesViewModel.getCurrenciesLiveData().observe(this, currenciesAdapter::update);
    }
}