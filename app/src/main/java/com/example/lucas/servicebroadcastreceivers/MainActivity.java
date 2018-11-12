package com.example.lucas.servicebroadcastreceivers;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.lucas.servicebroadcastreceivers.services.DownloadIntentService;
import com.example.lucas.servicebroadcastreceivers.services.DownloadService;

import java.sql.DriverManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermissionWriteExternalStorage();
    }

    public void onClickDownload(View view) {
        EditText editText = findViewById(R.id.edtUrl);
        String url = editText.getText().toString();
        Log.w("app", url);
        String fileName = "teste01";

        Intent intent = new Intent(MainActivity.this, DownloadService.class);
        intent.putExtra("url", url);
        intent.putExtra("fileName", fileName);
        startService(intent);
    }

    public void onClickDownloadIS(View view) {
        EditText editText = findViewById(R.id.edtUrl);
        String url = editText.getText().toString();
        Log.w("app", url);
        String fileName = "teste01";

        Intent intent = new Intent(MainActivity.this, DownloadIntentService.class);
        intent.putExtra("url", url);
        intent.putExtra("fileName", fileName);
        startService(intent);
    }

    public void requestPermissionWriteExternalStorage() {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        0);
            }
        }
    }
}
