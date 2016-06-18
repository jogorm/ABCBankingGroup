package com.example.joakimgormrandulff.abcbankinggroup;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.joakimgormrandulff.abcbankinggroup.models.AccountModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    AccountModel accountModel = new AccountModel();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //TODO Fix this oncreate, make it possible to return to this activity from balanceactivity.
        //TODO or remove back button, can just use button of device for now

        final String value = getIntent().getStringExtra("index");
        String firstName = getIntent().getStringExtra("firstName");
        final String cleanFirstName = firstName.replaceAll("\\s+", "");
        String lastName = getIntent().getStringExtra("lastName");
        final String cleanLastName = lastName.replaceAll("\\s+", "");
        String address = getIntent().getStringExtra("address");
        final String cleanAddress = address.replaceAll("\\s+","");
        final String postcode = getIntent().getStringExtra("postcode");
        final int tele = getIntent().getIntExtra("telephone", 1);
        final int balance = getIntent().getIntExtra("balance", 1);
        final int running_totals = getIntent().getIntExtra("running", 1);
        final int pin = getIntent().getIntExtra("pin", 1);

        int i = Integer.parseInt(value);
        //new JSONTask().execute("http://abcmoneygroup.cloudapp.net/service1.svc/getaccounts/" + i);
        updateHeadline(cleanFirstName, cleanLastName);


        Button balance_button = (Button)findViewById(R.id.balance_button);
        balance_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = getApplicationContext();
                Intent bal = new Intent(context, BalanceActivity.class);
                Bundle ext = new Bundle();
                ext.putInt("balance", balance);
                bal.putExtras(ext);
                startActivity(bal);
            }
        });

        Button info_button = (Button)findViewById(R.id.info_button);
        info_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                Intent info = new Intent(context, InfoActivity.class);
                Bundle ext = new Bundle();
                ext.putString("ID", value);
                ext.putString("first", cleanFirstName);
                ext.putString("last", cleanLastName);
                ext.putString("add", cleanAddress);
                ext.putString("post", postcode);
                ext.putInt("tele", tele);
                info.putExtras(ext);
                startActivity(info);

            }
        });

        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                Intent trans = new Intent(context, TransactionActivity.class);
                Bundle ext = new Bundle();
                ext.putString("ID", value);
                trans.putExtras(ext);
                startActivity(trans);
            }
        });

        Button updateButton = (Button)findViewById(R.id.change_button);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                Intent update = new Intent(context, UpdateActivity.class);
                Bundle ext = new Bundle();
                ext.putString("ID", value);
                ext.putInt("balance", balance);
                ext.putString("first", cleanFirstName);
                ext.putString("last", cleanLastName);
                ext.putString("add", cleanAddress);
                ext.putString("post", postcode);
                ext.putInt("tele", tele);
                ext.putInt("running", running_totals);
                ext.putInt("pin", pin);
                update.putExtras(ext);
                startActivity(update);
            }
        });


    }

    public void updateHeadline(String name, String name2){

        try {

            TextView headline = (TextView)findViewById(R.id.textView4);

            String head = "Welcome back, " + name + " " + name2 + "!";
            headline.setText(head);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



//    public class JSONTask extends AsyncTask<String,String, AccountModel> {
//
//        @Override
//        protected AccountModel doInBackground(String... params) {
//            HttpURLConnection connection = null;
//            BufferedReader reader = null;
//            Log.i("connection", "Inside doInBackground");
//            try {
//                URL url = new URL(params[0]);
//                connection = (HttpURLConnection) url.openConnection();
//                connection.connect();
//                Log.i("connection", "connection.connect");
//
//                InputStream stream = connection.getInputStream();
//                reader = new BufferedReader(new InputStreamReader(stream));
//
//                StringBuffer buffer = new StringBuffer();
//                String line = "";
//                while((line = reader.readLine()) != null){
//                    buffer.append(line);
//                }
//                Log.i("connection", "Finished reading");
//
//                String finalJSON = buffer.toString();
//
//                JSONObject parentObject = new JSONObject(finalJSON);
//                JSONArray parentArray = parentObject.getJSONArray("GetAccountResult");
//
//                //accountModelList = new ArrayList<>();
//
//                Log.i("connection", "before setting");
//                    JSONObject finalObject = parentArray.getJSONObject(0);
//
//
//                accountModel.setAccountID(finalObject.getInt("AccountID"));
//                accountModel.setFirstName(finalObject.getString("firstName"));
//                accountModel.setLastName(finalObject.getString("lastName"));
//                accountModel.setAddress(finalObject.getString("Address"));
//                accountModel.setPostCode(finalObject.getString("postCode"));
//                accountModel.setTelePhone(finalObject.getInt("telePhone"));
//                accountModel.setBalance(finalObject.getInt("balance"));
//                accountModel.setPin(finalObject.getInt("pin"));
//                accountModel.setRunningTotals(finalObject.getInt("runningTotals"));
//
//                    //accountModelList.add(accountModel);
//                Log.i("connection", "last Name: " + accountModel.getLastName());
//
//                return accountModel;
//
//
//
//
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            } finally {
//                if(connection != null) {
//                    connection.disconnect();
//                }
//                try {
//                    if(reader != null){
//                        reader.close();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(AccountModel result) {
//            super.onPostExecute(result);
//
//        }
//    }
}
