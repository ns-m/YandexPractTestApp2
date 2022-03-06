package com.practicum.currencyconverter.presentation.exchangerate;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class ExchangeRateDiffUtils extends DiffUtil.ItemCallback<ExchangeRateVo> {

    @Override
    public boolean areItemsTheSame(@NonNull final ExchangeRateVo oldItem, @NonNull final ExchangeRateVo newItem) {
        return oldItem.getFrom().equals(newItem.getTo());
    }

    @Override
    public boolean areContentsTheSame(@NonNull final ExchangeRateVo oldItem, @NonNull final ExchangeRateVo newItem) {
        return oldItem.equals(newItem);
    }
}
