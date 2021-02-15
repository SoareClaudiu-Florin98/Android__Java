package com.example.proiect.asyncTask;

public interface Callback<R> {

    void runResultOnUiThread(R result);
}
