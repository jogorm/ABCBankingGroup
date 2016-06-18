package com.example.joakimgormrandulff.abcbankinggroup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InfoActivity extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        String value = getIntent().getStringExtra("ID");
        String firstName = getIntent().getStringExtra("first");
        String name = "First Name: " + firstName;
        String lastName = getIntent().getStringExtra("last");
        String last = "Last Name: " + lastName;
        String address = getIntent().getStringExtra("add");
        String add = "Address: " + address;
        String postcode = getIntent().getStringExtra("post");
        String post = "Postcode: " + postcode;
        int tele = getIntent().getIntExtra("tele", 1);
        String telePhone = Integer.toString(tele);
        String teleP = "Telephone number: " + telePhone;
        int running_totals = getIntent().getIntExtra("running", 1);

        String[] values = new String[] {name, last, add, post, teleP};

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,
                list);

        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);


    }



}
