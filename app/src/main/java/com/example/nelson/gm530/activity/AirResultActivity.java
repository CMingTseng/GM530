package com.example.nelson.gm530.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.nelson.gm530.R;
import com.example.nelson.gm530.utils.NetworkTask;

public class AirResultActivity extends AppCompatActivity {
    private final static String TAG = "AirResultActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air_result);
        final ListView listview = (ListView) findViewById(R.id.lv);
        NetworkTask newTask = new NetworkTask(new NetworkTask.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(BaseAdapter adapter) {
                listview.setAdapter(adapter);
            }
        });
        newTask.execute(getBaseContext(), "http://opendata.epa.gov.tw/ws/Data/REWXQA/?$orderby=SiteName&$skip=0&$top=1000&format=json");
    }
}
