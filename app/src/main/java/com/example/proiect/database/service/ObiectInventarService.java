package com.example.proiect.database.service;

import android.content.Context;

import com.example.proiect.asyncTask.AsyncTaskRunner;
import com.example.proiect.asyncTask.Callback;
import com.example.proiect.database.DatabaseManager;
import com.example.proiect.database.dao.ObiectInventarDao;
import com.example.proiect.database.model.ObiectInventar;

import java.util.List;
import java.util.concurrent.Callable;

public class ObiectInventarService {
    private final ObiectInventarDao obiectInventarDao;
    private final AsyncTaskRunner taskRunner;

    public ObiectInventarService(Context context) {
        obiectInventarDao = DatabaseManager.getInstance(context).getObiectInventarDao();
        taskRunner = new AsyncTaskRunner();
    }

    public List<ObiectInventar> getAllV2() {
        return obiectInventarDao.getAll();
    }

    public void getAll(Callback<List<ObiectInventar>> callback) {
        Callable<List<ObiectInventar>> callable = new Callable<List<ObiectInventar>>() {
            @Override
            public List<ObiectInventar> call() {
                return obiectInventarDao.getAll();
            }
        };
        taskRunner.executeAsync(callable, callback);
    }
    public void insert(Callback<ObiectInventar> callback, final ObiectInventar obiectInventar) {
        Callable<ObiectInventar> callable = new Callable<ObiectInventar>() {
            @Override
            public ObiectInventar call() {
                if (obiectInventar == null) {
                    return null;
                }
                long id = obiectInventarDao.insert(obiectInventar);
                if (id == -1) {
                    return null;
                }
                obiectInventar.setId(id);
                return obiectInventar;
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

    public void update(Callback<ObiectInventar> callback, final ObiectInventar obiectInventar) {
        Callable<ObiectInventar> callable = new Callable<ObiectInventar>() {
            @Override
            public ObiectInventar call() {
                if (obiectInventar == null) {
                    return null;
                }
                int count = obiectInventarDao.update(obiectInventar);
                if (count < 1) {
                    return null;
                }
                return obiectInventar;
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

    public void delete(Callback<Integer> callback, final ObiectInventar obiectInventar) {
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() {
                if (obiectInventar == null) {
                    return -1;
                }
                return obiectInventarDao.delete(obiectInventar);
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

}
