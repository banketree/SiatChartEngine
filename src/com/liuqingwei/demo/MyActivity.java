package com.liuqingwei.demo;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.liuqingwei.siatchartengine.DrawView;
import com.liuqingwei.siatchartengine.R;
import com.liuqingwei.util.PostRequest;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */

    int statusCode = 0;
    String result;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        init();


    }
    private void init() {
        LinearLayout layout=(LinearLayout) findViewById(R.id.root);
        final DrawView view=new DrawView(this);
        view.setMinimumHeight(400);
        view.setMinimumWidth(500);

        //通知view组件重绘
        view.invalidate();
        layout.addView(view);
    }

}
