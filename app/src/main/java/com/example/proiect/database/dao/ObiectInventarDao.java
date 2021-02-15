package com.example.proiect.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.proiect.database.model.ObiectInventar;

import java.util.List;

@Dao
public interface ObiectInventarDao {
    @Query("select * from obiecteInventar")
    List<ObiectInventar> getAll();
    @Query("SELECT * FROM obiecteinventar WHERE idCompanie=:idCompanie")
    List<ObiectInventar> findObiecteInventarForCompanie(final long idCompanie);
    @Insert
    long insert(ObiectInventar obiectInventar);
    @Update
    int update(ObiectInventar obiectInventar);
    @Delete
    int delete(ObiectInventar obiectInventar);
}
