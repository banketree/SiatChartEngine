package com.liuqingwei.util;

import android.app.DownloadManager;
import android.widget.ProgressBar;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by Meteoral on 14-10-11.
 */
public class PostRequest {

    private String _url;
    private DefaultHttpClient _client;
    private String _eventId;
    private Integer _page;


    public PostRequest(){
        _eventId ="53b64a8611dbae4910000003";
        _page = 0;
        _url = "http://www.bit-health.com/ecg/drawEcg.php";
        _client = new DefaultHttpClient(new BasicHttpParams());
    }
    public PostRequest(String url)
    {
        String _eventId ="53b64a8611dbae4910000003";
        Integer _page = 0;
        _url = url;
        _client = new DefaultHttpClient(new BasicHttpParams());
    }
    public PostRequest(String eventId,Integer page)
    {
        _eventId = eventId;
        _page = page;
        _url = "http://www.bit-health.com/ecg/drawEcg.php";
        _client = new DefaultHttpClient(new BasicHttpParams());
    }
    public String getUrl() {
        return _url;
    }

    public DefaultHttpClient getClient() {
        return _client;
    }

    public String getEventId() {
        return _eventId;
    }

    public Integer getPage() {
        return _page;
    }

    public void setUrl(String _url) {
        this._url = _url;
    }

    public void setClient(DefaultHttpClient _client) {
        this._client = _client;
    }

    public void setEventId(String _eventId) {
        this._eventId = _eventId;
    }

    public void setPage(Integer _page) {
        this._page = _page;
    }

    public String getJsonResponse() throws Exception{
        String urlParm = "?type="+_page+"&EventId="+_eventId;
        return requests(getUrl()+urlParm,getClient());
    }
    public String getAutoNextJsonResponse() throws Exception{
        String urlParm = "?type="+_page+"&EventId="+_eventId;
        _page++;
        return requests(getUrl()+urlParm,getClient());
    }
    public String getAutoPreJsonResponse() throws Exception{
        String urlParm = "?type="+_page+"&EventId="+_eventId;
        _page--;
        return requests(getUrl()+urlParm,getClient());
    }
    private String requests(String url,DefaultHttpClient client) throws Exception {
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
    private String retrieveInputStream(HttpEntity httpEntity) {

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
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }
}
