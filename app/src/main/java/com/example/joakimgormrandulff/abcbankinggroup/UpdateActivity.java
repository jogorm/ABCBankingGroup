package com.example.joakimgormrandulff.abcbankinggroup;

import android.app.LauncherActivity;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UpdateActivity extends AppCompatActivity {


    private String[] arrText = new String[]{"FirstName", "LastName", "Address", "Postcode", "Telephone"};
    private String[] arrTemp;
    MyListAdapter myListAdapter = null;
    String value;
    int balance;
    int pin;
    int running_totals;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);


        arrTemp = new String[arrText.length];
        myListAdapter = new MyListAdapter();
        ListView listView = (ListView) findViewById(R.id.listViewMain);
        listView.setAdapter(myListAdapter);



        value = getIntent().getStringExtra("ID");
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
        running_totals = getIntent().getIntExtra("running", 1);
        pin = getIntent().getIntExtra("pin", 1);
        balance = getIntent().getIntExtra("balance", 1);


        Button save = (Button)findViewById(R.id.PostButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String allValues = "";
                ArrayList<String> valueList = new ArrayList<String>();
                for(int i = 0; i<myListAdapter.getCount(); i++ ){
                    allValues += (myListAdapter.getItem(i) + ",");

                    valueList.add(allValues);

                }
                //Toast.makeText(UpdateActivity.this, allValues, Toast.LENGTH_LONG).show();

//
                if(allValues.contains("null")){
                    Log.i("connection", "Valuelist has empty value");
                    Toast.makeText(UpdateActivity.this, "You haven't filled out all the fields.", Toast.LENGTH_LONG).show();
                }
                else{
                    Log.i("connection", "!valueList.contains(null) = else");

                    new JSONTask().execute();
                    Toast.makeText(UpdateActivity.this, "Please restart the application to update your data", Toast.LENGTH_LONG).show();
                }



            }
        });
    }






    public class JSONTask extends AsyncTask<Void, Void, Void>{


        @Override
        protected Void doInBackground(Void... params) {

            try {

                JSONObject json = new JSONObject();

                json.put("accountId", value);
                json.put("balance", balance);
                json.put("runningTotals", running_totals);
                json.put("firstName", myListAdapter.getItem(0));
                json.put("lastName", myListAdapter.getItem(1));
                json.put("address", myListAdapter.getItem(2));
                json.put("postcode", myListAdapter.getItem(3));
                json.put("telephone", myListAdapter.getItem(4));
                json.put("pin", pin);

                String jsonString = json.toString();

                Log.i("connection", jsonString);


                StringBuilder sb = new StringBuilder();
                String http = "http://abcmoneygroup.cloudapp.net/Service1.svc/updateaccount";

                HttpURLConnection urlConnection = null;
//                URL url = new URL(http);
                //urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection = (HttpURLConnection) ((new URL(http).openConnection()));
//                urlConnection.setDoOutput(true);
//                urlConnection.setRequestProperty("Content-Type", "application/json");
//                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setRequestMethod("POST");
                urlConnection.connect();

                OutputStream outputStream = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                writer.write(jsonString);
//                writer.write(lazyJsonString);
                writer.close();
                outputStream.close();


                int HttpResult = urlConnection.getResponseCode();
                String response = Integer.toString(urlConnection.getResponseCode());
                Log.i("connectionResponse", response);
                if(HttpResult ==HttpURLConnection.HTTP_OK){
                    BufferedReader br = new BufferedReader(new InputStreamReader(
                            urlConnection.getInputStream(),"utf-8"));
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();



                }else{

                    InputStream errorstream = urlConnection.getErrorStream();
                    String responses="";
                    String line;
                    BufferedReader br=new BufferedReader(new InputStreamReader(errorstream));
                    while ((line=br.readLine()) != null) {
                        responses+=line;
                    }

                    Log.d("body of Bad Request " , "Responses: "+responses);
                    //Toast.makeText(UpdateActivity.this, connection.getResponseMessage(), Toast.LENGTH_LONG).show();
                }




            } catch (JSONException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }


    private class MyListAdapter extends BaseAdapter implements View.OnTouchListener{

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            if(arrText != null && arrText.length != 0){
                return arrText.length;
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
//            return arrText[position];
            return arrTemp[position];

        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            Log.i("connection", "Inside getItemId");
            return position;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            //ViewHolder holder = null;
            final ViewHolder holder;
            if (convertView == null) {

                holder = new ViewHolder();
                LayoutInflater inflater = UpdateActivity.this.getLayoutInflater();
                convertView = inflater.inflate(R.layout.listitems, null);
                holder.textView1 = (TextView) convertView.findViewById(R.id.textView123);
                holder.editText1 = (EditText) convertView.findViewById(R.id.editText123);
                holder.editText1.setOnTouchListener(this);

                convertView.setOnTouchListener(this);
                convertView.setTag(holder);



            } else {

                holder = (ViewHolder) convertView.getTag();
            }

            holder.ref = position;

            holder.textView1.setText(arrText[position]);
            holder.editText1.setText(arrTemp[position]);
            holder.editText1.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub


                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                              int arg3) {
                    // TODO Auto-generated method stub


                }

                @Override
                public void afterTextChanged(Editable arg0) {
                    // TODO Auto-generated method stub
                    arrTemp[holder.ref] = arg0.toString();

                }
            });

            return convertView;
        }

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if (view instanceof EditText) {
                EditText editText = (EditText) view;
                editText.setFocusable(true);
                editText.setFocusableInTouchMode(true);
            } else {
                ViewHolder holder = (ViewHolder) view.getTag();
                holder.editText1.setFocusable(false);
                holder.editText1.setFocusableInTouchMode(false);
            }
            return false;
        }

        private class ViewHolder {
            TextView textView1;
            EditText editText1;
            int ref;

        }


        public void bindView(View view, Context context, Cursor cur) {
            ViewHolder holder = (ViewHolder) view.getTag();
            if (cur!=null) holder.textView1.setText(cur.getString(cur.getColumnIndex("name")));
        }


    }
}

