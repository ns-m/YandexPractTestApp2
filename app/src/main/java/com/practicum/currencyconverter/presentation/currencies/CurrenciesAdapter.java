package com.practicum.currencyconverter.presentation.currencies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.practicum.currencyconverter.R;
import com.practicum.currencyconverter.data.models.Currency;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.RecyclerView;

public class CurrenciesAdapter extends RecyclerView.Adapter<CurrenciesAdapter.ViewHolder> {

    private final OnItemClickListener onItemClickListener;
    private final AsyncListDiffer<Currency> differ = new AsyncListDiffer<>(this, new CurrenciesDiffUtils());

    public CurrenciesAdapter(final OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_currency, parent, false);

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

    public void update(List<Currency> items) {
        differ.submitList(items);
    }

    interface OnItemClickListener {

        void onItemClick(final Currency currency);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView iconImageView;
        private final TextView nameTextView;
        private final TextView codeTextView;

        ViewHolder(final View itemView) {
            super(itemView);

            iconImageView = itemView.findViewById(R.id.iconImageView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            codeTextView = itemView.findViewById(R.id.codeTextView);
        }

        void bind(final Currency currency, final OnItemClickListener onItemClickListener) {
            itemView.setOnClickListener(v -> onItemClickListener.onItemClick(currency));

            iconImageView.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(), currency.getIcon()));
            nameTextView.setText(currency.getName());
            codeTextView.setText(currency.getCode());
        }
    }
}
