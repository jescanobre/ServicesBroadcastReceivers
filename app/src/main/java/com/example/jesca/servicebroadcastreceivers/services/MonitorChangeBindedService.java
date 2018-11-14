package com.example.jesca.servicebroadcastreceivers.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class MonitorChangeBindedService extends Service {

    private final IBinder mBinder = new LocalBinder();

    private String hour;
    private String url;
    private String id;

    public final static String EXTRA_URL = "url";
    public final static String EXTRA_ID_ELEMENT = "id_element";

    public class LocalBinder extends Binder {
        public MonitorChangeBindedService getService() {
            return MonitorChangeBindedService.this;
        }
    }

    public MonitorChangeBindedService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null) {
            this.id  = intent.getStringExtra(EXTRA_ID_ELEMENT);
            this.url = intent.getStringExtra(EXTRA_URL);

            new Thread() {
                @Override
                public void run() {

                    Connection connection = Jsoup.connect(url);
                    while (true) {
                        try {
                            Document document = connection.post();
                            Element element = document.getElementById(id);

                            hour = element.text();

                            sleep(1000);

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return this.mBinder;
    }

    public String getHour() {
        return this.hour;
    }
}
