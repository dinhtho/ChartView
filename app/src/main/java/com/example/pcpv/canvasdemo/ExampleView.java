package com.example.pcpv.canvasdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by PCPV on 01/17/2018.
 */

public class ExampleView extends View {
    private static final String TAG = "ExampleView";
    private Paint paint;
    private Bitmap bitmap;
    private Path path;
    private int colorPaint;
    private float strokeWidth;
    private boolean manualDrawing;

    public ExampleView(Context context) {
        super(context);
    }

    public ExampleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context,attrs);
        initPaint();
        path = new Path();
        bitmap = BitmapFactory.decodeResource(getResources(),
                R.mipmap.ic_launcher);
    }

    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(colorPaint);
        paint.setStrokeWidth(strokeWidth);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ExampleView);
        colorPaint = ta.getColor(R.styleable.ExampleView_app_color_paint, Color.BLUE);
        strokeWidth =ta.getDimension(R.styleable.ExampleView_app_stroke_width,10);
        manualDrawing =ta.getBoolean(R.styleable.ExampleView_app_manual_drawing,true);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        canvas.drawPoint(30, 30, paint);

        canvas.drawLine(30, 100, 700, 100, paint);

        canvas.drawRect(30, 150, 500, 300, paint);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(600, 150, 1000, 300, paint);

        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(150, 450, 100, paint);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(600, 450, 100, paint);

        paint.setStyle(Paint.Style.FILL);
        canvas.drawOval(30, 700, 300, 800, paint);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawOval(600, 700, 900, 800, paint);


        Log.i(TAG, "onDraw: " + bitmap.getHeight());
        Log.i(TAG, "onDraw: " + bitmap.getWidth());

        int left = 30;
        int top = 900;


        Rect rect = new Rect(0, 0, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
        RectF rectF = new RectF(left, top, left + bitmap.getWidth(), top + bitmap.getHeight());
        canvas.drawRect(rectF, paint);
        canvas.drawBitmap(bitmap, rect, rectF, paint);

        paint.setTextSize(100);
        canvas.drawText("abc", 900, 900, paint);

        if(manualDrawing) {
            canvas.drawPath(path, paint);
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float pointX = event.getX();
        float pointY = event.getY();
        // Checks for the event that occurs
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(pointX, pointY);
                return true;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(pointX, pointY);
                break;
            default:
                return false;
        }
        // Force a view to draw again
        postInvalidate();
        return true;
    }
}
