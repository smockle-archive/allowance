package com.smockle.allowance;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment {

    private TextView mBalance;
    private Button mAddTransaction;
    private Button mViewTransactions;
    public static final String BALANCE = "balance";

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        MainActivity activity = (MainActivity)getActivity();
        mBalance = (TextView) rootView.findViewById(R.id.balance);
        mBalance.setText(String.format("$%.2f", activity.balance));

        mAddTransaction = (Button) rootView.findViewById(R.id.add_transaction);
        mAddTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransactionFragment transactionFragment = new TransactionFragment();
                Bundle args = new Bundle();
                transactionFragment.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack so the user can navigate back
                transaction.replace(R.id.container, transactionFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });

        mViewTransactions = (Button) rootView.findViewById(R.id.view_transactions);
        mViewTransactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListFragment listFragment = new ListFragment();
                Bundle args = new Bundle();
                listFragment.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack so the user can navigate back
                transaction.replace(R.id.container, listFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });

        if (savedInstanceState != null) {
            // Restore history
            mBalance.setText(savedInstanceState.getString(BALANCE, ""));
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        // Save history
        savedInstanceState.putString(BALANCE, mBalance.getText().toString());
    }
}