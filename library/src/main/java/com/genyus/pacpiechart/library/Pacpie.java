package com.genyus.pacpiechart.library;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Toast;

import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("DrawAllocation")
public class Pacpie extends View implements View.OnTouchListener{

    private final static String TAG = "PacpieChart";
    private final static float DEFAULT_LINE_STROKE_WIDTH = 3.0F;
    private final static float DEFUALT_SLICE_STROKE_WIDTH = 0.0F;

    private OnClickListener listener;
    private Context context;

    private PacpieState state = PacpieState.WAIT;
    private Paint slicePaints = new Paint();
    private Paint linePaints = new Paint();

    private int lineColor;
    private int sliceColor;
    private float lineStrokeWidth = DEFAULT_LINE_STROKE_WIDTH;
    private float sliceStrokeWidth = DEFUALT_SLICE_STROKE_WIDTH;
    private boolean lineAntiAlias = true;
    private boolean sliceAntiAlias = true;

    private boolean isRotationActivated = false;
    private int backgroundColor;

    private int diameter;
    private float start;
    private float sweep;

    private int maxConnection;
    private long touchDownTime;

    private List<PacpieSlice> slicesList = new ArrayList<>();

    public Pacpie(Context context) {
        super(context);

        Log.d(TAG, "create");

        this.context = context;

        this.lineColor = context.getResources().getColor(R.color.default_line_color);
        this.sliceColor = context.getResources().getColor(R.color.default_slice_color);
        this.backgroundColor = context.getResources().getColor(android.R.color.transparent);
    }

    public Pacpie(Context context, AttributeSet attrs) {
        super(context, attrs);

        Log.d(TAG, "create attrs");
        this.context = context;

        this.backgroundColor = context.getResources().getColor(android.R.color.transparent);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PacPieChart, 0, 0);
        try {
            this.sliceAntiAlias = ta.getBoolean(R.styleable.PacPieChart_sliceAntiAlias, true);
            this.sliceColor = ta.getColor(R.styleable.PacPieChart_sliceDefaultColor, context.getResources().getColor(R.color.default_slice_color));
            this.sliceStrokeWidth = ta.getFloat(R.styleable.PacPieChart_sliceStrokeWidth, DEFUALT_SLICE_STROKE_WIDTH);
            this.lineAntiAlias = ta.getBoolean(R.styleable.PacPieChart_lineAntiAlias, true);
            this.lineColor = ta.getColor(R.styleable.PacPieChart_lineDefaultColor, context.getResources().getColor(R.color.default_slice_color));;
            this.lineStrokeWidth = ta.getFloat(R.styleable.PacPieChart_lineStrokeWidth, DEFAULT_LINE_STROKE_WIDTH);;
            this.isRotationActivated = ta.getBoolean(R.styleable.PacPieChart_activate_rotation, isRotationActivated);
        } finally {
            ta.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (state != PacpieState.IS_READY_TO_DRAW) {
            return;
        }

        /**
         * settings the default value to slice and line
         */
        canvas.drawColor(backgroundColor);
        slicePaints.setAntiAlias(lineAntiAlias);
        slicePaints.setStyle(Paint.Style.FILL);
        slicePaints.setColor(sliceColor);
        slicePaints.setStrokeWidth(sliceStrokeWidth);
        linePaints.setAntiAlias(sliceAntiAlias);
        linePaints.setColor(lineColor);
        linePaints.setStrokeWidth(lineStrokeWidth);
        linePaints.setStyle(Paint.Style.STROKE);

        /**
         * calculate wich size we gonna choose to draw our circle correctly
         */
        diameter = this.getMeasuredWidth();
        if(this.getMeasuredHeight() < diameter){
            diameter = this.getMeasuredHeight();
        }

        /**
         * calculate padding to center our circle in the view
         */
        int paddingVertical = (this.getMeasuredHeight() - diameter)/2;
        int paddingHorizontal = (this.getMeasuredWidth() - diameter)/2;

        RectF mOvals = new RectF(this.getPaddingLeft(), this.getPaddingTop(), diameter - this.getPaddingRight(), diameter - this.getPaddingBottom());
        mOvals.offsetTo(paddingHorizontal+getPaddingLeft(), paddingVertical+getPaddingTop());
        start = PacpieState.START_INC.stateCode;

        /**
         * draw each slice
         */
        for (int i = 0; i < slicesList.size(); i++) {
            PacpieSlice item = slicesList.get(i);

            slicePaints.setColor(sliceColor);
            if(-1 != item.color){
                slicePaints.setColor(item.color);
            }

            if(0F == item.count){
                throw new RuntimeException("percent is 0, will not be draw");
            }

            sweep = (float) 360 * ((float) item.count / (float) maxConnection);
            Log.d(TAG, "sweep = " + sweep + " & start = " + start);
            canvas.drawArc(mOvals, start, sweep, true, slicePaints);
            canvas.drawArc(mOvals, start, sweep, true, linePaints);
            start = start + sweep;
        }

        this.setOnTouchListener(this);
        state = PacpieState.IS_DRAW;
    }

