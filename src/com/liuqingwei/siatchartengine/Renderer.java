package com.liuqingwei.siatchartengine;

import android.graphics.Color;
import android.graphics.Typeface;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meteoral on 14-10-11.
 */
public class Renderer implements Serializable{
    /** 表格标题 */
    String siatChartTitle = "";
    /** 表格字体 */
    private float siatChartTextSize = 15;
    /** 没有颜色值 */
    public static final int NO_COLOR = 0;
    /** 默认背景颜色-白色 */
    public static final int BACKGROUND_COLOR = Color.WHITE;
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
    private int siatAxesColor = TEXT_COLOR;
    /** 坐标标签是否可见 */
    private boolean mShowLabels = true;
    /** 坐标标签颜色 */
    private int mLabelsColor = TEXT_COLOR;
    /** 坐标标签字体大小 */
    private float mLabelsTextSize = 10;
    /** If the legend is visible. */
    private boolean mShowLegend = true;
    /** The legend text size. */
    private float mLegendTextSize = 12;
    /** If the legend should size to fit. */
    private boolean mFitLegend = false;
    /** If the X axis grid should be displayed. */
    private boolean mShowGridX = false;
    /** If the Y axis grid should be displayed. */
    private boolean mShowGridY = false;
    /** If the custom text grid should be displayed on the X axis. */
    private boolean mShowCustomTextGridX = false;
    /** If the custom text grid should be displayed on the Y axis. */
    private boolean mShowCustomTextGridY = false;
    /** The antialiasing flag. */
    private boolean mAntialiasing = true;
    /** The legend height. */
    private int mLegendHeight = 0;
    /** The margins size. */
    private int[] mMargins = new int[] { 20, 30, 10, 20 };
    /** A value to be used for scaling the chart. */
    private float mScale = 1;
    /** A flag for enabling the pan. */
    private boolean mPanEnabled = true;
    /** A flag for enabling the zoom. */
    private boolean mZoomEnabled = true;
    /** A flag for enabling the visibility of the zoom buttons. */
    private boolean mZoomButtonsVisible = false;
    /** The zoom rate. */
    private float mZoomRate = 1.5f;
    /** A flag for enabling the external zoom. */
    private boolean mExternalZoomEnabled = false;
    /** The original chart scale. */
    private float mOriginalScale = mScale;
    /** A flag for enabling the click on elements. */
    private boolean mClickEnabled = false;
    /** The selectable radius around a clickable point. */
    private int selectableBuffer = 15;
    /** If the chart should display the values (available for pie chart). */
    private boolean mDisplayValues;

    /** 设置表格是否可滑动 */
    private boolean siatScrollable;
    /** The start angle for circular charts such as pie, doughnut, etc. */
    private float mStartAngle = 0;

}
