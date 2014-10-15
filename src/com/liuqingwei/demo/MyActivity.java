package com.liuqingwei.demo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import com.liuqingwei.siatchartengine.DrawView;
import com.liuqingwei.siatchartengine.R;
import com.liuqingwei.siatchartengine.Renderer;

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
        init();


    }
    private void init() {
        LinearLayout layout=(LinearLayout) findViewById(R.id.root);
        Renderer renderer = new Renderer();
        renderer.setSiatShowLabel(true);
        renderer.setSiatChartLabel("这是测试的心电图");
        renderer.setSiatLineStep(1);
        renderer.setSiatScrollable(true);
        renderer.getSiatBackgroundColor();

        final DrawView view=new DrawView(this,renderer);
        view.setMinimumHeight(400);
        view.setMinimumWidth(500);

        //通知view组件重绘
        view.invalidate();
        layout.addView(view);
    }

}
