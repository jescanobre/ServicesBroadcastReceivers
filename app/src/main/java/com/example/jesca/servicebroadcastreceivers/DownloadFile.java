package com.example.jesca.servicebroadcastreceivers;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

//Thread faz download e passa uma url, o nome do arquivo e o contexto,
// o contexto serve p/ pegar o serviço de download

public class DownloadFile extends Thread {
    private String url;
    private String fileName;
    private Context context;
    public DownloadFile(String url, String fileName, Context context) {
        this.url = url;
        this.fileName = fileName;
        this.context = context;
    }

    @Override
    public void run() {
        if( this.url != null && !this.url.isEmpty() ) {
            Uri uri = Uri.parse(this.url);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, this.fileName);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            DownloadManager manager = (DownloadManager) this.context.getSystemService(Context.DOWNLOAD_SERVICE);
            manager.enqueue(request);
        }
    }
}
