package com.example.pcpv.canvasdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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

        chartView.setBegunColumnVariable(0);
        chartView.setBegunRowVariable(0);
        chartView.setColumnVariableUnit(1000);
        chartView.setRowVariableUnit(1);
        chartView.setOriginCoordinateBottom(true);
        List<ChartData> chartDataList = new ArrayList<>();
        chartDataList.add(new ChartData(0, 1500));
        chartDataList.add(new ChartData(1, 3000));
        chartDataList.add(new ChartData(2, 1000));
        chartDataList.add(new ChartData(2.5f, 1500.0f));
        chartDataList.add(new ChartData(3, 4000));
        chartDataList.add(new ChartData(4, 2000));
        chartDataList.add(new ChartData(5, 1000));
        chartDataList.add(new ChartData(6, 6000));
        chartDataList.add(new ChartData(7, 4500));
        chartView.setChartDataList(chartDataList);
    }
}
