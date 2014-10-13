package com.liuqingwei.siatchartengine;

import android.content.Context;
import android.graphics.*;
import android.view.View;
import com.liuqingwei.util.PostRequest;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Meteoral on 14-10-10.
 */

public class DrawView extends View{

    private int width;
    private int height;
    private PostRequest request;
    private Renderer render;
    private boolean isDrawn = false;
    private Rect rect;
    public DrawView(Context context) {
        super(context);
        render = new Renderer();
    }
    public DrawView(Context context,Renderer renderer) {
        super(context);
        render = renderer;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Canvas lineCanvas = new Canvas();

        // 创建画笔
        /** 创建背景色画笔 */
        Paint background = new Paint();
        background.setColor(render.BACKGROUND_COLOR);//设置心率图灰色背景

        Paint backP = new Paint();
        backP.setColor(render.getSiatAxesColor());
        backP.setAlpha(100);
        Paint p = new Paint();
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        canvas.drawRect(0,0,width,height,background);
        /** 心电图每小格40ms
         *  6000/40 = 屏幕横向共150个小格
         *  150个小格平均分布在width宽上
         *  横纵grid的步距 */
        final int gridStep = width/150;
        /** 绘制竖向的红色grid */
        for(int k=0;k<width/gridStep;k++)
        {
            if(k%5==0) {//每隔5个格子粗体显示
                backP.setStrokeWidth(2);
                canvas.drawLine(k * gridStep, 0, k * gridStep, height, backP);
            }
            else
            {
                backP.setStrokeWidth(1);
                canvas.drawLine(k * gridStep, 0, k * gridStep, height, backP);
            }
        }
        /** 绘制横向的红色grid */
        for(int g=0;g<height/gridStep;g++)
        {
            if(g%5==0) {
                backP.setStrokeWidth(2);
                canvas.drawLine(0,g*gridStep, width,g*gridStep, backP);
            }
            else
            {
                backP.setStrokeWidth(1);
                canvas.drawLine(0,g*gridStep, width,g*gridStep, backP);
            }
        }
        rect = canvas.getClipBounds();
        canvas.saveLayerAlpha(new RectF(rect),0xFF,Canvas.MATRIX_SAVE_FLAG);
        final int halfHeight = height/2;
        p.setColor(Color.BLACK);
        p.setStrokeWidth(3);
        try {
            request = new PostRequest();
            String result = request.getJsonResponse();
            JSONObject obj = new JSONObject(result);
            JSONArray data = obj.getJSONArray("data");//得到的心电图数据
            final int step = render.getSiatLineStep();//步进值，默认为3，值越大步进越多，绘制越快，精度越低
            final int counts = (data.length()/width)*step;//每个横向像素点所需步进的纵向值
            for(int i=0;i<width/step;i++)
            {
                canvas.drawLine(i*step, (-data.getInt(i*counts)*0.5f)+halfHeight, (i+1)*step, (-data.getInt((i+1)*counts)*0.5f)+halfHeight, p);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        if(render.isSiatShowLabel()) {
            Paint pa = new Paint();
            pa.setColor(render.TEXT_COLOR);
            pa.setTypeface(render.getSiatTextTypeface());
            pa.setTextSize(render.getSiatChartTextSize());
            pa.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(render.getSiatChartLabel(), width/2, height-10, pa);
        }
        /*
        canvas.drawText("画圆：", 10, 20, p);// 画文本
        canvas.drawCircle(60, 20, 10, p);// 小圆
        p.setAntiAlias(true);// 设置画笔的锯齿效果。 true是去除，大家一看效果就明白了
        canvas.drawCircle(120, 20, 20, p);// 大圆

        canvas.drawText("画线及弧线：", 10, 60, p);
        p.setColor(Color.GREEN);// 设置绿色
        canvas.drawLine(60, 40, 100, 40, p);// 画线
        canvas.drawLine(110, 40, 190, 80, p);// 斜线
        //画笑脸弧线
        p.setStyle(Paint.Style.STROKE);//设置空心
        RectF oval1=new RectF(150,20,180,40);
        canvas.drawArc(oval1, 180, 180, false, p);//小弧形
        oval1.set(190, 20, 220, 40);
        canvas.drawArc(oval1, 180, 180, false, p);//小弧形
        oval1.set(160, 30, 210, 60);
        canvas.drawArc(oval1, 0, 180, false, p);//小弧形

        canvas.drawText("画矩形：", 10, 80, p);
        p.setColor(Color.GRAY);// 设置灰色
        p.setStyle(Paint.Style.FILL);//设置填满
        canvas.drawRect(60, 60, 80, 80, p);// 正方形
        canvas.drawRect(60, 90, 160, 100, p);// 长方形

        canvas.drawText("画扇形和椭圆:", 10, 120, p);
        /* 设置渐变色 这个正方形的颜色是改变的 */
        /*
        Shader mShader = new LinearGradient(0, 0, 100, 100,
                new int[] { Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW,
                        Color.LTGRAY }, null, Shader.TileMode.REPEAT); // 一个材质,打造出一个线性梯度沿著一条线。
        p.setShader(mShader);
        // p.setColor(Color.BLUE);
        RectF oval2 = new RectF(60, 100, 200, 240);// 设置个新的长方形，扫描测量
        canvas.drawArc(oval2, 200, 130, true, p);
        // 画弧，第一个参数是RectF：该类是第二个参数是角度的开始，第三个参数是多少度，第四个参数是真的时候画扇形，是假的时候画弧线
        //画椭圆，把oval改一下
        oval2.set(210,100,250,130);
        canvas.drawOval(oval2, p);

        canvas.drawText("画三角形：", 10, 200, p);
        // 绘制这个三角形,你可以绘制任意多边形
        Path path = new Path();
        path.moveTo(80, 200);// 此点为多边形的起点
        path.lineTo(120, 250);
        path.lineTo(80, 250);
        path.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path, p);

        // 你可以绘制很多任意多边形，比如下面画六连形
        p.reset();//重置
        p.setColor(Color.LTGRAY);
        p.setStyle(Paint.Style.STROKE);//设置空心
        Path path1=new Path();
        path1.moveTo(180, 200);
        path1.lineTo(200, 200);
        path1.lineTo(210, 210);
        path1.lineTo(200, 220);
        path1.lineTo(180, 220);
        path1.lineTo(170, 210);
        path1.close();//封闭
        canvas.drawPath(path1, p);
        /*
         * Path类封装复合(多轮廓几何图形的路径
         * 由直线段*、二次曲线,和三次方曲线，也可画以油画。drawPath(路径、油漆),要么已填充的或抚摸
         * (基于油漆的风格),或者可以用于剪断或画画的文本在路径。
         */

        //画圆角矩形
        /*
        p.setStyle(Paint.Style.FILL);//充满
        p.setColor(Color.LTGRAY);
        p.setAntiAlias(true);// 设置画笔的锯齿效果
        canvas.drawText("画圆角矩形:", 10, 260, p);
        RectF oval3 = new RectF(80, 260, 200, 300);// 设置个新的长方形
        canvas.drawRoundRect(oval3, 20, 15, p);//第二个参数是x半径，第三个参数是y半径

        //画贝塞尔曲线
        canvas.drawText("画贝塞尔曲线:", 10, 310, p);
        p.reset();
        p.setStyle(Paint.Style.STROKE);
        p.setColor(Color.GREEN);
        Path path2=new Path();
        path2.moveTo(100, 320);//设置Path的起点
        path2.quadTo(150, 310, 170, 400); //设置贝塞尔曲线的控制点坐标和终点坐标
        canvas.drawPath(path2, p);//画出贝塞尔曲线

        //画点
        p.setStyle(Paint.Style.FILL);
        canvas.drawText("画点：", 10, 390, p);
        canvas.drawPoint(60, 390, p);//画一个点
        canvas.drawPoints(new float[]{60,400,65,400,70,400}, p);//画多个点

        //画图片，就是贴图
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        canvas.drawBitmap(bitmap, 250,360, p);
        */
    }

}
