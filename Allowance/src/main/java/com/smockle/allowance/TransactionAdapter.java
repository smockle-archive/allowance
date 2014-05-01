package com.smockle.allowance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

// https://github.com/thecodepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
public class TransactionAdapter extends ArrayAdapter<Transaction> {
    public TransactionAdapter(Context context, ArrayList<Transaction> transactions) {
        super(context, R.layout.item_transaction, transactions);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Transaction transaction = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_transaction, parent, false);
        }

        // Lookup view for data population
        TextView mTransactionAmount = (TextView) convertView.findViewById(R.id.transaction_amount);
        TextView mTransactionDescription = (TextView) convertView.findViewById(R.id.transaction_description);
        TextView mTransactionDate = (TextView) convertView.findViewById(R.id.transaction_date);

        // Populate the data into the template view using the data object
        mTransactionAmount.setText(String.format("$%.2f", transaction.amount));
        mTransactionDescription.setText(transaction.description);
        mTransactionDate.setText(transaction.date.toString());

        // Return the completed view to render on screen
        return convertView;
    }
}