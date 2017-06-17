package com.example.nelson.gm530.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.BaseAdapter;
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

/**
 * Created by Neo on 2017/6/17.
 */

public class NetworkTask extends AsyncTask<Object, Integer, String> {
    private final static String TAG = NetworkTask.class.getSimpleName();
    private Context mContext;
    //    private ProgressDialog mProgress;
    private OnTaskCompleted mListener;

    public interface OnTaskCompleted {
        void onTaskCompleted(BaseAdapter adapter);
    }

    public NetworkTask(OnTaskCompleted listener) {
        this.mListener = listener;
    }

    @Override
    protected String doInBackground(Object... params) {
        mContext = (Context) params[0];
        String urlpath = (String) params[1];
        final HttpClient client = new DefaultHttpClient();
        final HttpGet get = new HttpGet(urlpath);
        try {
            HttpResponse response = client.execute(get);
            HttpEntity entity = response.getEntity();
            final String result = EntityUtils.toString(entity);
            return result;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        mProgress = new ProgressDialog(mContext);
//        mProgress.setCancelable(false);
//        mProgress = ProgressDialog.show(mContext, "連線中", "請稍候");
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.d(TAG, "Show Result : " + result);
        if (result != null) {

            try {
                JSONArray array = new JSONArray(result);
                JSONObject object = array.getJSONObject(0);
                ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject records = array.getJSONObject(i);
                    HashMap<String, Object> item = new HashMap<String, Object>();
                    item.put("County", records.getString("County"));
                    item.put("City", records.getString("SiteName"));
                    item.put("MajorPollutant", records.getString("MajorPollutant"));
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
                //FIXME  best use AirResultAdapter
                final SimpleAdapter adapter = new SimpleAdapter(mContext, list, R.layout.list_item, new String[]{"County", "City", "MajorPollutant"}, new int[]{R.id.Country, R.id.city, R.id.status});
                if (mListener != null) {
                    mListener.onTaskCompleted(adapter);
//                    mProgress.dismiss();
                } else {
//                    mProgress.setMessage("  Listener null !!!");
                }
            } catch (JSONException e) {
                e.printStackTrace();
//                mProgress.setMessage("  JSON JSONException");
            }
        } else {
//            mProgress.setMessage(" No Result !!!!!");
        }
    }
}

