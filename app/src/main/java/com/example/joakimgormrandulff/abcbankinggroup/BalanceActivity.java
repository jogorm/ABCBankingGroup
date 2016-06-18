package com.example.joakimgormrandulff.abcbankinggroup;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BalanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);

        int balance = getIntent().getIntExtra("balance", 1);

        TextView bal = (TextView)findViewById(R.id.textView5);
        String result = "Your current balance is: " + balance;
        bal.setText(result);



    }
}
