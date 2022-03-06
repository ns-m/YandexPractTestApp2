package com.practicum.currencyconverter.presentation.exchangerate;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.practicum.currencyconverter.R;
import com.practicum.currencyconverter.data.models.Currency;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.RecyclerView;

public class ExchangeRateAdapter extends RecyclerView.Adapter<ExchangeRateAdapter.ViewHolder> {

    private final OnItemClickListener onItemClickListener;
    private final AsyncListDiffer<ExchangeRateVo> differ = new AsyncListDiffer<>(this, new ExchangeRateDiffUtils());

    public ExchangeRateAdapter(final OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exchange_rate, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.bind(differ.getCurrentList().get(position), onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    public void update(List<ExchangeRateVo> items) {
        differ.submitList(items);
    }

    interface OnItemClickListener {

        void onItemClick(final Currency currency);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView fromCurrencyTextView;
        private final TextView fromCurrencyNameTextView;
        private final TextView fromRateTextView;
        private final TextView toCurrencyTextView;
        private final TextView toCurrencyNameTextView;
        private final TextView courseMovementTextView;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            fromCurrencyTextView = itemView.findViewById(R.id.fromCurrencyTextView);
            fromCurrencyNameTextView = itemView.findViewById(R.id.fromCurrencyNameTextView);
            fromRateTextView = itemView.findViewById(R.id.fromRateTextView);
            toCurrencyTextView = itemView.findViewById(R.id.toCurrencyTextView);
            toCurrencyNameTextView = itemView.findViewById(R.id.toCurrencyNameTextView);
            courseMovementTextView = itemView.findViewById(R.id.courseMovementTextView);
        }

        void bind(final ExchangeRateVo exchangeRateVo, final OnItemClickListener onItemClickListener) {
            itemView.setOnClickListener(v -> onItemClickListener.onItemClick(exchangeRateVo.getTo()));

            fromCurrencyTextView.setText(exchangeRateVo.getFrom().getCode());
            fromCurrencyNameTextView.setText(exchangeRateVo.getFrom().getName());
            fromRateTextView.setText(exchangeRateVo.getFromCurrencyRate());
            toCurrencyTextView.setText(exchangeRateVo.getTo().getCode());
            toCurrencyNameTextView.setText(exchangeRateVo.getTo().getName());
            courseMovementTextView.setText(exchangeRateVo.getCourseMovement());
        }
    }
}
