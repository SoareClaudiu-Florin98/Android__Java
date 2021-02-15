package com.example.proiect.database.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "ObiecteInventar")
public class ObiectInventar implements Serializable {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private long id ;
    @ColumnInfo(name = "denumire")
    private String denumire ;
    @ColumnInfo(name = "valoare")
    private float valoare ;
    @ColumnInfo(name = "data_adaugare")
    private Date dataAdaugare ;
    @ColumnInfo(name = "cantitate")
    private int cantitate ;
    @ColumnInfo(name = "categorie")
    private String categorie ;
    @ForeignKey(entity = Companie.class, parentColumns = "idCompanie",childColumns = "idCompanie")
    private long idCompanie ;
    @Ignore
    public ObiectInventar() {
    }

    public long getIdCompanie() {
        return idCompanie;
    }


    public void setIdCompanie(long idCompanie) {
        this.idCompanie = idCompanie;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public float getValoare() {
        return valoare;
    }

    public void setValoare(float valoare) {
        this.valoare = valoare;
    }

    public Date getDataAdaugare() {
        return dataAdaugare;
    }

    public void setDataAdaugare(Date dataAdaugare) {
        this.dataAdaugare = dataAdaugare;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public ObiectInventar(long id, String denumire, float valoare, Date dataAdaugare, int cantitate, String categorie, long idCompanie) {
        this.id = id;
        this.denumire = denumire;
        this.valoare = valoare;
        this.dataAdaugare = dataAdaugare;
        this.cantitate = cantitate;
        this.categorie = categorie;
        this.idCompanie = idCompanie;
    }
    @Ignore
    public ObiectInventar(String denumire, float valoare, Date dataAdaugare, int cantitate, String categorie) {
        this.denumire = denumire;
        this.valoare = valoare;
        this.dataAdaugare = dataAdaugare;
        this.cantitate = cantitate;
        this.categorie = categorie;
    }
}
