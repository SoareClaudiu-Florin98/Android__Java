package com.example.proiect.asyncTask;


import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AsyncTaskRunner {
    private final Handler my_handler = new Handler(Looper.getMainLooper());
    private final Executor my_executor = Executors.newCachedThreadPool();
    public <R> void executeAsync(Callable<R> asyncOperation, Callback<R> mainThreadOp) {
        try {
            my_executor.execute(new RunnableTask<>(my_handler, asyncOperation, mainThreadOp));
        } catch (Exception ex) {
            Log.i("AsyncTaskRunner", "failed call executeAsync " + ex.getMessage());
        }
    }
}
