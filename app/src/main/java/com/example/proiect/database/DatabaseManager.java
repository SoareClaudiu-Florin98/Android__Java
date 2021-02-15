package com.example.proiect.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.proiect.database.dao.CompanieDao;
import com.example.proiect.database.dao.MijlocFixDao;
import com.example.proiect.database.dao.ObiectInventarDao;
import com.example.proiect.database.model.Companie;
import com.example.proiect.database.model.MijlocFix;
import com.example.proiect.database.model.ObiectInventar;
import com.example.proiect.util.DateConverter;

@Database(entities = {Companie.class, MijlocFix.class, ObiectInventar.class}, exportSchema = false, version = 1)
@TypeConverters({DateConverter.class})
public abstract class DatabaseManager extends RoomDatabase {
    private static final String DAM_DB = "dam_db";

    private static DatabaseManager databaseManager;

    public static DatabaseManager getInstance(Context context) {
        if (databaseManager == null) {
            synchronized (DatabaseManager.class) {
                if (databaseManager == null) {
                    databaseManager = Room.databaseBuilder(context, DatabaseManager.class, DAM_DB)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return databaseManager;
    }
    public abstract MijlocFixDao getMijlocFixDao();
    public abstract ObiectInventarDao getObiectInventarDao();
    public abstract CompanieDao getCompanieDao();


}
