package com.example.proiect.asyncTask;

import android.os.Handler;
import android.util.Log;

import java.util.concurrent.Callable;

public class RunnableTask<R> implements Runnable {
    private final Handler my_handler;
    private final Callable<R> asyncOp;
    private final Callback<R> mainThreadOp;

    public RunnableTask(Handler handler, Callable<R> asyncOperation, Callback<R> mainThreadOperation) {
        this.my_handler = handler;
        this.asyncOp = asyncOperation;
        this.mainThreadOp = mainThreadOperation;
    }

    @Override
    public void run() {
        try {
            final R result = asyncOp.call();
            my_handler.post(new HandlerMessage<>(mainThreadOp, result));
        } catch (Exception e) {
            Log.i("RunnableTask", "failed call runnable " + e.getMessage());
        }
    }
}
