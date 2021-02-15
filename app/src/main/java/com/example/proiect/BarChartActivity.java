package com.example.proiect;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import com.example.proiect.asyncTask.Callback;
import com.example.proiect.database.model.ObiectInventar;
import com.example.proiect.database.service.ObiectInventarService;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class BarChartActivity extends AppCompatActivity {

    private ObiectInventarService obiectInventarService;
    private List<ObiectInventar> obiecteInventar = new ArrayList<>();
    List<String> valori = new ArrayList<>();
    List<Integer> counter = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);


        obiectInventarService = new ObiectInventarService(getApplicationContext());
        obiectInventarService.getAll(getAllFromDbCallback2());
        addDataSet();
    }

    private Callback<List<ObiectInventar>> getAllFromDbCallback2() {
        return new Callback<List<ObiectInventar>>() {
            @Override
            public void runResultOnUiThread(List<ObiectInventar> result) {
                if (result != null) {
                    obiecteInventar.clear();
                    obiecteInventar.addAll(result);

                    for (ObiectInventar obiectInventar : obiecteInventar) {
                        if(valori.contains(obiectInventar.getCategorie())){
                            Integer value = counter.get(valori.indexOf(obiectInventar.getCategorie()));
                            counter.set(valori.indexOf(obiectInventar.getCategorie()),value+obiectInventar.getCantitate());
                        }
                        else {
                            valori.add(obiectInventar.getCategorie());
                            counter.add(obiectInventar.getCantitate());
                        }
                    }

                    addDataSet();
                }
            }
        };
    }

    private void addDataSet() {
        BarChart barChart = findViewById(R.id.id_barchar);

        ArrayList<BarEntry> barEntryArrayList=new ArrayList<>();
        ArrayList<String> labelNames=new ArrayList<>();
        for(String val:valori)labelNames.add(val);

        for(int i=0;i<counter.size();i++){
            barEntryArrayList.add(new BarEntry(i,counter.get(i).intValue()));
        }

        BarDataSet barDataSet = new BarDataSet(barEntryArrayList,getString(R.string.colorbar));
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        BarData barData=new BarData(barDataSet);
        barChart.setData(barData);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelNames));

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labelNames.size());
        xAxis.setLabelRotationAngle(270);
        barChart.animateY(2000);
        barChart.invalidate();
    }

}