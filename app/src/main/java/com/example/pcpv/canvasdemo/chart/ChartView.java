package com.example.pcpv.canvasdemo.chart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.pcpv.canvasdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PCPV on 01/17/2018.
 */

public class ChartView extends View {
    private static final String TAG = "ExampleView";
    private Paint paint;
    private Path path;
    private int colorPaint;
    private final int paddingBottom = 60;
    private final int paddingTop = 60;
    private final int paddingLeft = 60;
    private int width;
    private int height;
    private List<ChartData> chartDataList;
    private List<Point> points;
    private Point selectedPoint;
    private int spaceXAxis = 165;
    private int spaceYAxis = 90;
    private int rowVariableUnit = 1;
    private int columnVariableUnit = 500;
    private int begunRowVariable = 0;
    private int begunColumnVariable = 0;
    private final Rect textBounds = new Rect();
    private boolean isOriginCoordinateBottom = true;


    public ChartView(Context context) {
        super(context);
    }

    public ChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(colorPaint);
        paint.setStyle(Paint.Style.STROKE);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ChartView);
        colorPaint = ta.getColor(R.styleable.ChartView_app_color_paint, Color.BLUE);
    }

    public void setBegunRowVariable(int begunRowVariable) {
        this.begunRowVariable = begunRowVariable;
    }

    public void setBegunColumnVariable(int begunColumnVariable) {
        this.begunColumnVariable = begunColumnVariable;
    }

    public void setRowVariableUnit(int rowVariableUnit) {
        this.rowVariableUnit = rowVariableUnit;
    }

    public void setColumnVariableUnit(int columnVariableUnit) {
        this.columnVariableUnit = columnVariableUnit;
    }

    public void setSpaceXAxis(int spaceXAxis) {
        this.spaceXAxis = spaceXAxis;
    }

    public void setSpaceYAxis(int spaceYAxis) {
        this.spaceYAxis = spaceYAxis;
    }

    public void setOriginCoordinateBottom(boolean originCoordinateBottom) {
        isOriginCoordinateBottom = originCoordinateBottom;
    }

    public void setChartDataList(List<ChartData> chartDataList) {
        this.chartDataList = chartDataList;
        this.postDelayed(new Runnable() {
            @Override
            public void run() {
                createPoints();
                createNewPath();
                postInvalidate();
            }
        }, 200);
    }

    private void createPoints() {
        points = new ArrayList<>();
        for (ChartData chartData : chartDataList) {
            float x = paddingLeft + chartData.getX() / rowVariableUnit * spaceXAxis;
            float y = isOriginCoordinateBottom ? height - (paddingBottom + chartData.getY() /
                    columnVariableUnit * spaceYAxis) : chartData.getY() /
                    columnVariableUnit * spaceYAxis + paddingTop;
            points.add(new Point((int) x, (int) y));
        }
    }

    private void createNewPath() {
        path = new Path();
        if (points != null && points.size() > 0) {
            path.moveTo(points.get(0).x, points.get(0).y);
            for (int i = 1; i < points.size(); i++) {
                lineTo(points.get(i));
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initPaint();
        drawGraph(canvas);
        if (path != null) {
            drawLine(canvas);
        }
        if (selectedPoint != null) {
            drawSelectedPoint(canvas);
        }
    }

    private void drawLine(Canvas canvas) {
        paint.setStrokeWidth(7);
        paint.setColor(Color.RED);
        canvas.drawPath(path, paint);

    }

    private void drawSelectedPoint(Canvas canvas) {
        paint.setStrokeWidth(1);
        paint.setColor(Color.RED);
        canvas.drawLine(selectedPoint.x, height - paddingBottom, selectedPoint.x, paddingTop, paint);
        canvas.drawLine(paddingLeft, selectedPoint.y, width, selectedPoint.y, paint);
    }

    private void drawGraph(Canvas canvas) {
        paint.setStrokeWidth(2);
        drawColumns(canvas);
        drawRows(canvas);
    }

    private void drawColumns(Canvas canvas) {
        int numberOfColumn = (width - paddingLeft) / spaceXAxis + 1;
        for (int i = 0; i < numberOfColumn; i++) {
            drawTextCentred(canvas, paint, String.valueOf(begunRowVariable + rowVariableUnit * i),
                    paddingLeft + spaceXAxis * i, height - paddingBottom / 2);
            canvas.drawLine(paddingLeft + spaceXAxis * i, height - paddingBottom,
                    paddingLeft + spaceXAxis * i, paddingTop, paint);
        }
    }

    private void drawRows(Canvas canvas) {
        int numberOfRow = (height - paddingTop - paddingBottom) / spaceYAxis + 1;
        paint.setTextAlign(Paint.Align.RIGHT);
        for (int i = 0; i < numberOfRow; i++) {
            String text = String.valueOf(begunColumnVariable + columnVariableUnit * i);
            paint.getTextBounds(text, 0, text.length(), textBounds);
            int y = isOriginCoordinateBottom ? (height - paddingBottom) - spaceYAxis * i :
                    spaceYAxis * i + paddingTop;
            canvas.drawText(text, paddingLeft - 6, y - textBounds.exactCenterY(), paint);
            canvas.drawLine(paddingLeft, y, width, y, paint);
        }
    }

    public void drawTextCentred(Canvas canvas, Paint paint, String text, float cx, float cy) {
        paint.setTextSize(20);
        paint.getTextBounds(text, 0, text.length(), textBounds);
        canvas.drawText(text, cx - textBounds.exactCenterX(), cy - textBounds.exactCenterY(), paint);
    }

    private void lineTo(Point point) {
        path.lineTo(point.x, point.y);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.width = w;
        this.height = h;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float pointX = event.getX();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            selectedPoint = getPointFromTough(pointX);
            if (selectedPoint != null) {
                // Force a view to draw again
                postInvalidate();
            }
        }
        return true;
    }

    private Point getPointFromTough(float x) {
        for (int i = 0; i < points.size() - 1; i++) {
            if (x >= points.get(i).x && x < points.get(i + 1).x) {
                if (Math.abs((x - points.get(i).x)) < Math.abs((x - points.get(i + 1).x))) {
                    return points.get(i);
                } else {
                    return points.get(i + 1);
                }
            }
        }
        return null;
    }
}
