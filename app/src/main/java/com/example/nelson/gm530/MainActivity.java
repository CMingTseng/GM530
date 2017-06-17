package com.example.nelson.gm530;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.nelson.gm530.fragment.AirResultFragment;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void OnClick(View view) {
        final Bundle arguments = new Bundle();
        arguments.putString("First", "value1");
        Fragment fragment = null;
        switch (view.getId()) {
            case R.id.smile:
                arguments.putString("Sec", "smile");
//                fragment = DMainFragment();
                break;
            case R.id.danger:
                arguments.putString("Sec", "danger");
                fragment = new AirResultFragment();
                break;
            case R.id.button4:
                arguments.putString("Sec", "button4");
//                fragment = standardFragment();
                break;
            case R.id.weather:
                arguments.putString("Sec", "WeatherFragment");
//                fragment = weatherFragment();
                break;
        }
        if (fragment != null) {
            fragment.setArguments(arguments);
            transFragment(fragment);
        }
    }

    private void transFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, fragment).commit();
    }
}
