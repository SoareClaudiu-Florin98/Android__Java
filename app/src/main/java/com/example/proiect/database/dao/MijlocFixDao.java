package com.example.proiect.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.proiect.database.model.MijlocFix;

import java.util.List;

@Dao
public interface MijlocFixDao {

    @Query("select * from mijloacefixe")
    List<MijlocFix> getAll();
    @Insert
    long insert(MijlocFix mijlocFix);
    @Update
    int update(MijlocFix mijlocFix);
    @Delete
    int delete(MijlocFix mijlocFix);
}
