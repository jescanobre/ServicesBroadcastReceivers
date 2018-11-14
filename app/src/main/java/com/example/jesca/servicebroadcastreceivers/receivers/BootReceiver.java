package com.example.jesca.servicebroadcastreceivers.receivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import com.example.jesca.servicebroadcastreceivers.services.MonitorChangeBindedService;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            Log.w("BootReceiver", "here!");
            Toast.makeText(context, "Booting Completed", Toast.LENGTH_LONG).show();

            Intent i = new Intent(context, MonitorChangeBindedService.class);
            i.putExtra(MonitorChangeBindedService.EXTRA_URL, "http://www.quehorassao.com.br/");
            i.putExtra(MonitorChangeBindedService.EXTRA_ID_ELEMENT, "cas1");
            context.startService(i);
        }

    }

}
