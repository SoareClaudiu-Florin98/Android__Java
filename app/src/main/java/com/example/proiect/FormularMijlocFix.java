package com.example.proiect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.proiect.util.DateConverter;
import com.example.proiect.database.model.MijlocFix;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;

public class FormularMijlocFix extends AppCompatActivity {
    public static final String NEW_MijlocFix_KEY = "new_MijlocFix_key";
    public static final String MIJ_FIXE_SHARED_PREF = "mij_fixe_shared_pref_good";
    public static final String DENUMIRE = "denumire";
    public static final String VALOARE = "valoare";
    public static final String DATA_ADAUGARE = "dataAdaugare";

    private TextInputEditText tietDenumire ;
    private TextInputEditText tietValoare ;
    private TextInputEditText tietDataAdaugare ;
    private Button btnSave ;
    private final DateConverter dateConverter = new DateConverter() ;
    private Intent intent ;
    private SharedPreferences preferences ;
    private MijlocFix mijlocFix = null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formular_mijloc_fix);
        initComponents();
        intent=getIntent() ;
        if (intent.hasExtra(NEW_MijlocFix_KEY)) {
            mijlocFix = (MijlocFix) intent.getSerializableExtra(NEW_MijlocFix_KEY);
            buildViewsFromMijloaceFixe(mijlocFix);
        }
    }

    private void buildViewsFromMijloaceFixe(MijlocFix mijlocFix) {
        if (mijlocFix == null) {
            return;
        }
        if (mijlocFix.getDataAdaugare() != null) {
            tietDataAdaugare.setText(DateConverter.toString(mijlocFix.getDataAdaugare()));
        }
        if (mijlocFix.getValoare() != 0.0f) {
            tietValoare.setText(String.valueOf(mijlocFix.getValoare()));
        }
        tietDenumire.setText(mijlocFix.getDenumire());
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

    private void getSharedPref() {
        String denumire = tietDenumire.getText() != null ? tietDenumire.getText().toString() : "";
        String valoare = tietValoare.getText().toString();
        String dataAdaugare = tietDataAdaugare.getText().toString();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(DENUMIRE, denumire);
        editor.putString(VALOARE, valoare);
        editor.putString(DATA_ADAUGARE, dataAdaugare);
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSharedPref();
    }

    private void initComponents(){
        tietDataAdaugare = findViewById(R.id.tiet_data_obiectInventar) ;
        tietDenumire = findViewById(R.id.tiet_denumire_obiectInventar) ;
        tietValoare = findViewById((R.id.tiet_valoare_obiectInventar)) ;
        btnSave= findViewById(R.id.btn_saveobiectInventar) ;
        preferences= getSharedPreferences(MIJ_FIXE_SHARED_PREF, MODE_PRIVATE) ;
        loadFromSharedPreference() ;
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validate()){
                    createMijlocFixFromView() ;
                    intent.putExtra(NEW_MijlocFix_KEY,  mijlocFix) ;
                    setResult(RESULT_OK,intent);

                    finish();
                    tietDenumire.setText("");
                    tietDataAdaugare.setText("");
                    tietValoare.setText("");
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
        if (tietValoare.getText() == null || Float.parseFloat(tietValoare.getText().toString().trim()) < 2500 || tietValoare.getText().toString().trim().length() < 0 ) {
            Toast.makeText(getApplicationContext(),
                    R.string.invalid_value_mij_fix,
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
    private void createMijlocFixFromView() {
        Date date = dateConverter.fromString(tietDataAdaugare.getText().toString());
        Float valoare = Float.parseFloat(tietValoare.getText().toString());
        String denumire = tietDenumire.getText().toString();
        if(mijlocFix==null){
        mijlocFix = new MijlocFix(denumire,valoare,date);
        }
        else {
            mijlocFix.setDataAdaugare(date);
            mijlocFix.setValoare(valoare);
            mijlocFix.setDenumire(denumire);
        }
    }
    private void loadFromSharedPreference(){
        String denumire = preferences.getString(DENUMIRE,"") ;
        String valoare = preferences.getString(VALOARE,"") ;
        String dataAdaugare = preferences.getString(DATA_ADAUGARE,"") ;
        tietDenumire.setText(denumire);
        tietValoare.setText(valoare);
        tietDataAdaugare.setText(dataAdaugare);

    }



}