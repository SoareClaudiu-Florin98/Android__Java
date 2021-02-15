package com.example.proiect.database.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Companii")
public class Companie {
    @ColumnInfo(name = "idCompanie")
    @PrimaryKey(autoGenerate = true)
    private long idCompanie;
    @ColumnInfo(name = "nume")
    private String nume ;
    @ColumnInfo(name = "domeniu_de_activitate")
    private String domeniuDeActivitate ;
    public long getIdCompanie() {
        return idCompanie;
    }
    public void setIdCompanie(long idCompanie) {
        this.idCompanie = idCompanie;
    }
    public String getNume() {
        return nume;
    }
    public void setNume(String nume) {
        this.nume = nume;
    }
    public String getDomeniuDeActivitate() {
        return domeniuDeActivitate;
    }
    public void setDomeniuDeActivitate(String domeniuDeActivitate) {
        this.domeniuDeActivitate = domeniuDeActivitate;
    }
    @Ignore
    public Companie(long idCompanie, String nume, String domeniuDeActivitate) {
        this.idCompanie = idCompanie;
        this.nume = nume;
        this.domeniuDeActivitate = domeniuDeActivitate;
    }
    public Companie(String nume, String domeniuDeActivitate) {
        this.nume = nume;
        this.domeniuDeActivitate = domeniuDeActivitate;
    }
}
