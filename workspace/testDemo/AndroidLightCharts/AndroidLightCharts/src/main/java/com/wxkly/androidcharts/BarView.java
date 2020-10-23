package com.wxkly.androidcharts;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Dacer on 11/4/13.
 * update by wxkly on 10/19/2020
 */
public class BarView extends View {
    private final int bottomTextTopMargin = MyUtils.sp2px(getContext(), 5);
    private final int DEFAULT_BOTTOM_TEXT_SPACE_BETWEEN =35;
    private final int DEFAULT_BAR_WIDTH =20;
    private final int TOP_CHART_MARGIN = MyUtils.dip2px(getContext(), 45);
    ;
    private final int MIN_VERTICAL_GRID_NUM = 4;
    private final int MIN_HORIZONTAL_GRID_NUM = 1;

    private final int DEFAULT_BOTTOM_TEXT_COLOR = Color.parseColor("#02E1FF");

    private final int DEFAULT_BACKGROUND_LINE_COLOR = Color.parseColor("#02E1FF");

    private final int DEFAULT_BAR_COLOR = Color.parseColor("#80209FFF");

    private final int DEFAULT_BAR_TEXT_COLOR = Color.parseColor("#233745");

    /**
     * the color of the textviews  on X-axis
     */
    private int xTextColor;
    /**
     * the color of the textviews  on Y-axis
     */
    private int yTextColor;

    /**
     * the textview size on X-axis
     */
    private int xTextSize;
    /**
     * the textview size on Y-axis
     */
    private int yTextSize;
    /**
     * the  color of background Axis line
     */
    private int backgroundAxisColor;


    /**
     * the color of the bar
     */
    private int barColor;

    /**
     * the width of the bar
     */
    private int barWidth;

    public boolean showPopup = true;
    private int mViewHeight;
    private boolean autoSetDataOfGird = true;
    /**
     * max nums of Y-axis displayed text
     */
    private static final int MAX_Y_TEXT_NUM = 6;

    /**
     * the space between the textviews  on X or Y-axis
     */
    private int bottomTextSpaceBetween;

    // the interval value between the nearby Y-axis displayed text
    private double databetween;

    private int bottomTextHeight = 0;
    private ArrayList<String> bottomTextList = new ArrayList<String>();
    private ArrayList<Float> dataLists;
    private ArrayList<Integer> xCoordinateList = new ArrayList<Integer>();
    private ArrayList<Integer> yCoordinateList = new ArrayList<Integer>();
    private ArrayList<Rect> rectArrayList = new ArrayList<Rect>();
    //    private ArrayList<ArrayList<Dot>> drawDotLists = new ArrayList<ArrayList<Dot>>();
    private Paint xTextPaint = new Paint();
    private Paint yTextPaint = new Paint();
    private Paint barPaint = new Paint();
    private Paint popupTextPaint = new Paint();

    /**
     * the color of the textview on the top of bar
     */
    private int barTextColor;


    /**
     *  display or not the textview on the top of bar
     */
    private boolean isShowBarText;

    private int bottomTextDescent;
    private int maxYValue;
    private Dot selectedDot;
    private int popupBottomPadding = MyUtils.dip2px(getContext(), 2);
    /*
          |  | ←topLineLength
        --+--+--+--+--+--+--
        --+--+--+--+--+--+--
         ↑sideLineLength
     */
    private int topLineLength = MyUtils.dip2px(getContext(), 12);
    private int sideLineLength = MyUtils.dip2px(getContext(), 45) / 3 * 2;
    private Rect yTextRect = new Rect();
    private int startDrawX = 0;
    private int backgroundGridWidth = MyUtils.dip2px(getContext(), 45);
    private Boolean drawDotLine = false;
    //    private final int YCOORD_TEXT_LEFT_MARGIN = MyUtils.dip2px(getContext(), 40);
    private int[] colorArray = {
            Color.parseColor("#e74c3c"), Color.parseColor("#2980b9"), Color.parseColor("#1abc9c")
    };


    public BarView(Context context) {
        this(context, null);
    }


