package com.example.nelson.gm530.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.nelson.gm530.R;
import com.example.nelson.gm530.utils.NetworkTask;

public class AirResultFragment extends Fragment {
    private final static String TAG = AirResultFragment.class.getSimpleName();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle arguments = getArguments();
        Log.d(TAG, " Show arguments 1 : " + arguments.getString("First"));
        Log.d(TAG, " Show arguments 2 : " + arguments.getString("Sec"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final Context context = container.getContext();
        final View root = inflater.inflate(R.layout.fragment_air_result, container, false);
        final ListView listview = (ListView) root.findViewById(R.id.lv);
        NetworkTask networktask = new NetworkTask(new NetworkTask.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(BaseAdapter adapter) {
                listview.setAdapter(adapter);
            }
        });
        networktask.execute(context, "http://opendata.epa.gov.tw/ws/Data/REWXQA/?$orderby=SiteName&$skip=0&$top=1000&format=json");
        return root;
    }
}
