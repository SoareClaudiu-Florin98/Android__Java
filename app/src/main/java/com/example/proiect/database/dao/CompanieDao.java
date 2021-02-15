package com.example.proiect.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.proiect.database.model.Companie;

import java.util.List;

@Dao
public interface CompanieDao {
    @Query("select * from companii")
    List<Companie> getAll();

    @Insert
    long insert(Companie companie);
    @Update
    int update(Companie companie);

    @Delete
    int delete(Companie companie);
}
