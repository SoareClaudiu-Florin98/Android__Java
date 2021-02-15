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
import com.example.proiect.database.model.ObiectInventar;
import com.example.proiect.util.DateConverter;

import java.util.Date;
import java.util.List;

public class ObiectInventarAdapter extends ArrayAdapter {
    private Context context ;
    private int resource ;
    private List<ObiectInventar> obiecteInventar ;
    private LayoutInflater inflater ;
    public ObiectInventarAdapter(@NonNull Context context, int resource, @NonNull List objects,LayoutInflater inflater) {
        super(context, resource, objects);
        this.context = context ;
        this.resource = resource ;
        this.obiecteInventar = objects ;
        this.inflater = inflater ;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(resource,parent,false)  ;
        ObiectInventar obiectInventar  = obiecteInventar.get(position) ;
        if(obiectInventar!= null){
            addDenumireObiectInventar(view,obiectInventar.getDenumire());
            addDataAdaugare(view,obiectInventar.getDataAdaugare());
            addValoareObiectDeInventar(view,obiectInventar.getValoare());
            addCantitateObiectDeInventar(view,obiectInventar.getCantitate()) ;
            addCategorieObiectDeInventar(view,obiectInventar.getCategorie()) ;
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
    private void  addDenumireObiectInventar(View view , String denumire){
        TextView textView = view.findViewById(R.id.denumire_obiect_de_inventar_adapter) ;
        populareTextView(textView, denumire);
    }
    private void addDataAdaugare(View view , Date data){
        TextView textView = view.findViewById(R.id.data_adaugare_obiect_de_inventar_adapter) ;
        String string =context.getResources().getString(R.string.data_adaugare_adapter)  + new DateConverter().toString(data) ;
        populareTextView(textView,string);
    }
    private void addValoareObiectDeInventar(View view , float valoare){
        TextView textView = view.findViewById(R.id.valoare_obiect_de_inventar_adapter) ;
        populareTextView(textView,context.getResources().getString(R.string.valoare_adapter) + String.valueOf(valoare));
    }
    private void addCantitateObiectDeInventar(View view , int cantitate){
        TextView textView = view.findViewById(R.id.cantitate_obiect_de_inventar_adapter) ;
        populareTextView(textView,context.getResources().getString(R.string.cantitate_adapter) + String.valueOf(cantitate));
    }
    private void addCategorieObiectDeInventar(View view , String categorie){
        TextView textView = view.findViewById(R.id.categorie_obiect_de_inventar_adapter) ;
        populareTextView(textView,context.getResources().getString(R.string.categorie_adapter) + categorie);
    }
}
