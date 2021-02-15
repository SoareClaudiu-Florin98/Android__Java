package com.example.proiect.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.proiect.BarChartActivity;
import com.example.proiect.PieChartActivity;
import com.example.proiect.R;
import com.example.proiect.asyncTask.Callback;
import com.example.proiect.database.model.MijlocFix;
import com.example.proiect.database.model.ObiectInventar;
import com.example.proiect.database.service.MijlocFixService;
import com.example.proiect.database.service.ObiectInventarService;

import java.util.ArrayList;
import java.util.List;

public class ChartsFragment extends Fragment {

    private Button btnChart1;
    private Button btnChart2;
    private MijlocFixService mijlocFixService;
    private ObiectInventarService obicteDeInventarService;
    private List<MijlocFix> mijloaceFixe= new ArrayList<>();
    private List<ObiectInventar> obiecteDeInventar= new ArrayList<ObiectInventar>();

    public static ChartsFragment newInstance() {
        ChartsFragment fragment = new ChartsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_charts, container, false);

        btnChart1 = view.findViewById(R.id.btn_chart1);
        btnChart2 = view.findViewById(R.id.btn_chart2);

        btnChart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext().getApplicationContext(), PieChartActivity.class);
                intent.putExtra("mijloace_fixe", mijloaceFixe.size());
                intent.putExtra("obiecte_de_inventar", obiecteDeInventar.size());
                startActivity(intent);
            }
        });
        btnChart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getContext().getApplicationContext(), BarChartActivity.class);
                startActivity(intent2);
            }
        });

        mijlocFixService = new MijlocFixService(getContext().getApplicationContext());
        mijlocFixService.getAll(getAllFromDbCallback());
        obicteDeInventarService = new ObiectInventarService(getContext().getApplicationContext()) ;
        obicteDeInventarService.getAll(getAllFromDbCallback2());

        return view;
    }

    private Callback<List<MijlocFix>> getAllFromDbCallback() {
        return new Callback<List<MijlocFix>>() {
            @Override
            public void runResultOnUiThread(List<MijlocFix> result) {
                if (result != null) {
                    mijloaceFixe.addAll(result);
                }
            }
        };
    }
    private Callback<List<ObiectInventar>> getAllFromDbCallback2() {
        return new Callback<List<ObiectInventar>>() {
            @Override
            public void runResultOnUiThread(List<ObiectInventar> result) {
                if (result != null) {
                    obiecteDeInventar.addAll(result);
                }
            }
        };
    }
}