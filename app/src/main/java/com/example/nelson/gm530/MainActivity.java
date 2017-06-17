package com.example.nelson.gm530;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.nelson.gm530.activity.AirResultActivity;
import com.example.nelson.gm530.activity.DMainActivity;
import com.example.nelson.gm530.activity.StandardActivity;
import com.example.nelson.gm530.activity.WeatherActivity;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void OnClick(View view) {
        Intent intent = new Intent();
        Context context = getBaseContext();
        switch (view.getId()) {
            case R.id.smile:
                intent.setClass(context, DMainActivity.class);
                break;
            case R.id.danger:
                intent.setClass(context, AirResultActivity.class);
                break;
            case R.id.button4:
                intent.setClass(context, StandardActivity.class);
                break;
            case R.id.weather:
                intent.setClass(context, WeatherActivity.class);
                break;
        }
        startActivity(intent);
    }
}
