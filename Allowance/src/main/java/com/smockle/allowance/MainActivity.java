package com.smockle.allowance;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends Activity {

    SharedPreferences storage = null;
    private float allowance;
    public float balance;
    private ArrayList<Transaction> transactions;
    private String transactions_portable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new MainFragment())
                    .commit();
        }
        storage = getSharedPreferences("com.smockle.allowance", MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (storage.getBoolean("firstrun", true)) {
            storage.edit().putFloat("allowance", allowance).commit();
            storage.edit().putBoolean("firstrun", false).commit();
            Gson gson = new Gson();
            transactions_portable = gson.toJson(transactions);
            storage.edit().putString("transactions", transactions_portable).commit();
        } else {
            storage.getFloat("allowance", allowance);
            storage.getFloat("balance", balance);
            storage.getString("transactions", transactions_portable);
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Transaction>>(){}.getType();
            transactions = gson.fromJson(transactions_portable, type);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class MainFragment extends Fragment {

        private TextView mBalance;
        public static final String BALANCE = "balance";

        public MainFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            MainActivity activity = (MainActivity)getActivity();
            mBalance = (TextView) rootView.findViewById(R.id.balance);
            mBalance.setText(Float.toString(activity.balance));

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
}
