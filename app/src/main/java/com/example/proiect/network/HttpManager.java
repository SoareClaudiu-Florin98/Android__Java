package com.example.proiect.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;

public class HttpManager implements Callable<String> {
    private URL url;
    private HttpURLConnection conexiune;
    private InputStream is;
    private InputStreamReader isr;
    private BufferedReader br;

    private final String urlAddress;

    public HttpManager(String urlAddress) {
        this.urlAddress = urlAddress;
    }

    @Override
    public String call() {
        try {
            return getResultFromHttp();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return null;
    }

    private String getResultFromHttp() throws IOException {
        url = new URL(urlAddress);
        conexiune = (HttpURLConnection) url.openConnection();
        is = conexiune.getInputStream();
        isr = new InputStreamReader(is);
        br = new BufferedReader(isr);
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }
    private void closeConnections() {
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            isr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        conexiune.disconnect();
    }
}
