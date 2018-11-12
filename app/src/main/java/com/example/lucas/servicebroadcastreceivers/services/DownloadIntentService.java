package com.example.lucas.servicebroadcastreceivers.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import com.example.lucas.servicebroadcastreceivers.DownloadFile;

public class DownloadIntentService extends IntentService {

    private String url;
    private String fileName;

    public DownloadIntentService() {
        super("DownloadIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        this.url = intent.getStringExtra("url");
        this.fileName = intent.getStringExtra("fileName");

        DownloadFile downloadFile = new DownloadFile(this.url, this.fileName, getApplicationContext());
        downloadFile.start();
    }

}
