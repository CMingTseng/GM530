package com.example.nelson.gm530;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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

public class AirResult extends AppCompatActivity {

    ArrayList<HashMap<String,Object>> list;
    ListView lv ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air_result);

        lv=(ListView)findViewById(R.id.lv);
        list=new ArrayList<HashMap<String,Object>>();
        NetworkTask newTask=new NetworkTask();
        newTask.execute("http://opendata.epa.gov.tw/ws/Data/REWXQA/?$orderby=SiteName&$skip=0&$top=1000&format=json");
    }


    public class NetworkTask extends AsyncTask<String,Void,String> {

        ProgressDialog dialog;
        String resultString;
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            dialog=new ProgressDialog(AirResult.this);
            dialog.setCancelable(false);
            dialog=ProgressDialog.show(AirResult.this, "連線中", "請稍候");
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
                JSONArray array =new JSONArray(resultString);
                JSONObject object=array.getJSONObject(0);

                for (int i =0 ; i<array.length();i++)
                {
                    JSONObject records=array.getJSONObject(i);
                    HashMap<String,Object> item=new HashMap<String,Object>();
                        item.put("County",records.getString("County"));
                        item.put("City",records.getString("SiteName"));
                        item.put("MajorPollutant",records.getString("MajorPollutant"));
                   // if(records.getString("MajorPollutant")=="懸浮微粒") {


                //}


                    list.add(item);

                }
                    /*
                    if(records.getString("MajorPollutant")=="懸浮粒子")
                    {
                        item.put("img",R.drawable.sur);
                    }else {
                        item.put("img",R.drawable.common_google_signin_btn_icon_disabled);
                    }
                    */
                SimpleAdapter adapter=new SimpleAdapter(
                        AirResult.this,
                        list,
                        R.layout.list_item,
                        new String[]{"County","City","MajorPollutant"},
                        new int[]{ R.id.Country, R.id.city, R.id.status}
                );lv.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

}
