package com.example.drs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class adbarchart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adbarchart);
        BarChart barChart = (BarChart) findViewById(R.id.barchart);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(883719f, 0));
        entries.add(new BarEntry(294542f, 1));
        entries.add(new BarEntry(7458f, 2));
        entries.add(new BarEntry(4457f, 3));
        entries.add(new BarEntry(16999f, 4));

        BarDataSet bardataset = new BarDataSet(entries, "Party votes");

        ArrayList<String> labels = new ArrayList<String>();

        labels.add("BJP");
        labels.add("INC");
        labels.add("BSP");
        labels.add("IND");
        labels.add("NOTA");

        BarData data = new BarData(labels, bardataset);
        barChart.setData(data); // set the data and list of labels into chart
        //barChart.setDescription("Set Bar Chart Description Here");  // set the description
        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        barChart.animateY(5000);
    }
}