    public BarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.barView);
        xTextColor = array.getColor(R.styleable.barView_bar_x_text_color, DEFAULT_BOTTOM_TEXT_COLOR);
        yTextColor = array.getColor(R.styleable.barView_bar_y_text_color, DEFAULT_BOTTOM_TEXT_COLOR);
        barColor = array.getColor(R.styleable.barView_bar_color, DEFAULT_BAR_COLOR);
        barWidth= MyUtils.dip2px(getContext(), array.getInt(R.styleable.barView_bar_width, DEFAULT_BAR_WIDTH));
        bottomTextSpaceBetween = MyUtils.sp2px(getContext(), array.getInt(R.styleable.barView_bar_x_text_space, DEFAULT_BOTTOM_TEXT_SPACE_BETWEEN));
//        showPopupType = array.getInt(R.styleable.LineView2_show_popup_type, 0);
        xTextSize = array.getInt(R.styleable.barView_bar_x_text_size, 18);
        yTextSize = array.getInt(R.styleable.barView_bar_y_text_size, 18);

        barTextColor= array.getColor(R.styleable.barView_bar_pop_textcolor, DEFAULT_BAR_TEXT_COLOR);
        isShowBarText= array.getBoolean(R.styleable.barView_bar_show_pop_text, true);


        backgroundAxisColor = array.getColor(R.styleable.barView_bar_background_line_color, DEFAULT_BACKGROUND_LINE_COLOR);
        array.recycle();

        barPaint.setAntiAlias(true);
        barPaint.setColor(barColor);
        barPaint.setTextSize(MyUtils.sp2px(getContext(), 13));
        barPaint.setStrokeWidth(5);
        barPaint.setTextAlign(Paint.Align.CENTER);

        popupTextPaint.setAntiAlias(true);
        popupTextPaint.setColor(barTextColor);
        popupTextPaint.setTextSize(MyUtils.sp2px(getContext(), 15));
        popupTextPaint.setStrokeWidth(5);
        popupTextPaint.setTextAlign(Paint.Align.CENTER);

        xTextPaint.setAntiAlias(true);
        xTextPaint.setTextSize(MyUtils.sp2px(getContext(), xTextSize));
        xTextPaint.setTextAlign(Paint.Align.CENTER);
        xTextPaint.setStyle(Paint.Style.FILL);
        xTextPaint.setColor(xTextColor);

        yTextPaint.setAntiAlias(true);
        yTextPaint.setTextSize(MyUtils.sp2px(getContext(), yTextSize));
        yTextPaint.setTextAlign(Paint.Align.CENTER);
        yTextPaint.setStyle(Paint.Style.FILL);
        yTextPaint.setColor(yTextColor);
        refreshTopLineLength();
    }


    public void setDrawDotLine(Boolean drawDotLine) {
        this.drawDotLine = drawDotLine;
    }

    public void setColorArray(int[] colors) {
        this.colorArray = colors;
    }

    /**
     * dataList will be reset when called is method.
     *
     * @param bottomTextList The String ArrayList in the bottom.
     */
    public void setBottomTextList(ArrayList<String> bottomTextList) {
        this.bottomTextList = bottomTextList;

        Rect r = new Rect();
        int longestWidth = 0;
        bottomTextDescent = 0;
        for (String s : bottomTextList) {
            xTextPaint.getTextBounds(s, 0, s.length(), r);
            if (bottomTextHeight < r.height()) {
                bottomTextHeight = r.height();
            }
            if (longestWidth < r.width()) {
                longestWidth = r.width();
            }
            if (bottomTextDescent < (Math.abs(r.bottom))) {
                bottomTextDescent = Math.abs(r.bottom);
            }
        }
        backgroundGridWidth =
                longestWidth + (int) bottomTextSpaceBetween;
        if (sideLineLength < longestWidth / 2) {
            sideLineLength = longestWidth / 2;
        }

        refreshXCoordinateList(getHorizontalGridNum());
    }


    public void setDataList(ArrayList<Integer> dataLists) {
        ArrayList<Float> newList = new ArrayList<>();
        for (Integer integer : dataLists) {
            newList.add((float) integer);
        }
        setFloatDataList(newList);
    }

    public void setFloatDataList(ArrayList<Float> dataLists) {
        selectedDot = null;
        this.dataLists = dataLists;
        if (dataLists.size() > bottomTextList.size()) {
            throw new RuntimeException(
                    "dacer.LineView error:" + " dataList.size() > bottomTextList.size() !!!");
        }

        double biggestData = 0;
        if (autoSetDataOfGird) {
            for (Float i : dataLists) {
                if (biggestData < i) {
                    biggestData = i;
                }
            }
        }
        databetween = (double) (biggestData / MAX_Y_TEXT_NUM);
        if (biggestData < 10) {
            biggestData = 10;
        } else if (biggestData >= 10) {
            biggestData = biggestData / 10;
            biggestData = Math.ceil(biggestData);
            biggestData = biggestData * 10;
        }

        databetween = (double) (biggestData / MAX_Y_TEXT_NUM);
        if (databetween < 10) {
            databetween = 10;
        } else if (databetween >= 10) {
            databetween = databetween / 10;
            databetween = Math.ceil(databetween);
            databetween = databetween * 10;
        }
        maxYValue = (int) biggestData;
        String maxString = String.valueOf(maxYValue);
        xTextPaint.getTextBounds(maxString, 0, maxString.length(), yTextRect);
        startDrawX = yTextRect.width() + 10;
        refreshAfterDataChanged();
        Log.i("LineView", "databetween=" + databetween);
        Log.i("LineView", "maxY=" + maxYValue);
        showPopup = true;
        setMinimumWidth(0); // It can help the LineView reset the Width,
        postInvalidate();
    }

    private void refreshAfterDataChanged() {
        int verticalGridNum = getVerticalGridlNum();
        refreshYCoordinateList(maxYValue);
        refreshDrawDotList(maxYValue);
        refreshXCoordinateList(getHorizontalGridNum());
    }

    private int getVerticalGridlNum() {
        int verticalGridNum = MIN_VERTICAL_GRID_NUM;
        if (dataLists != null && !dataLists.isEmpty()) {
            for (Float f : dataLists) {
                if (verticalGridNum < (f + 1)) {
                    verticalGridNum = (int) Math.floor(f + 1);
                }
            }
        }
        return verticalGridNum;
    }

    private int getHorizontalGridNum() {
        int horizontalGridNum = bottomTextList.size() - 1;
        if (horizontalGridNum < MIN_HORIZONTAL_GRID_NUM) {
            horizontalGridNum = MIN_HORIZONTAL_GRID_NUM;
        }
        return horizontalGridNum;
    }

    private void refreshXCoordinateList(int horizontalGridNum) {
        xCoordinateList.clear();
        for (int i = 0; i < (horizontalGridNum + 1); i++) {
            xCoordinateList.add(sideLineLength + backgroundGridWidth * i + startDrawX);
        }
    }

    private void refreshYCoordinateList(int verticalGridNum) {
        yCoordinateList.clear();
        for (int i = 0; i < (verticalGridNum + 1); i++) {
            yCoordinateList.add(topLineLength + ((mViewHeight
                    - topLineLength
                    - bottomTextHeight
                    - bottomTextTopMargin
            ) * i / (verticalGridNum)));
        }
    }

    private void refreshDrawDotList(int verticalGridNum) {
        rectArrayList.clear();
        if (dataLists != null && !dataLists.isEmpty()) {
            for (int i = 0; i < dataLists.size(); i++) {
                int x = xCoordinateList.get(i);
                float y = getYAxesOf(dataLists.get(i), verticalGridNum);
                Rect rect = new Rect(x - barWidth / 2, (int) y, x + barWidth / 2, yCoordinateList.get(yCoordinateList.size() - 1));
                rectArrayList.add(rect);
            }
        }
    }

    private float getYAxesOf(float value, int verticalGridNum) {
        return topLineLength + ((mViewHeight
                - topLineLength
                - bottomTextHeight
                - bottomTextTopMargin
        ) * (verticalGridNum - value) / verticalGridNum);
    }

    private void refreshTopLineLength() {
        // For prevent popup can't be completely showed when backgroundGridHeight is too small.
        topLineLength = TOP_CHART_MARGIN;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBackgroundLines(canvas);
        drawRects(canvas);
        drawPopText(canvas);
//        drawDots(canvas);
    }


    private void drawRects(Canvas canvas) {
        Paint linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(MyUtils.dip2px(getContext(), 2));
        for (int k = 0; k < rectArrayList.size(); k++) {
            canvas.drawRect(rectArrayList.get(k), barPaint);
        }
    }
    private void drawPopText(Canvas canvas){
        if(isShowBarText) {
            for (int k = 0; k < rectArrayList.size(); k++) {
                Rect rect = rectArrayList.get(k);
                canvas.drawText(String.valueOf(dataLists.get(k).intValue()), rectArrayList.get(k).left + (float) rect.width() / 2, rectArrayList.get(k).top - 10, popupTextPaint);
            }
        }
    }
