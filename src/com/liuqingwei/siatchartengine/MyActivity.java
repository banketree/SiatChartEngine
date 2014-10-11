package com.liuqingwei.siatchartengine;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.LinearLayout;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

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
        view.setMinimumHeight(600);
        view.setMinimumWidth(900);
        //通知view组件重绘
        view.invalidate();
        layout.addView(view);
        try {
            result = getRequest("http://www.bit-health.com/ecg/drawEcg.php?type=1&EventId=53b64a8611dbae4910000003", new DefaultHttpClient(new BasicHttpParams()));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println(result);

    }
    protected static String getRequest(String url, DefaultHttpClient client) throws Exception {

        String result = null;
        int statusCode = 0;
        HttpGet getMethod = new HttpGet(url);
        try {

            //getMethod.setHeader("User-Agent", USER_AGENT);

            HttpResponse httpResponse = client.execute(getMethod);

            //statusCode == 200 正常

            statusCode = httpResponse.getStatusLine().getStatusCode();



            //处理返回的httpResponse信息

            result = retrieveInputStream(httpResponse.getEntity());
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            getMethod.abort();
        }
        return result;
    }
    protected static String retrieveInputStream(HttpEntity httpEntity) {

        int length = (int) httpEntity.getContentLength();

        //the number of bytes of the content, or a negative number if unknown. If the content length is known but exceeds Long.MAX_VALUE, a negative number is returned.

        //length==-1，下面这句报错，println needs a message

        if (length < 0) length = 10000;

        StringBuffer stringBuffer = new StringBuffer(length);

        try {

            InputStreamReader inputStreamReader = new InputStreamReader(httpEntity.getContent(), HTTP.UTF_8);

            char buffer[] = new char[length];

            int count;

            while ((count = inputStreamReader.read(buffer, 0, length - 1)) > 0) {

                stringBuffer.append(buffer, 0, count);

            }

        } catch (UnsupportedEncodingException e) {



        } catch (IllegalStateException e) {


        } catch (IOException e) {

        }

        return stringBuffer.toString();

    }
}
