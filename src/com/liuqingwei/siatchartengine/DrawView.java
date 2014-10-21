package com.liuqingwei.siatchartengine;

import android.content.Context;
import android.graphics.*;
import android.os.AsyncTask;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 图像绘制类
 * Created by Meteoral.Liu On MacOS
 * User: Meteoral
 * Date: 14-10-10
 * WebSite: http://www.liuqingwei.com
 * QQ: 120599662
 */
public class DrawView extends View{

    /** 获取当前页的数据 */
    public static final int GET_THIS_PAGE = 0;
    /** 获取上一页的数据 */
    public static final int GET_PRE_PAGE = 1;
    /** 获取下一页的数据 */
    public static final int GET_NEXT_PAGE = 2;
    /** 控件的宽 */
    private int width;
    /** 控件的高 */
    private int height;
    /** 渲染配置器 */
    private Renderer render;
    /** 控件的Rect */
    private Rect rect;
    /** 控件Context */
    private Context con;
    /** 滑动事件横向坐标 */
    private float eventX;
    /** 数据集 */
    private float[] drawData;
    /** 数据集中是否存在数据 */
    private boolean hasData;
    /** 待发送的URL值(不含参数) */
    private String _url = "http://www.bit-health.com/ecg/drawEcg.php";
    /** 待发送事件EventId */
    private String _eventId = "53b64a8611dbae4910000003";
    /** 待发送获取的页数 */
    private Integer _page = 0;

    public DrawView(Context context) {
        super(context);
        con = context;
        render = new Renderer();
    }
    public DrawView(Context context,Renderer renderer) {
        super(context);
        con = context;
        render = renderer;
    }
    /**
     * 获取请求的url地址
     * @return 返回url地址
     */
    public String getUrl() {
        return _url;
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
     * 设置请求url地址（不含参数）
     * @param _url 请求的url地址
     */
    public void setUrl(String _url) {
        this._url = _url;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        DrawBackgroud(canvas);
        if(drawData==null) {
            getDrawData(GET_THIS_PAGE);
        }
        if(hasData) {
            DrawChart(canvas);
        }
    }

    /**
     * 绘制心率图的背景
     * @param canvas 画布
     */
    public void DrawBackgroud(Canvas canvas)
    {
        /* 创建背景色画笔 */
        Paint background = new Paint();
        background.setColor(render.BACKGROUND_COLOR);//设置心率图灰色背景
        Paint backP = new Paint();
        backP.setColor(render.getSiatAxesColor());
        backP.setAlpha(100);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        rect = canvas.getClipBounds();
        canvas.drawRect(0, 0, width, height, background);
        /* 是否绘制横纵轴 */
        if(render.isSiatShowAxes()) {
            /* 心电图每小格40ms
             *  6000/40 = 屏幕横向共150个小格
             *  150个小格平均分布在width宽上
             *  横纵grid的步距 */
            final int gridStep = width / 150;
            /* 绘制竖向的红色grid */
            for (int k = 0; k < width / gridStep; k++) {
                if (k % 5 == 0) {//每隔5个格子粗体显示
                    backP.setStrokeWidth(2);
                    canvas.drawLine(k * gridStep, 0, k * gridStep, height, backP);
                } else {
                    backP.setStrokeWidth(1);
                    canvas.drawLine(k * gridStep, 0, k * gridStep, height, backP);
                }
            }
            /* 绘制横向的红色grid */
            for (int g = 0; g < height / gridStep; g++) {
                if (g % 5 == 0) {
                    backP.setStrokeWidth(2);
                    canvas.drawLine(0, g * gridStep, width, g * gridStep, backP);
                } else {
                    backP.setStrokeWidth(1);
                    canvas.drawLine(0, g * gridStep, width, g * gridStep, backP);
                }
            }
        }
        /* 是否绘制Label标签 */
        if (render.isSiatShowLabel()) {
            Paint labelPaint = new Paint();
            labelPaint.setColor(render.TEXT_COLOR);
            labelPaint.setTypeface(render.getSiatTextTypeface());
            labelPaint.setTextSize(render.getSiatChartTextSize());
            labelPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(render.getSiatChartLabel(), width / 2, height - 10, labelPaint);
        }
    }

    /**
     * 绘制曲线
     * @param canvas 画布
     */
    public void DrawChart(Canvas canvas)
    {
            Paint p = new Paint();
            canvas.saveLayerAlpha(new RectF(rect), 0xFF, Canvas.MATRIX_SAVE_FLAG);
            p.setColor(Color.BLACK);
            p.setStrokeWidth(3);
            canvas.drawLines(drawData,p);
    }

    /**
     * 发送请求得到Json类型的数据
     * @param dataType 获取方式(本页、上一页、下一页)
     * @return true有数据，false无数据
     */
    public boolean getDrawData(int dataType)
    {
            switch (dataType)
            {
                case GET_THIS_PAGE:{
                    new PostRequest().execute(GET_THIS_PAGE);
                }
                break;
                case GET_PRE_PAGE:{
                    new PostRequest().execute(GET_PRE_PAGE);
                }
                break;
                case GET_NEXT_PAGE:{
                    new PostRequest().execute(GET_NEXT_PAGE);
                }
                break;
                default:
                    new PostRequest().execute(GET_THIS_PAGE);
            }

        return hasData;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        if(render.isSiatScrollable()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    eventX = event.getX();
                    break;
                case MotionEvent.ACTION_UP:
                    if (eventX - event.getX() > width / 4) {
                        getDrawData(GET_PRE_PAGE);
                        if (hasData) {
                            Toast.makeText(con, "上一页", Toast.LENGTH_SHORT).show();
                        } else {
                            setPage(getPage() + 1);
                        }
                    } else if (event.getX() - eventX > width / 4) {
                        getDrawData(GET_NEXT_PAGE);
                        if (hasData) {
                            Toast.makeText(con, "下一页", Toast.LENGTH_SHORT).show();
                        } else {
                            setPage(getPage() - 1);
                        }
                    }
                    break;
            }
        }
        return true;
    }

