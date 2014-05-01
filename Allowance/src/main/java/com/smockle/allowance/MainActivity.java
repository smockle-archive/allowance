package com.smockle.allowance;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends Activity {

    SharedPreferences storage = null;
    public float allowance;
    public float balance;
    public ArrayList<Transaction> transactions;
    private String transactions_portable;

    void hackBalance() {
        balance = allowance;
        if (transactions != null) {
            for (Iterator<Transaction> i = transactions.iterator(); i.hasNext(); ) {
                Transaction t = i.next();
                balance -= t.amount;
            }
        }
    }

    public void exportPrefs() {
        balance = allowance;
        storage.edit().putFloat("allowance", allowance).commit();
        storage.edit().putFloat("balance", balance).commit();
        storage.edit().putBoolean("firstrun", false).commit();
        Gson gson = new Gson();
        transactions_portable = gson.toJson(transactions);
        storage.edit().putString("transactions", transactions_portable).commit();
    }

    public void importPrefs() {
        allowance = storage.getFloat("allowance", 90);
        balance = storage.getFloat("balance", allowance);
        transactions_portable = storage.getString("transactions", "");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Transaction>>(){}.getType();
        transactions = gson.fromJson(transactions_portable, type);
        hackBalance();
    }

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
        importPrefs();
    }

    @Override
    protected void onResume() {
        super.onResume();
        storage = getSharedPreferences("com.smockle.allowance", MODE_PRIVATE);
        importPrefs();
    }

    @Override
    protected void onPause() {
        super.onPause();
        storage = getSharedPreferences("com.smockle.allowance", MODE_PRIVATE);
        exportPrefs();
    }

    @Override
    protected void onStop() {
        super.onStop();
        storage = getSharedPreferences("com.smockle.allowance", MODE_PRIVATE);
        exportPrefs();
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
}
