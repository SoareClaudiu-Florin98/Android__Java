package com.example.proiect.database.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "MijloaceFixe")
public class MijlocFix implements Serializable {

    @ColumnInfo(name = "id")
    @PrimaryKey (autoGenerate = true)
    private long id ;
    @ForeignKey(entity = Companie.class, parentColumns = "idCompanie",childColumns = "idCompanie")
    private long idCompanie ;
    public void setIdCompanie(long idCompanie) {
        this.idCompanie = idCompanie;
    }
    private String denumire ;
    private float valoare ;
    private Date dataAdaugare ;
    private String idFirebase ;

    public String getIdFirebase() {
        return idFirebase;
    }
    public void setIdFirebase(String idFirebase) {
        this.idFirebase = idFirebase;
    }
    public long getIdCompanie() {
        return idCompanie;
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
    public MijlocFix(long id, String denumire, float valoare, Date dataAdaugare) {
        this.id = id;
        this.denumire = denumire;
        this.valoare = valoare;
        this.dataAdaugare = dataAdaugare;
    }
    @Ignore
    public MijlocFix() {
    }
    @Ignore
    public MijlocFix(long id, long idCompanie, String denumire, float valoare, Date dataAdaugare, String idFirebase) {
        this.id = id;
        this.idCompanie = idCompanie;
        this.denumire = denumire;
        this.valoare = valoare;
        this.dataAdaugare = dataAdaugare;
        this.idFirebase = idFirebase;
    }
    @Ignore
    public MijlocFix(String denumire, float valoare, Date dataAdaugare) {
        this.denumire = denumire;
        this.valoare = valoare;
        this.dataAdaugare = dataAdaugare;
    }
}
