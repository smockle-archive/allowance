package com.smockle.allowance;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class ListFragment extends Fragment {

    private ArrayList<String> mOutputWords;

    public ListFragment() {
    }

    // http://stackoverflow.com/a/5070922/1923134
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        final MainActivity activity = (MainActivity)getActivity();

        ListView mTransactionsListView = (ListView) rootView.findViewById(R.id.transactions_list_view);

        if (activity.transactions == null) {
            activity.transactions = new ArrayList<Transaction>();
        }

        TransactionAdapter arrayAdapter = new TransactionAdapter(activity, activity.transactions);
        mTransactionsListView.setAdapter(arrayAdapter);

        View empty = rootView.findViewById(R.id.empty);
        mTransactionsListView.setEmptyView(empty);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }
}