    class PostRequest extends AsyncTask<Integer, String, String> {

        /**
         * 无参数构造发送请求函数
         * 默认构造url为http://www.bit-health.com/ecg/drawEcg.php
         * 默认起始页第0页
         */
        public PostRequest(){
        }

        public PostRequest(String url,String eventId,Integer page)
        {
            _eventId = eventId;
            _page = page;
            _url = url;
        }

        @Override
        protected String doInBackground(Integer... type) {
            String responseString = "";
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response;
            try {
                String urlParm = null;
                if(type[0]==GET_THIS_PAGE) {
                    urlParm = "?type="+_page+"&EventId="+_eventId;
                }
                else if(type[0]==GET_NEXT_PAGE) {
                    _page++;
                    urlParm = "?type="+_page+"&EventId="+_eventId;
                }
                else if(type[0]==GET_PRE_PAGE) {
                    _page--;
                    if(_page<0) _page=0;//页数不能小于0
                    urlParm = "?type="+_page+"&EventId="+_eventId;
                }
                response = httpclient.execute(new HttpGet(getUrl()+urlParm));
                StatusLine statusLine = response.getStatusLine();
                if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    out.close();
                    responseString = out.toString();
                } else{
                    //Closes the connection.
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                }
            } catch (ClientProtocolException e) {
                //TODO Handle problems..
            } catch (IOException e) {
                //TODO Handle problems..
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                JSONObject obj = new JSONObject(result);
                if (0 == obj.getInt("flag") || 1 == obj.getInt("flag"))//得到的回复中含有数据
                {
                    JSONArray data = obj.getJSONArray("data");//得到的心电图数据
                    hasData = true;
                    int step = render.getSiatLineStep();//步进值，默认为3，值越大步进越多，绘制越快，精度越低
                    final int counts = (data.length() / width) * step;//每个横向像素点所需步进的纵向值
                    final int halfHeight = height / 2;
                    drawData = new float[(width / step) * 4];
                    for (int i = 0; i < width / step; i++) {
                        drawData[i * 4] = i * step;
                        drawData[i * 4 + 1] = (-data.getInt(i * counts) * 0.5f) + halfHeight;
                        drawData[i * 4 + 2] = (i + 1) * step;
                        drawData[i * 4 + 3] = (-data.getInt((i + 1) * counts) * 0.5f) + halfHeight;
                    }
                } else if (2 == obj.getInt("flag"))//没有数据了
                {
                    hasData = false;
                }
            }catch(Exception e)
            {
                e.printStackTrace();
            }
            invalidate();
        }
    }

}
