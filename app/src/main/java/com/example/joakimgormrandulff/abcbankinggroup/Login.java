package com.example.joakimgormrandulff.abcbankinggroup;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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


public class Login extends AppCompatActivity {


    List<AccountModel> accountModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        new JSONTask().execute("http://abcmoneygroup.cloudapp.net/service1.svc/getallaccounts");

        Button login_button = (Button)findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText id = (EditText) findViewById(R.id.text_id);
                String idString = id.getText().toString();
                EditText pin = (EditText) findViewById(R.id.text_pin);
                String pinString = pin.getText().toString();

                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                if (idString.matches("") || (pinString.matches(""))) {
                    Log.i("input", idString);
                    Toast toast = Toast.makeText(context, "Please input your user ID and pin correctly.", duration);
                    toast.show();


                }
                AccountModel model = new AccountModel();


                int idInt = Integer.parseInt(idString);
                int pinInt = Integer.parseInt(pinString);
                for(int i = 0; i < accountModelList.size(); i++){
                    String asda = Integer.toString(accountModelList.get(i).getAccountID());
                    Log.i("account", asda);

                    if(accountModelList.get(i).getAccountID() == idInt) {
                        Log.i("account", "found match");
                        int index = accountModelList.get(i).getAccountID();

                        if(accountModelList.get(i).getPin() == pinInt){
                            Log.i("account", "Found matching id and pin");
                            Intent intent = new Intent(context, MenuActivity.class);
                            String data = Integer.toString(idInt);

                            Bundle extras = new Bundle();

                            String firstName = accountModelList.get(i).getFirstName();
                            String lastName = accountModelList.get(i).getLastName();
                            String address = accountModelList.get(i).getAddress();
                            String postCode = accountModelList.get(i).getPostCode();
                            int tele = accountModelList.get(i).getTelePhone();
                            int balance = accountModelList.get(i).getBalance();
                            int running = accountModelList.get(i).getRunningTotals();
                            int pins = accountModelList.get(i).getPin();

                            extras.putString("index", data);
                            extras.putString("firstName", firstName);
                            extras.putString("lastName", lastName);
                            extras.putString("address", address);
                            extras.putString("postcode", postCode);
                            extras.putInt("telephone", tele);
                            extras.putInt("balance", balance);
                            extras.putInt("running", running);
                            extras.putInt("pin", pins);


                            intent.putExtras(extras);
                            startActivity(intent);

                        }
                        else{
                            Toast pinToast = Toast.makeText(context, "Your information is wrong. Contact your bank for more details.", duration);
                            pinToast.show();
                        }
                    }
                    else{
                        Toast idToast = Toast.makeText(context, "Your information is wrong. Contact your bank for more details.", duration);
                    }
                }



            }
        });


    }


    public class JSONTask extends AsyncTask<String,String, List<AccountModel>>{

        @Override
        protected List<AccountModel> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";
                while((line = reader.readLine()) != null){
                    buffer.append(line);
                }

                String finalJSON = buffer.toString();

                JSONObject parentObject = new JSONObject(finalJSON);
                JSONArray parentArray = parentObject.getJSONArray("GetAllAccountsResult");




                accountModelList = new ArrayList<>();

                for(int i = 0 ; i<parentArray.length(); i++  ) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    AccountModel accountModel = new AccountModel();

                    accountModel.setAccountID(finalObject.getInt("AccountID"));
                    accountModel.setFirstName(finalObject.getString("firstName"));
                    accountModel.setLastName(finalObject.getString("lastName"));
                    accountModel.setAddress(finalObject.getString("Address"));
                    accountModel.setPostCode(finalObject.getString("postCode"));
                    accountModel.setTelePhone(finalObject.getInt("telePhone"));
                    accountModel.setBalance(finalObject.getInt("balance"));
                    accountModel.setPin(finalObject.getInt("pin"));
                    accountModel.setRunningTotals(finalObject.getInt("runningTotals"));

                    accountModelList.add(accountModel);
                }
                return accountModelList;




            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if(connection != null) {
                    connection.disconnect();
                }
                try {
                    if(reader != null){
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<AccountModel> result) {
            super.onPostExecute(result);

        }
    }
}
