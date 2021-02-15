package com.example.proiect.asyncTask;

public class HandlerMessage<R> implements Runnable {
    private final Callback<R> mainThreadOp;
    private final R result;
    public HandlerMessage(Callback<R> mainThreadOperation, R result) {
        this.mainThreadOp = mainThreadOperation;
        this.result = result;
    }
    @Override
    public void run() {
        mainThreadOp.runResultOnUiThread(result);
    }
}
