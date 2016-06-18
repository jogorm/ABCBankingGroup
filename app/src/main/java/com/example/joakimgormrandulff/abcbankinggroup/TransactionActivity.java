package com.example.joakimgormrandulff.abcbankinggroup;

import android.accounts.Account;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.joakimgormrandulff.abcbankinggroup.models.TransactionModel;

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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TransactionActivity extends AppCompatActivity {
    ListView list;
    List<TransactionModel> transactionModelList = new ArrayList<>();
    String[] values;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        String value = getIntent().getStringExtra("ID");
        int id = Integer.parseInt(value);

        mContext = this;
        new JSONTask().execute("http://abcmoneygroup.cloudapp.net/service1.svc/gettransactionsforaccount/" + id);

        list = (ListView) findViewById(R.id.listView2);

    }

    public class JSONTask extends AsyncTask<String,String, List<TransactionModel>> {

        @Override
        protected List<TransactionModel> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            Log.i("connection", "Inside doInBackground");
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                Log.i("connection", "connection.connect");

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";
                while((line = reader.readLine()) != null){
                    buffer.append(line);
                }
                Log.i("connection", "Finished reading");

                String finalJSON = buffer.toString();

                JSONObject parentObject = new JSONObject(finalJSON);
                JSONArray parentArray = parentObject.getJSONArray("GetTransactionsForAccountResult");

                Log.i("connection", "before setting");



                for(int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    TransactionModel transactionModel = new TransactionModel();

                    transactionModel.setTrans_id(finalObject.getInt("Trans_ID"));
                    transactionModel.setAcc_id(finalObject.getInt("acc_id"));
                    transactionModel.setAmount(finalObject.getInt("amount"));
                    transactionModel.setDateTime(finalObject.getString("dateTime"));

                    String partial = transactionModel.getDateTime().substring(6, 19);
                    Long date = Long.parseLong(partial, 10);

                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.UK);

                    Date dates = new Date(date);
                    String text = format.format(dates);
                    transactionModel.setCleanDateTime(text);
                    transactionModelList.add(transactionModel);
                }


                return transactionModelList;


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
        protected void onPostExecute(List<TransactionModel> result) {
            super.onPostExecute(result);
            Log.i("connection", "Executed");

            TransactionAdapter adapter = new TransactionAdapter(getApplicationContext(),R.layout.row, result );
            list.setAdapter(adapter);


//
        }

        public class TransactionAdapter extends ArrayAdapter{

            public List<TransactionModel> transactionList;
            private int resource;
            private LayoutInflater inflater;
            public TransactionAdapter(Context context, int resource, List<TransactionModel> objects) {
                super(context, resource, objects);
                transactionList = objects;
                this.resource = resource;
                inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                if(convertView == null){
                    convertView = inflater.inflate(R.layout.row, null);
                }

                TextView date;
                TextView amount;

                date = (TextView)convertView.findViewById(R.id.Date);
                amount = (TextView)convertView.findViewById(R.id.Amount);


                String amountString = Integer.toString(transactionList.get(position).getAmount());
                date.setText(transactionList.get(position).getCleanDateTime());
                amount.setText(amountString);

                return convertView;
            }
        }
        
    }

}
