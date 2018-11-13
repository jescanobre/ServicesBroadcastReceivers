package com.example.lucas.servicebroadcastreceivers.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;

import com.example.lucas.servicebroadcastreceivers.MainActivity;
import com.example.lucas.servicebroadcastreceivers.R;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class MonitorChangeService extends IntentService {
    private String id;
    private String url;
    public final static String EXTRA_URL = "url";
    public final static String EXTRA_ID_ELEMENT = "id_element";
    private static int nId = 0;
    public MonitorChangeService() {
        super("MonitorChangeService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        if (intent != null) {
            this.id  = intent.getStringExtra(EXTRA_ID_ELEMENT);
            this.url = intent.getStringExtra(EXTRA_URL);
            Connection connection = Jsoup.connect(url);
            while (true) {
                try {
                    Document document = connection.post();
                    Element element = document.getElementById(this.id);

                    NotificationCompat.Builder notification = new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.ic_sync_black_24dp)
                            .setContentTitle("Mudanças")
                            .setContentText(element.text())
                            .setAutoCancel(true);

                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                    notificationManager.notify(nId++, notification.build());

                    Thread.sleep(60000);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