//    private void drawBackgroundLines(Canvas canvas) {
//        Paint paint = new Paint();
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(MyUtils.dip2px(getContext(), 1f));
//        paint.setColor(BACKGROUND_LINE_COLOR);
//        paint.setAlpha(25);
//        //绘制Y轴纵坐标背景线
//        Path dottedPath = new Path();
//        for (int i = 0; i < yCoordinateList.size(); i++) {
//            if ((yCoordinateList.size() - 1 - i) % databetween == 0) {
//                dottedPath.moveTo(startDrawX, yCoordinateList.get(i));
//                dottedPath.lineTo(getWidth(), yCoordinateList.get(i));
//            }
//        }
//        canvas.drawPath(dottedPath, paint);
//
//        //draw bottom text
//        paint.setAlpha(255);//去掉透明
//        if (bottomTextList != null) {
//            for (int i = 0; i < bottomTextList.size(); i++) {
//                float x = sideLineLength + backgroundGridWidth * i + startDrawX;
//                canvas.drawText(bottomTextList.get(i), x,
//                        mViewHeight - bottomTextDescent, bottomTextPaint);
//            }
//            //x 轴
//            canvas.drawLine(startDrawX, yCoordinateList.get(yCoordinateList.size() - 1), getWidth(),
//                    yCoordinateList.get(yCoordinateList.size() - 1),
//                    paint);
//
//            //y 轴
//            canvas.drawLine(startDrawX, yCoordinateList.get(yCoordinateList.size() - 1), startDrawX, yCoordinateList.get(0),
//                    paint);
//        }
//
//        int j = 0;
//        for (int i = yCoordinateList.size() - 1; i >= 0; i--) {
//            if ((i) % (databetween) == 0) {
//                canvas.drawText(String.valueOf(i), (int) (yTextRect.width() / 2), yCoordinateList.get(j) + (int) (yTextRect.height() / 2), bottomTextPaint);
//            }
//            j++;
//        }
//
//    }

    private void drawBackgroundLines(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(MyUtils.dip2px(getContext(), 1f));
        paint.setColor(backgroundAxisColor);
        paint.setAlpha(25);
        PathEffect effects = new DashPathEffect(new float[]{10, 5, 10, 5}, 1);

//
//        //draw vertical lines
//        for (int i = 0; i < xCoordinateList.size(); i++) {
//            canvas.drawLine(xCoordinateList.get(i), 0, xCoordinateList.get(i),
//                    mViewHeight - bottomTextTopMargin - bottomTextHeight - bottomTextDescent,
//                    paint);
//        }
//
//        //draw dotted lines
//        paint.setPathEffect(effects);
        //绘制Y轴纵坐标背景线
        Path dottedPath = new Path();
        for (int i = 0; i < yCoordinateList.size(); i++) {
            if ((yCoordinateList.size() - 1 - i) % databetween == 0) {
                dottedPath.moveTo(startDrawX, yCoordinateList.get(i));
                dottedPath.lineTo(getWidth(), yCoordinateList.get(i));
            }
        }
        canvas.drawPath(dottedPath, paint);
//        dottedPath.moveTo(0, 0);
//        dottedPath.lineTo(0, mViewHeight);
//        dottedPath.lineTo(getWidth(), mViewHeight);
//        dottedPath.lineTo(getWidth(), 0);
//        dottedPath.lineTo(0,0);
//        canvas.drawRect(0,0,getWidth(),mViewHeight, paint);
//        canvas.drawPath(dottedPath, paint);
        //draw bottom text
        paint.setAlpha(255);//remove  transparent
        if (bottomTextList != null) {
            for (int i = 0; i < bottomTextList.size(); i++) {
                float x = sideLineLength + backgroundGridWidth * i + startDrawX;
                canvas.drawText(bottomTextList.get(i), x,
                        mViewHeight - bottomTextDescent, xTextPaint);
            }
            //x axis
            canvas.drawLine(startDrawX, yCoordinateList.get(yCoordinateList.size() - 1), getWidth(),
                    yCoordinateList.get(yCoordinateList.size() - 1),
                    paint);

            //y axis
            canvas.drawLine(startDrawX, yCoordinateList.get(yCoordinateList.size() - 1), startDrawX, yCoordinateList.get(0),
                    paint);
        }

        int j = 0;
        for (int i = yCoordinateList.size() - 1; i >= 0; i--) {
            if ((i) % (databetween) == 0) {
                //draw Y-axis displayed text
                canvas.drawText(String.valueOf(i), (int) (yTextRect.width() / 2), yCoordinateList.get(j) + (int) (yTextRect.height() / 2), yTextPaint);
            }
            j++;
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mViewWidth = measureWidth(widthMeasureSpec);
        mViewHeight = measureHeight(heightMeasureSpec);
        //        mViewHeight = MeasureSpec.getSize(measureSpec);
        refreshAfterDataChanged();
        setMeasuredDimension(mViewWidth, mViewHeight);
    }

    private int measureWidth(int measureSpec) {
//        int horizontalGridNum = getHorizontalGridNum();
//        int preferred = backgroundGridWidth * horizontalGridNum + sideLineLength * 2;
        int preferred = getMinimumWidth();
        int mesureWidth = getMeasurement(measureSpec, preferred);
        backgroundGridWidth = (mesureWidth - yTextRect.width()) / bottomTextList.size();
        return mesureWidth;
    }

    private int measureHeight(int measureSpec) {
        int preferred = getMinimumHeight();
        return getMeasurement(measureSpec, preferred);
    }

    private int getMeasurement(int measureSpec, int preferred) {
        int specSize = MeasureSpec.getSize(measureSpec);
        int measurement;
        switch (MeasureSpec.getMode(measureSpec)) {
            case MeasureSpec.EXACTLY:
                measurement = specSize;
                break;
            case MeasureSpec.AT_MOST:
                measurement = Math.min(preferred, specSize);
                break;
            default:
                measurement = preferred;
                break;
        }
        return measurement;
    }

    class Dot {
        int x;
        float y;
        float data;
        int targetX;
        float targetY;
        int linenumber;
        int velocity = MyUtils.dip2px(getContext(), 18);

        Dot(int x, float y, int targetX, float targetY, float data, int linenumber) {
            this.x = x;
            this.y = y;
            this.linenumber = linenumber;
            setTargetData(targetX, targetY, data, linenumber);
        }

        Point setupPoint(Point point) {
            point.set(x, (int) y);
            return point;
        }

        Dot setTargetData(int targetX, float targetY, float data, int linenumber) {
            this.targetX = targetX;
            this.targetY = targetY;
            this.data = data;
            this.linenumber = linenumber;
            return this;
        }

        boolean isAtRest() {
            return (x == targetX) && (y == targetY);
        }

        void update() {
            x = (int) updateSelf(x, targetX, velocity);
            y = updateSelf(y, targetY, velocity);
        }

        private float updateSelf(float origin, float target, int velocity) {
            if (origin < target) {
                origin += velocity;
            } else if (origin > target) {
                origin -= velocity;
            }
            if (Math.abs(target - origin) < velocity) {
                origin = target;
            }
            return origin;
        }
    }
}