    public void setValues(List<PacpieSlice> data) {
        if(null == data || 0 == data.size()){
            throw new RuntimeException("data cannot be null or empty !");
        }
        slicesList = data;
        for(int i = 0 ; i<data.size() ; ++i){
            maxConnection += data.get(i).count;
        }
        state = PacpieState.IS_READY_TO_DRAW;
    }

    public int getColorValues(int index) {
        if (slicesList == null) {
            return 0;
        } else if (index < 0)
            return ((PacpieSlice) slicesList.get(0)).color;
        else if (index > slicesList.size())
            return ((PacpieSlice) slicesList
                    .get(slicesList.size() - 1)).color;
        else
            return ((PacpieSlice) slicesList
                    .get(slicesList.size() - 1)).color;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        boolean handled = false;

        Log.d(TAG, "touch");
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                touchDownTime = SystemClock.elapsedRealtime();
                return true;

            case MotionEvent.ACTION_UP:
                Log.d(TAG, "time = "+ (SystemClock.elapsedRealtime() - touchDownTime));
                if (SystemClock.elapsedRealtime() - touchDownTime <= 300){
                    float x = event.getX();
                    float y = event.getY();
                    Log.d(TAG, "x = " + x);
                    Log.d(TAG, "y = " + y);


                    performClick(x, y);
                    handled = true;
                }
                return false;
        }

        return handled;
    }

    public void performClick(float x, float y){
        Toast.makeText(context, "click click", Toast.LENGTH_SHORT).show();
    }

    public void toggle(){
        if(View.GONE == this.getVisibility()){
            expand();
        } else {
            collapse();
        }
    }

    public void expand() {
        final int initialHeight = Pacpie.this.getMeasuredHeight();
        Pacpie.this.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {

                state = PacpieState.IS_READY_TO_DRAW;
                ViewHelper.setScaleX(Pacpie.this, interpolatedTime);
                ViewHelper.setScaleY(Pacpie.this, interpolatedTime);
                if(isRotationActivated){
                    ViewHelper.setRotation(Pacpie.this, 360 * interpolatedTime);
                }
                Pacpie.this.invalidate();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        a.setDuration((int) (initialHeight * 2 / Pacpie.this.getContext().getResources()
                .getDisplayMetrics().density));
        Pacpie.this.startAnimation(a);
    }

    public void collapse() {
        final int initialHeight = Pacpie.this.getMeasuredHeight();
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    Pacpie.this.setVisibility(View.GONE);
                } else {
                    state = PacpieState.IS_READY_TO_DRAW;
                    ViewHelper.setScaleX(Pacpie.this, 1 - interpolatedTime);
                    ViewHelper.setScaleY(Pacpie.this, 1 - interpolatedTime);
                    if(isRotationActivated){
                        ViewHelper.setRotation(Pacpie.this, 360 * (1-interpolatedTime));
                    }
                    Pacpie.this.invalidate();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        a.setDuration((int) (initialHeight * 2 / Pacpie.this.getContext().getResources()
                .getDisplayMetrics().density));
        Pacpie.this.startAnimation(a);
    }

    public void setRotation(boolean rotation){
        this.isRotationActivated = rotation;
    }

    public boolean isRotationActivated(){
        return this.isRotationActivated;
    }

    /**
     * @param state
     */
    public void setState(PacpieState state) {
        this.state = state;
    }

    /**
     * @return the current pie state
     * @see {PacpieState}
     */
    public PacpieState getState(){
        return this.state;
    }

    /**
     * @return the antiAlias for Slice
     */
    public boolean isAntiAliasForSlice() {
        return sliceAntiAlias;
    }

    /**
     * @param antiAlias the antiAlias to set
     */
    public void setSliceAntiAlias(boolean antiAlias) {
        this.sliceAntiAlias = antiAlias;
    }

    /**
     * @return the antiAlias for line
     */
    public boolean isAntiAliasForLine() {
        return lineAntiAlias;
    }

    /**
     * @param antiAlias the antiAlias to set
     */
    public void setLineAntiAlias(boolean antiAlias) {
        this.lineAntiAlias = antiAlias;
    }

    /**
     * @return the lineColor
     */
    public int getLineColor() {
        return lineColor;
    }

    /**
     * @param lineColor the lineColor to set
     */
    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }

    /**
     * @return the sliceColor
     */
    public int getSliceColor() {
        return sliceColor;
    }

    /**
     * @param sliceColor the sliceColor to set
     */
    public void setSliceColor(int sliceColor) {
        this.sliceColor = sliceColor;
    }

    /**
     * @return the lineStrokeWidth
     */
    public float getLineStrokeWidth() {
        return lineStrokeWidth;
    }

    /**
     * @param lineStrokeWidth the lineStrokeWidth to set
     */
    public void setLineStrokeWidth(float lineStrokeWidth) {
        this.lineStrokeWidth = lineStrokeWidth;
    }

    /**
     * @return the sliceStrokeWidth
     */
    public float getSliceStrokeWidth() {
        return sliceStrokeWidth;
    }

    /**
     * @param sliceStrokeWidth the sliceStrokeWidth to set
     */
    public void setSliceStrokeWidth(float sliceStrokeWidth) {
        this.sliceStrokeWidth = sliceStrokeWidth;
    }

    /**
     * @return the backgroundColor
     */
    public int getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * @param backgroundColor the backgroundColor to set
     */
    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
