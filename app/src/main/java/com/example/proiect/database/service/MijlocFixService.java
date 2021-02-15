package com.example.proiect.database.service;

import android.content.Context;

import com.example.proiect.asyncTask.AsyncTaskRunner;
import com.example.proiect.asyncTask.Callback;
import com.example.proiect.database.DatabaseManager;
import com.example.proiect.database.dao.MijlocFixDao;
import com.example.proiect.database.model.MijlocFix;

import java.util.List;
import java.util.concurrent.Callable;

public class MijlocFixService {
    private final MijlocFixDao mijlocFixDao;
    private final AsyncTaskRunner taskRunner;

    public MijlocFixService(Context context) {
        mijlocFixDao = DatabaseManager.getInstance(context).getMijlocFixDao();
        taskRunner = new AsyncTaskRunner();
    }

    public List<MijlocFix> getAllV2() {
        return mijlocFixDao.getAll();
    }

    public void getAll(Callback<List<MijlocFix>> callback) {
        Callable<List<MijlocFix>> callable = new Callable<List<MijlocFix>>() {
            @Override
            public List<MijlocFix> call() {
                return mijlocFixDao.getAll();
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

    public void insert(Callback<MijlocFix> callback, final MijlocFix mijlocFix) {
        Callable<MijlocFix> callable = new Callable<MijlocFix>() {
            @Override
            public MijlocFix call() {
                if (mijlocFix == null) {
                    return null;
                }
                long id = mijlocFixDao.insert(mijlocFix);
                if (id == -1) {
                    return null;
                }
                mijlocFix.setId(id);
                return mijlocFix;
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

    public void update(Callback<MijlocFix> callback, final MijlocFix mijlocFix) {
        Callable<MijlocFix> callable = new Callable<MijlocFix>() {
            @Override
            public MijlocFix call() {
                if (mijlocFix == null) {
                    return null;
                }
                int count = mijlocFixDao.update(mijlocFix);
                if (count < 1) {
                    return null;
                }
                return mijlocFix;
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

    public void delete(Callback<Integer> callback, final MijlocFix mijlocFix) {
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() {
                if (mijlocFix == null) {
                    return -1;
                }
                return mijlocFixDao.delete(mijlocFix);
            }
        };
        taskRunner.executeAsync(callable, callback);
    }
}
