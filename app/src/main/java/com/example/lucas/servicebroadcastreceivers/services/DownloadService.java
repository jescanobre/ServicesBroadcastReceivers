package com.example.lucas.servicebroadcastreceivers.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.lucas.servicebroadcastreceivers.DownloadFile;

import java.util.ArrayList;

public class DownloadService extends Service {
    private String url;
    private String fileName;
    public DownloadService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.url = intent.getStringExtra("url");
        this.fileName = intent.getStringExtra("fileName");

        DownloadFile downloadFile = new DownloadFile(this.url, this.fileName, getApplicationContext());
        downloadFile.start();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
