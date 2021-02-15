package com.example.proiect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.example.proiect.asyncTask.AsyncTaskRunner;
import com.example.proiect.asyncTask.Callback;
import com.example.proiect.database.DatabaseManager;
import com.example.proiect.database.model.MijlocFix;
import com.example.proiect.database.model.ObiectInventar;
import com.example.proiect.database.service.MijlocFixService;
import com.example.proiect.database.service.ObiectInventarService;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

public class PieChartActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";

    private float[] yData = {25.3f, 10.6f};
    private String[] xData = {"MijloaceFixe", "ObiecteInventar" };
    PieChart pieChart;

    private MijlocFixService mijlocFixService;
    private List<MijlocFix> mijloaceFixe = new ArrayList<>();

    private ObiectInventarService obiectInventarService;
    private List<ObiectInventar> obiecteInventar = new ArrayList<>();

    private float sumaMF = 0;
    private float sumaOI = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);

        Intent intent = getIntent() ;
        int mij_fixe=   intent.getIntExtra("mijloace_fixe",0) ;
        int ob_inv=   intent.getIntExtra("obiecte_de_inventar",0) ;
        yData[0] = mij_fixe ;
        yData[1] = ob_inv +3  ;

        pieChart = (PieChart) findViewById(R.id.idPieChart);
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText(getString(R.string.numar_active));
        pieChart.setCenterTextSize(10);

        addDataSet();
    }


    private void addDataSet() {
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        for(int i = 0; i < yData.length; i++){
            yEntrys.add(new PieEntry(yData[i] , i));
        }

        for(int i = 1; i < xData.length; i++){
            xEntrys.add(xData[i]);
        }

        PieDataSet pieDataSet = new PieDataSet(yEntrys, getString(R.string.mijloace_fixe_obiecte_inventar) );
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        ArrayList<Integer> colors = new ArrayList<>();

        colors.add(Color.BLUE);
        colors.add(Color.RED);

        pieDataSet.setColors(colors);

        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }
}