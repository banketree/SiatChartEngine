package com.liuqingwei.siatchartengine;

import android.content.Context;
import android.graphics.*;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import com.liuqingwei.util.PostRequest;
import org.json.JSONArray;
import org.json.JSONObject;

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
    /** 发送到服务器的请求 */
    private PostRequest request;
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
    public DrawView(Context context,Renderer renderer,PostRequest re) {
        super(context);
        con = context;
        render = renderer;
        request = re;
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
        else{
            Toast.makeText(con, "没有数据", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 绘制心率图的背景
     * @param canvas
     */
    public void DrawBackgroud(Canvas canvas)
    {
        /** 创建背景色画笔 */
        Paint background = new Paint();
        background.setColor(render.BACKGROUND_COLOR);//设置心率图灰色背景
        Paint backP = new Paint();
        backP.setColor(render.getSiatAxesColor());
        backP.setAlpha(100);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        rect = canvas.getClipBounds();
        canvas.drawRect(0, 0, width, height, background);
        /** 是否绘制横纵轴 */
        if(render.isSiatShowAxes()) {
            /** 心电图每小格40ms
             *  6000/40 = 屏幕横向共150个小格
             *  150个小格平均分布在width宽上
             *  横纵grid的步距 */
            final int gridStep = width / 150;
            /** 绘制竖向的红色grid */
            for (int k = 0; k < width / gridStep; k++) {
                if (k % 5 == 0) {//每隔5个格子粗体显示
                    backP.setStrokeWidth(2);
                    canvas.drawLine(k * gridStep, 0, k * gridStep, height, backP);
                } else {
                    backP.setStrokeWidth(1);
                    canvas.drawLine(k * gridStep, 0, k * gridStep, height, backP);
                }
            }
            /** 绘制横向的红色grid */
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
        /** 是否绘制Label标签 */
        if (render.isSiatShowLabel()) {
            Paint labelPaint = new Paint();
            labelPaint.setColor(render.TEXT_COLOR);
            labelPaint.setTypeface(render.getSiatTextTypeface());
            labelPaint.setTextSize(render.getSiatChartTextSize());
            labelPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(render.getSiatChartLabel(), width / 2, height - 10, labelPaint);
        }
    }

    public void DrawChart(Canvas canvas)
    {
            Paint p = new Paint();
            canvas.saveLayerAlpha(new RectF(rect), 0xFF, Canvas.MATRIX_SAVE_FLAG);
            p.setColor(Color.BLACK);
            p.setStrokeWidth(3);
            canvas.drawLines(drawData,p);
    }
    public boolean getDrawData(int dataType)
    {
        try {
            if(request==null) {
                request = new PostRequest();
            }
            String result;
            switch (dataType)
            {
                case GET_THIS_PAGE:{
                    result = request.getJsonResponse();
                }
                break;
                case GET_PRE_PAGE:{
                    result = request.getAutoPreJsonResponse();
                }
                break;
                case GET_NEXT_PAGE:{
                    result = request.getAutoNextJsonResponse();
                }
                break;
                default:
                    result = request.getJsonResponse();
            }
            JSONObject obj = new JSONObject(result);
            if(0==obj.getInt("flag")||1==obj.getInt("flag"))//得到的回复中含有数据
            {
                JSONArray data = obj.getJSONArray("data");//得到的心电图数据
                hasData = true;
                int step = render.getSiatLineStep();//步进值，默认为3，值越大步进越多，绘制越快，精度越低
                final int counts = (data.length() / width) * step;//每个横向像素点所需步进的纵向值
                final int halfHeight = height / 2;
                drawData = new float[(width/step)*4];
                for (int i = 0; i < width / step; i++) {
                    drawData[i*4] = i*step;
                    drawData[i*4+1] = (-data.getInt(i * counts) * 0.5f) + halfHeight;
                    drawData[i*4+2] = (i+1)*step;
                    drawData[i*4+3] = (-data.getInt((i+1) * counts) * 0.5f) + halfHeight;
                }
            }
            else if(2==obj.getInt("flag"))//没有数据了
            {
                hasData = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
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
                            postInvalidate();
                            Toast.makeText(con, "上一页", Toast.LENGTH_SHORT).show();
                        } else {
                            request.setPage(request.getPage() + 1);
                            Toast.makeText(con, "没有数据", Toast.LENGTH_SHORT).show();
                        }
                    } else if (event.getX() - eventX > width / 4) {
                        getDrawData(GET_NEXT_PAGE);
                        if (hasData) {
                            postInvalidate();
                            Toast.makeText(con, "下一页", Toast.LENGTH_SHORT).show();
                        } else {
                            request.setPage(request.getPage() - 1);
                            Toast.makeText(con, "最后一页", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
            }
        }
        return true;
    }

}
