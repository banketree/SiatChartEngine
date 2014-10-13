package com.liuqingwei.siatchartengine;

import android.graphics.Color;
import android.graphics.Typeface;

import java.io.Serializable;

/**
 * Created by Meteoral on 14-10-11.
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
    /** 是否应用了背景颜色 */
    private boolean siatApplyBackgroundColor;
    /** 坐标轴是否可见 */
    private boolean siatShowAxes = true;
    /** 坐标轴颜色 */
    private int siatAxesColor = Color.RED;
    /** 坐标标签是否可见 */
    private int siatLineStep = 3;
    /** 设置表格是否可滑动 */
    private boolean siatScrollable;

    public String getSiatChartLabel() {
        return siatChartLabel;
    }

    public void setSiatChartLabel(String siatChartLabel) {
        this.siatChartLabel = siatChartLabel;
    }

    public float getSiatChartTextSize() {
        return siatChartTextSize;
    }

    public void setSiatChartTextSize(float siatChartTextSize) {
        this.siatChartTextSize = siatChartTextSize;
    }

    public static Typeface getRegularTextFont() {
        return REGULAR_TEXT_FONT;
    }

    public String getSiatTextTypefaceName() {
        return siatTextTypefaceName;
    }

    public void setSiatTextTypefaceName(String siatTextTypefaceName) {
        this.siatTextTypefaceName = siatTextTypefaceName;
    }

    public int getSiatTextTypefaceStyle() {
        return siatTextTypefaceStyle;
    }

    public void setSiatTextTypefaceStyle(int siatTextTypefaceStyle) {
        this.siatTextTypefaceStyle = siatTextTypefaceStyle;
    }

    public int getSiatAxesColor() {
        return siatAxesColor;
    }

    public void setSiatAxesColor(int siatAxesColor) {
        this.siatAxesColor = siatAxesColor;
    }

    public Typeface getSiatTextTypeface() {
        return siatTextTypeface;
    }

    public void setSiatTextTypeface(Typeface siatTextTypeface) {
        this.siatTextTypeface = siatTextTypeface;
    }

    public int getSiatBackgroundColor() {
        return siatBackgroundColor;
    }

    public void setSiatBackgroundColor(int siatBackgroundColor) {
        this.siatBackgroundColor = siatBackgroundColor;
    }

    public boolean isSiatApplyBackgroundColor() {
        return siatApplyBackgroundColor;
    }

    public void setSiatApplyBackgroundColor(boolean siatApplyBackgroundColor) {
        this.siatApplyBackgroundColor = siatApplyBackgroundColor;
    }

    public boolean isSiatShowAxes() {
        return siatShowAxes;
    }

    public void setSiatShowAxes(boolean siatShowAxes) {
        this.siatShowAxes = siatShowAxes;
    }

    public int getSiatLineStep() {
        return siatLineStep;
    }

    public void setSiatLineStep(int siatLineStep) {
        this.siatLineStep = siatLineStep;
    }

    public boolean isSiatScrollable() {
        return siatScrollable;
    }

    public void setSiatScrollable(boolean siatScrollable) {
        this.siatScrollable = siatScrollable;
    }


    public boolean isSiatShowLabel() {
        return siatShowLabel;
    }

    public void setSiatShowLabel(boolean siatShowLabel) {
        this.siatShowLabel = siatShowLabel;
    }
}
