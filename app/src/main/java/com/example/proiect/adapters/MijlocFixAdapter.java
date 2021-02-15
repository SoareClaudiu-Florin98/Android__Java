package com.example.proiect.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.proiect.R;
import com.example.proiect.util.DateConverter;
import com.example.proiect.database.model.MijlocFix;

import java.util.Date;
import java.util.List;

public class MijlocFixAdapter extends ArrayAdapter {
    private Context context ;
    private int resource ;
    private List<MijlocFix> mijloaceFixe ;
    private LayoutInflater inflater ;

    public MijlocFixAdapter(@NonNull Context context, int resource, @NonNull List objects,LayoutInflater inflater) {
        super(context, resource, objects);
        this.context = context ;
        this.mijloaceFixe = objects ;
        this.inflater = inflater ;
        this.resource = resource ;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(resource,parent,false)  ;
        MijlocFix mijlocFix  = mijloaceFixe.get(position) ;
        if(mijlocFix != null){
            addDenumireMijlocFix(view,mijlocFix.getDenumire());
            addDataAdaugare(view,mijlocFix.getDataAdaugare());
            addValoareMijlocFix(view,mijlocFix.getValoare());

        }

        return view ;
    }
    private void populareTextView(TextView textView, String nume) {
        if (nume != null && !nume.isEmpty()) {
            textView.setText(nume);
        } else {
            textView.setText(R.string.default_value);
        }
    }
    private void  addDenumireMijlocFix(View view , String denumire){
        TextView textView = view.findViewById(R.id.denumire_mijloc_fix_adapter) ;
        populareTextView(textView, denumire);
    }
    private void  addValoareMijlocFix(View view , float valoare){
        TextView textView = view.findViewById(R.id.valoare_mijloc_fix_adapter) ;
        populareTextView(textView,context.getResources().getString(R.string.valoare_adapter) + String.valueOf(valoare));
    }
    private void addDataAdaugare(View view , Date data){
        TextView textView = view.findViewById(R.id.data_adaugare_mijloc_fix_adapter) ;
        String string =context.getResources().getString(R.string.data_adaugare_adapter)  + new DateConverter().toString(data) ;
        populareTextView(textView,string);
    }

}
