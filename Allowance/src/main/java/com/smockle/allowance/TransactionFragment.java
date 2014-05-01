package com.smockle.allowance;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

/**
 * A placeholder fragment containing a simple view.
 */
public class TransactionFragment extends Fragment {

    private EditText mTransaction;
    private Button mAddTransaction;

    public TransactionFragment() {
    }

    float getFloatFromEditText(EditText transaction) {
        Float f;
        try {
            f = Float.parseFloat(transaction.getText().toString());
        } catch (NullPointerException e) {
            return 0;
        } catch (NumberFormatException e) {
            return 0;
        }
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_transaction, container, false);

        final MainActivity activity = (MainActivity)getActivity();
        mTransaction = (EditText) rootView.findViewById(R.id.transaction);

        mAddTransaction = (Button) rootView.findViewById(R.id.add_transaction);
        mAddTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            // Hide keyboard.
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow((null == activity.getCurrentFocus()) ? null : activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

            // Update transactions.
            Transaction t = new Transaction(getFloatFromEditText(mTransaction), "", new Date());

            if (t.amount == 0) {
                Toast.makeText(activity, R.string.empty_transaction, Toast.LENGTH_SHORT).show();
            } else {

            if (activity.transactions == null) {
                activity.transactions = new ArrayList<Transaction>();
            }
            activity.transactions.add(t);

            // Update balance.
            activity.balance -= t.amount;

            // Create new fragment_main.
            MainFragment mainFragment = new MainFragment();
            Bundle args = new Bundle();
            mainFragment.setArguments(args);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.container, mainFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
            }}
        });

        if (savedInstanceState != null) {
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }
}