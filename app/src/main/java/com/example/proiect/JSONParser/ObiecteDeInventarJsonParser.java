package com.example.proiect.JSONParser;

import com.example.proiect.database.model.ObiectInventar;
import com.example.proiect.util.DateConverter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ObiecteDeInventarJsonParser {

    public static  List<ObiectInventar> fromJSONObiectDeInventar(String json){

        List<ObiectInventar> results = new ArrayList<>() ;
        if(json== null|| json.isEmpty()){
            return new ArrayList<>() ;
        }
        try{
            JSONArray array = new JSONArray(json) ;
            for(int i = 0 ;i<array.length() ; i++){
                JSONObject object = array.getJSONObject(i) ;
                String denumire = object.getString("denumire") ;
                String dataAdaugare = object.getString("data_adaugare") ;
                float valoare = (float) object.getDouble("valoare");
                int cantitate = object.getInt("cantitate") ;
                JSONObject caracteristici = object.getJSONObject("caracteristici") ;
                JSONObject categorie = caracteristici.getJSONObject("categorie") ;
                String numeCategorie = categorie.getString("nume_categorie") ;
                ObiectInventar obiectInventar = new ObiectInventar(denumire,valoare,new DateConverter().fromString(dataAdaugare),cantitate,numeCategorie) ;
                results.add(obiectInventar) ;
            }
        }
        catch (Exception e ){
            e.printStackTrace();
        }

        return results ;
    }
}
