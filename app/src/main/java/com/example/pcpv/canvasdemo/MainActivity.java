package com.example.pcpv.canvasdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.pcpv.canvasdemo.chart.ChartData;
import com.example.pcpv.canvasdemo.chart.ChartView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ChartView chartView = findViewById(R.id.chart);
//        chartView.setSpaceYAxis(40);
//        chartView.setSpaceXAxis(80);
//        chartView.setColumnVariableUnit(500);
//        chartView.setRowVariableUnit(10);
        chartView.setOriginCoordinateBottom(true);
        List<ChartData> chartDataList = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            chartDataList.add(new ChartData(i * 0.5f, i * 1));
        }
        chartView.setChartDataList(chartDataList);
        chartView.setChartListener(chartData -> {
            Log.i("log", "onCreate: "+chartData.getX());
            Log.i("log", "onCreate: "+chartData.getY());
        });
    }
}
