package com.example.nelson.gm530;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

private Context context ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;


    }
    public void OnClick(View view)
    {
        Intent intent =new Intent();
        switch (view.getId())
        {
            case R.id.smile :
                intent.setClass(context,DMainActivity.class);
                startActivity(intent);
                break;

            case R.id.danger :
                intent.setClass(context,AirResult.class);
                startActivity(intent);
                break;

            case R.id.button4 :
                intent.setClass(context,standard.class);
                startActivity(intent);
                break;
            case R.id.weather :
                intent.setClass(context,weather.class);
                startActivity(intent);
        }
    }
}
