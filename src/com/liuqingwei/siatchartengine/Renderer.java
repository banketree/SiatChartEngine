package com.liuqingwei.siatchartengine;

import android.graphics.Color;
import android.graphics.Typeface;

import java.io.Serializable;

/**
 * 心率图渲染器类
 * Created by Meteoral.Liu On MacOS
 * User: Meteoral
 * Date: 14-10-10
 * WebSite: http://www.liuqingwei.com
 * QQ: 120599662
 */
public class Renderer implements Serializable{
    /** 表格标题 */
    private String siatChartLabel = "";
    /** 是否显示标题 */
    private boolean siatShowLabel = false;
    /** 表格字体 */
    private float siatChartTextSize = 24;
    /** 没有颜色值 */
    public static final int NO_COLOR = 0;
    /** 默认背景颜色-灰白色 */
    public static final int BACKGROUND_COLOR = 0xFFF0F0F0;
    /** 默认字体颜色 */
    public static final int TEXT_COLOR = Color.BLACK;
    /** 建立一个普通的字体样式 */
    private static final Typeface REGULAR_TEXT_FONT = Typeface
            .create(Typeface.SERIF, Typeface.NORMAL);
    /** 字体名 */
    private String siatTextTypefaceName = REGULAR_TEXT_FONT.toString();
    /** 字体样式 */
    private int siatTextTypefaceStyle = Typeface.NORMAL;
    /** 字体typeface */
    private Typeface siatTextTypeface;
    /** 表格的背景颜色 */
    private int siatBackgroundColor;
    /** 坐标轴是否可见 */
    private boolean siatShowAxes = true;
    /** 坐标轴颜色 */
    private int siatAxesColor = Color.RED;
    /** 坐标标签是否可见 */
    private int siatLineStep = 1;
    /** 设置表格是否可滑动翻页 */
    private boolean siatScrollable = true;

    /**
     * 获取表格标题
     * @return 返回表格标题
     */
    public String getSiatChartLabel() {
        return siatChartLabel;
    }

    /**
     * 设置表格标题
     * @param siatChartLabel 字符串 表格标题
     */
    public void setSiatChartLabel(String siatChartLabel) {
        this.siatChartLabel = siatChartLabel;
    }

    /**
     * 获取表格标题字体大小
     * @return 返回表格标题字体大小
     */
    public float getSiatChartTextSize() {
        return siatChartTextSize;
    }

    /**
     * 设置表格标题字体大小
     * @param siatChartTextSize 字体大小
     */
    public void setSiatChartTextSize(float siatChartTextSize) {
        this.siatChartTextSize = siatChartTextSize;
    }

    /**
     * 获取表格标题字体样式
     * @return 返回表格标题字体样式
     */
    public static Typeface getRegularTextFont() {
        return REGULAR_TEXT_FONT;
    }

    /**
     * 获取表格标题字体样式名称
     * @return 返回样式名称
     */
    public String getSiatTextTypefaceName() {
        return siatTextTypefaceName;
    }

    /**
     * 设置表格标题字体样式
     * @param siatTextTypefaceName 字体样式名
     */
    public void setSiatTextTypefaceName(String siatTextTypefaceName) {
        this.siatTextTypefaceName = siatTextTypefaceName;
    }

    /**
     * 获取表格FaceStyle
     * @return FaceStyle
     */
    public int getSiatTextTypefaceStyle() {
        return siatTextTypefaceStyle;
    }

    /**
     * 设置表格FaceStyle
     * @param siatTextTypefaceStyle Facestyle属性
     */
    public void setSiatTextTypefaceStyle(int siatTextTypefaceStyle) {
        this.siatTextTypefaceStyle = siatTextTypefaceStyle;
    }

    /**
     * 获取表格背景横纵轴直线的颜色
     * @return 返回颜色值
     */
    public int getSiatAxesColor() {
        return siatAxesColor;
    }

    /**
     * 设置表格背景横纵轴直线的颜色
     * @param siatAxesColor 需设置的颜色值
     */
    public void setSiatAxesColor(int siatAxesColor) {
        this.siatAxesColor = siatAxesColor;
    }

    /**
     * 获取表格主背景色
     * @return 返回背景色值
     */
    public int getSiatBackgroundColor() {
        return siatBackgroundColor;
    }

    /**
     * 设置表格主背景色
     * @param siatBackgroundColor 需设置的背景色值
     */
    public void setSiatBackgroundColor(int siatBackgroundColor) {
        this.siatBackgroundColor = siatBackgroundColor;
    }

    /**
     * 获取字体Tyepface
     * @return 字体的Tyepface
     */
    public Typeface getSiatTextTypeface() {
        return siatTextTypeface;
    }

    /**
     * 设置字体的Typeface
     * @param siatTextTypeface 字体Typeface
     */
    public void setSiatTextTypeface(Typeface siatTextTypeface) {
        this.siatTextTypeface = siatTextTypeface;
    }

    /**
     * 是否显示背景的横纵轴
     * @return true显示，false不显示
     */
    public boolean isSiatShowAxes() {
        return siatShowAxes;
    }

    /**
     * 设置是否显示背景的横纵轴
     * @param siatShowAxes true显示，false不显示
     */
    public void setSiatShowAxes(boolean siatShowAxes) {
        this.siatShowAxes = siatShowAxes;
    }

    /**
     * 获取表格中数据的步进值
     * @return 步进值大小
     */
    public int getSiatLineStep() {
        return siatLineStep;
    }

    /**
     * 设置表格中数据的步进值
     * 步进值越大，表格中数据绘制越快，绘制的质量越低
     * 步进值越小，表格中数据绘制越慢，绘制越精细
     * @param siatLineStep 步进值，最小为1.最大为10
     */
    public void setSiatLineStep(int siatLineStep) {
        if(siatLineStep<1) siatLineStep=1;
        if(siatLineStep>10) siatLineStep=10;
        this.siatLineStep = siatLineStep;
    }

    /**
     * 查询表格是否可以左右滑动加载数据
     * @return true可滑动加载，false不可滑动加载
     */
    public boolean isSiatScrollable() {
        return siatScrollable;
    }

    /**
     * 设置表格左右滑动加载数据开关
     * @param siatScrollable true可滑动加载数据，false不可滑动加载数据
     */
    public void setSiatScrollable(boolean siatScrollable) {
        this.siatScrollable = siatScrollable;
    }

    /**
     * 是否显示表格的标题
     * @return true显示表格标题，false不显示表格标题
     */
    public boolean isSiatShowLabel() {
        return siatShowLabel;
    }

    /**
     * 设置是否显示表格的标题
     * @param siatShowLabel true显示表格标题，false不显示表格标题
     */
    public void setSiatShowLabel(boolean siatShowLabel) {
        this.siatShowLabel = siatShowLabel;
    }
}
