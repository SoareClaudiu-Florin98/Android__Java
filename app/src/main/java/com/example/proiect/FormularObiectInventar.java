package com.example.proiect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.proiect.database.model.ObiectInventar;
import com.example.proiect.util.DateConverter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;

public class FormularObiectInventar extends AppCompatActivity {
    public static final String NEW_OBIECT_INVENTAR_KEY = "NEW_OBIECT_INVENTAR_KEY";
    public static final String OBIECT_INVENTAR_SHARED_PREF = "OBIECT_INVENTAR_SHARED_PREF";
    public static final String DENUMIRE = "denumire";
    public static final String VALOARE = "valoare";
    public static final String DATA_ADAUGARE = "dataAdaugare";
    public static final String CATEGORIE = "categorie";
    public static final String CANTITATE = "cantitate";

    private TextInputEditText tietDenumire ;
    private TextInputEditText tietValoare ;
    private TextInputEditText tietDataAdaugare ;
    private TextInputEditText tietCategorie ;
    private TextInputEditText tietCantitate ;

    private Button btnSave ;
    private final DateConverter dateConverter = new DateConverter() ;
    private Intent intent ;
    private SharedPreferences preferences ;

    private ObiectInventar obiectInventar = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formular_obiect_inventar);
        initComponents();
        intent=getIntent() ;
        if (intent.hasExtra(NEW_OBIECT_INVENTAR_KEY)) {
            obiectInventar = (ObiectInventar) intent.getSerializableExtra(NEW_OBIECT_INVENTAR_KEY);
            buildViewsFromObiecteInventar(obiectInventar);
        }
    }
    private void initComponents(){
        tietDataAdaugare = findViewById(R.id.tiet_data_obiectInventar) ;
        tietDenumire = findViewById(R.id.tiet_denumire_obiectInventar) ;
        tietValoare = findViewById((R.id.tiet_valoare_obiectInventar)) ;
        tietCategorie = findViewById(R.id.tiet_categorie_obiect_inventar) ;
        tietCantitate = findViewById(R.id.tiet_cantitate_obiect_inventar) ;
        btnSave= findViewById(R.id.btn_saveobiectInventar) ;
        preferences= getSharedPreferences(OBIECT_INVENTAR_SHARED_PREF, MODE_PRIVATE) ;
        loadFromSharedPreference() ;
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validate()){
                    createMijlocFixFromView() ;
                    intent.putExtra(NEW_OBIECT_INVENTAR_KEY,  obiectInventar) ;
                    setResult(RESULT_OK,intent);
                    finish();
                    tietDenumire.setText("");
                    tietDataAdaugare.setText("");
                    tietValoare.setText("");
                    tietCategorie.setText("");
                    tietCantitate.setText("");
                }
            }
        });
    }
    private boolean validate() {
        if (tietDataAdaugare.getText() == null || dateConverter.fromString(tietDataAdaugare.getText().toString()) == null) {
            Toast.makeText(getApplicationContext(),
                    R.string.invalid_date,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (tietValoare.getText() == null || tietValoare.getText().toString().trim().length() < 0   || Float.parseFloat(tietValoare.getText().toString().trim()) > 2500) {
            Toast.makeText(getApplicationContext(),
                    R.string.invalid_value_ob_inv,
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
    private void createMijlocFixFromView() {
        Date date = dateConverter.fromString(tietDataAdaugare.getText().toString());
        Float valoare = Float.parseFloat(tietValoare.getText().toString());
        String denumire = tietDenumire.getText().toString();
        int cantitate = Integer.parseInt( tietCantitate.getText().toString() );
        String categorie = tietCategorie.getText().toString() ;
        if(obiectInventar==null){
            obiectInventar = new ObiectInventar(denumire,valoare,date,cantitate,categorie);
        }
        else {
            obiectInventar.setDataAdaugare(date);
            obiectInventar.setValoare(valoare);
            obiectInventar.setDenumire(denumire);
            obiectInventar.setCategorie(categorie);
            obiectInventar.setCantitate(cantitate);
        }
    }
    private void loadFromSharedPreference(){
        String denumire = preferences.getString(DENUMIRE,"") ;
        String valoare = preferences.getString(VALOARE,"") ;
        String dataAdaugare = preferences.getString(DATA_ADAUGARE,"") ;
        String cantitate = preferences.getString(CANTITATE,"") ;
        String categorie = preferences.getString(CATEGORIE,"") ;
        tietDenumire.setText(denumire);
        tietValoare.setText(valoare);
        tietDataAdaugare.setText(dataAdaugare);
        tietCantitate.setText(cantitate);
        tietCategorie.setText(categorie);

    }
    private void getSharedPref() {
        String denumire = tietDenumire.getText() != null ? tietDenumire.getText().toString() : "";
        String valoare = tietValoare.getText().toString();
        String dataAdaugare = tietDataAdaugare.getText().toString();
        String categorie = tietCategorie.getText().toString() ;
        String cantitate = tietCantitate.getText().toString() ;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(DENUMIRE, denumire);
        editor.putString(VALOARE, valoare);
        editor.putString(DATA_ADAUGARE, dataAdaugare);
        editor.putString(CANTITATE,cantitate) ;
        editor.putString(CATEGORIE,categorie) ;
        editor.apply();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSharedPref();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getSharedPref();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        getSharedPref();
    }
    private void buildViewsFromObiecteInventar(ObiectInventar obiectInventar) {
        if (obiectInventar == null) {
            return;
        }
        if (obiectInventar.getDataAdaugare() != null) {
            tietDataAdaugare.setText(DateConverter.toString(obiectInventar.getDataAdaugare()));
        }
        if (obiectInventar.getValoare() != 0.0f) {
            tietValoare.setText(String.valueOf(obiectInventar.getValoare()));
        }
        tietDenumire.setText(obiectInventar.getDenumire());
        tietCategorie.setText(obiectInventar.getCategorie());
        tietCantitate.setText(String.valueOf(obiectInventar.getCantitate()));
    }


}