package com.example.nelson.gm530.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.nelson.gm530.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class WeatherActivity extends AppCompatActivity {
    ArrayList<HashMap<String,Object>> list;
    ListView lv ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        lv=(ListView)findViewById(R.id.listview);
        list=new ArrayList<HashMap<String,Object>>();
        NetworkTask newTask=new NetworkTask();
        newTask.execute("http://opendata.epa.gov.tw/ws/Data/ATM00698/?$skip=0&$top=1000&format=json");

    }


    public class NetworkTask extends AsyncTask<String,Void,String> {

        ProgressDialog dialog;
        String resultString;
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            dialog = new ProgressDialog(WeatherActivity.this);
            dialog.setCancelable(false);
            dialog = ProgressDialog.show(WeatherActivity.this, "連線中", "請稍候");
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            HttpClient client=new DefaultHttpClient();
            HttpGet get=new HttpGet(params[0]);
            try {
                HttpResponse response=client.execute(get);
                HttpEntity entity=response.getEntity();
                resultString= EntityUtils.toString(entity);

            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            dialog.dismiss();
//            Toast.makeText(MainActivity.this, resultString, Toast.LENGTH_LONG).show();
            Log.d("result",resultString);
            try {
                JSONArray array=new JSONArray("resultString");
                JSONObject obj =array.getJSONObject(0);
                for (int i=0 ;i<array.length();i++){
                    JSONObject object =array.getJSONObject(i);
                    HashMap<String,Object> item =new HashMap<>();
                    item.put("SiteName",object.getString("SiteName"));
                    item.put("Temperature",object.getString("Temperature"));
                    item.put("Moisture",object.getString("Moisture"));
                    item.put("Weather",object.getString("Weather"));
                    item.put("WindDirection",object.getString("WindDirection"));
                    //item.put("Data",object.getString("DataCreationDate"));
                    list.add(item);
                }

                SimpleAdapter sp =new SimpleAdapter(
                        WeatherActivity.this,
                        list,
                        R.layout.wheather_list,
                        new String[] {"SiteName","Temperature","Moisture","WindDirection","Weather"} ,
                        new int[] {R.id.text_CName,R.id.text_T,R.id.text_Moisture,R.id.text_WindDirection,R.id.text_Weather}
                );
                lv.setAdapter(sp);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }



    }
}
