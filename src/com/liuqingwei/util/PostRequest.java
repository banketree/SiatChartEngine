package com.liuqingwei.util;

import android.os.StrictMode;
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
 * 提交请求类
 * Created by Meteoral.Liu On MacOS
 * User: Meteoral
 * Date: 14-10-10
 * WebSite: http://www.liuqingwei.com
 * QQ: 120599662
 */
public class PostRequest {

    private String _url;
    private DefaultHttpClient _client;
    private String _eventId;
    private Integer _page;

    /**
     * 无参数构造发送请求函数
     * 默认构造url为http://www.bit-health.com/ecg/drawEcg.php
     * 默认起始页第0页
     */
    public PostRequest(){
        _eventId ="53b64a8611dbae4910000003";
        _page = 0;
        _url = "http://www.bit-health.com/ecg/drawEcg.php";
        _client = new DefaultHttpClient(new BasicHttpParams());
    }

    /**
     * 构造发送请求的函数
     * 默认起始页第0页
     * @param url 不带参数的url
     */
    public PostRequest(String url)
    {
        String _eventId ="53b64a8611dbae4910000003";
        Integer _page = 0;
        _url = url;
        _client = new DefaultHttpClient(new BasicHttpParams());
    }

    /**
     * 构造发送请求的函数
     * @param eventId 发送请求的EventId
     * @param page 发送请求页数
     */
    public PostRequest(String eventId,Integer page)
    {
        _eventId = eventId;
        _page = page;
        _url = "http://www.bit-health.com/ecg/drawEcg.php";
        _client = new DefaultHttpClient(new BasicHttpParams());
    }
    /**
     * 构造发送请求的函数
     * @param url 发送请求的url(不含参数)
     * @param eventId 发送请求的EventId
     * @param page 发送请求页数
     */
    public PostRequest(String url,String eventId,Integer page)
    {
        _eventId = eventId;
        _page = page;
        _url = url;
        _client = new DefaultHttpClient(new BasicHttpParams());
    }

    /**
     * 获取请求的url地址
     * @return 返回url地址
     */
    public String getUrl() {
        return _url;
    }

    /**
     * 获取请求的HttpClient
     * @return 返回请求的HttpClient
     */
    public DefaultHttpClient getClient() {
        return _client;
    }

    /**
     * 获取请求的EventId
     * @return 返回EventId
     */
    public String getEventId() {
        return _eventId;
    }

    /**
     * 获取请求的页数
     * @return 返回页数
     */
    public Integer getPage() {
        return _page;
    }

    /**
     * 设置请求url地址（不含参数）
     * @param _url 请求的url地址
     */
    public void setUrl(String _url) {
        this._url = _url;
    }

    /**
     * 设置请求的HttpClient
     * @param _client 请求的HttpClient
     */
    public void setClient(DefaultHttpClient _client) {
        this._client = _client;
    }

    /**
     * 设置请求的EventID
     * @param _eventId 请求的EventId
     */
    public void setEventId(String _eventId) {
        this._eventId = _eventId;
    }

    /**
     * 设置请求页数
     * @param _page 请求页数
     */
    public void setPage(Integer _page) {
        this._page = _page;
    }

    /**
     * 发送请求，获取当前页Json格式数据
     * @return Json格式的字符串
     * @throws Exception
     */
    public String getJsonResponse() throws Exception{
        String urlParm = "?type="+_page+"&EventId="+_eventId;
        return requests(getUrl()+urlParm,getClient());
    }

    /**
     * 发送请求，获取上一页Json格式数据
     * @return Json格式的字符串
     * @throws Exception
     */
    public String getAutoNextJsonResponse() throws Exception{
        _page++;
        String urlParm = "?type="+_page+"&EventId="+_eventId;
        return requests(getUrl()+urlParm,getClient());
    }
    /**
     * 发送请求，获取下一页Json格式数据
     * @return Json格式的字符串
     * @throws Exception
     */
    public String getAutoPreJsonResponse() throws Exception{
        _page--;
        if(_page<0) _page=0;//页数不能小于0
        String urlParm = "?type="+_page+"&EventId="+_eventId;
        return requests(getUrl()+urlParm,getClient());
    }

    /**
     * 提交Get请求到指定的URL
     * @param url 已经格式化好带有参数的URL
     * @param client 模拟请求的客户端配置
     * @return 发出请求后得到的结果
     * @throws Exception
     */
    private String requests(String url,DefaultHttpClient client) throws Exception {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        String result = null;
        int statusCode = 0;
        HttpGet getMethod = new HttpGet(url);
        try {
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

    /**
     * 处理提交的信息
     * @param httpEntity
     * @return
     */
    private String retrieveInputStream(HttpEntity httpEntity) {

        int length = (int) httpEntity.getContentLength();
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
