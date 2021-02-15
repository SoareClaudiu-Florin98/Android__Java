package com.example.proiect.database.service;

import android.content.Context;

import com.example.proiect.asyncTask.AsyncTaskRunner;
import com.example.proiect.asyncTask.Callback;
import com.example.proiect.database.DatabaseManager;
import com.example.proiect.database.dao.CompanieDao;
import com.example.proiect.database.model.Companie;

import java.util.List;
import java.util.concurrent.Callable;

public class CompanieService {
    private final CompanieDao companieDao;
    private final AsyncTaskRunner taskRunner;

    public CompanieService(Context context) {
        companieDao = DatabaseManager.getInstance(context).getCompanieDao();
        taskRunner = new AsyncTaskRunner();
    }

    public List<Companie> getAllV2() {
        return companieDao.getAll();
    }

    public void getAll(Callback<List<Companie>> callback) {
        Callable<List<Companie>> callable = new Callable<List<Companie>>() {
            @Override
            public List<Companie> call() {
                return companieDao.getAll();
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

    public void insert(Callback<Companie> callback, final Companie companie) {
        Callable<Companie> callable = new Callable<Companie>() {
            @Override
            public Companie call() {
                if (companie == null) {
                    return null;
                }
                long id = companieDao.insert(companie);
                if (id == -1) {
                    return null;
                }
                companie.setIdCompanie(id);
                return companie;
            }
        };
        taskRunner.executeAsync(callable, callback);
    }
    public void update(Callback<Companie> callback, final Companie companie) {
        Callable<Companie> callable = new Callable<Companie>() {
            @Override
            public Companie call() {
                if (companie == null) {
                    return null;
                }
                int count = companieDao.update(companie);
                if (count < 1) {
                    return null;
                }
                return companie;
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

    public void delete(Callback<Integer> callback, final Companie companie) {
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() {
                if (companie == null) {
                    return -1;
                }
                return companieDao.delete(companie);
            }
        };
        taskRunner.executeAsync(callable, callback);
    }
}
