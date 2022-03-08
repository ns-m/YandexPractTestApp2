package com.practicum.currencyconverter.data.models;

import com.practicum.currencyconverter.R;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

public enum Currency {
    AUD("AUD", R.string.currency_australia, R.drawable.ic_australia),
    CAD("CAD", R.string.currency_canada, R.drawable.ic_canada),
    CHF("CHF", R.string.currency_switzerland, R.drawable.ic_switzerland),
    CNY("CNY", R.string.currency_china, R.drawable.ic_china),
    CZK("CZK", R.string.currency_czech_republic, R.drawable.ic_czech_republic),
    DKK("DKK", R.string.currency_denmark, R.drawable.ic_denmark),
    EUR("EUR", R.string.currency_european_union, R.drawable.ic_euro),
    GBP("GBP", R.string.currency_united_kingdom, R.drawable.ic_great_britain),
    HKD("HKD", R.string.currency_hong_kong, R.drawable.ic_hong_kong),
    JPY("JPY", R.string.currency_japan, R.drawable.ic_japan),
    MXN("MXN", R.string.currency_mexico, R.drawable.ix_mexico),// none
    NOK("NOK", R.string.currency_norway, R.drawable.ic_norway),
    NZD("NZD", R.string.currency_new_zealand, R.drawable.ic_new_zeland), // none
    PLN("PLN", R.string.currency_poland, R.drawable.ic_poland),
    RUB("RUB", R.string.currency_russia, R.drawable.ic_russia),
    SEK("SEK", R.string.currency_sweden, R.drawable.ic_sweden),
    SGD("SGD", R.string.currency_singapore, R.drawable.ic_singapore),
    TRY("TRY", R.string.currency_turkey, R.drawable.ic_turkey),
    USD("USD", R.string.currency_united_states, R.drawable.ic_usa),
    ZAR("ZAR", R.string.currency_south_africa, R.drawable.ic_south_africa);

    private final String code;

    @StringRes
    private final int name;

    @DrawableRes
    private final int icon;

    Currency(final String code, @StringRes final int name, @DrawableRes final int icon) {
        this.code = code;
        this.name = name;
        this.icon = icon;
    }

    public String getCode() {
        return code;
    }

    public int getName() {
        return name;
    }

    public int getIcon() {
        return icon;
    }
}
