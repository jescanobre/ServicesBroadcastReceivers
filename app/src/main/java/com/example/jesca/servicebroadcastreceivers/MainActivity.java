package com.example.jesca.servicebroadcastreceivers;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jesca.servicebroadcastreceivers.services.DownloadIntentService;
import com.example.jesca.servicebroadcastreceivers.services.DownloadService;
import com.example.jesca.servicebroadcastreceivers.services.MonitorChangeBindedService;
import com.example.jesca.servicebroadcastreceivers.services.MonitorChangeBindedService.LocalBinder;
import com.example.jesca.servicebroadcastreceivers.services.MonitorChangeService;
import com.example.jesca.servicebroadcastreceivers.services.PlayMusicService;

public class MainActivity extends AppCompatActivity {

    static final int PICK_FILE_REQUEST = 1;

    MonitorChangeBindedService bindedService;
    boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermissionWriteExternalStorage();

        Intent intent = new Intent(MainActivity.this, MonitorChangeService.class);
        intent.putExtra(MonitorChangeService.EXTRA_URL, "http://www.quehorassao.com.br/");
        intent.putExtra(MonitorChangeService.EXTRA_ID_ELEMENT, "cas1");
        startService(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if( requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK ) {
            Uri music = data.getData();

            //utiliza cursor para pegar o nome da música
            String[] projection = {
                    MediaStore.Audio.Media.DISPLAY_NAME
            };

            Cursor cursor = getContentResolver().query(music, projection, null, null, null);
            if( cursor != null ) {
                cursor.moveToFirst();

                int column = cursor.getColumnIndex(DocumentsContract.Document.COLUMN_DISPLAY_NAME);
                String name = cursor.getString(column);

                cursor.close();

                TextView textView = findViewById(R.id.txt_music);
                if (name != null)
                    textView.setText(name);
            }

            //executa a música utilizando o service
            Intent intent = new Intent(MainActivity.this, PlayMusicService.class);
            intent.putExtra(PlayMusicService.EXTRA_URI, music);
            startService(intent);
        }
    }

    public void onClickDownload(View view) {
        EditText editText = findViewById(R.id.edtUrl);
        String url = editText.getText().toString();

        String fileName = "teste01";

        //a intent usa a mainactivity como contexto e executa o downloadservice
        Intent intent = new Intent(MainActivity.this, DownloadService.class);
        intent.putExtra("url", url);
        intent.putExtra("fileName", fileName);
        startService(intent);
    }

    public void onClickDownloadIS(View view) {
        EditText editText = findViewById(R.id.edtUrl);
        String url = editText.getText().toString();
        String fileName = "teste01";

        Intent intent = new Intent(MainActivity.this, DownloadIntentService.class);
        intent.putExtra("url", url);
        intent.putExtra("fileName", fileName);
        startService(intent);
    }

    public void onClickPlayMusic(View view) {
        //abre uma tela para escolher um arquivo de audio
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, Uri.parse("content://musics"));
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType(MediaStore.Audio.Media.CONTENT_TYPE);
        startActivityForResult(Intent.createChooser(intent, "Pick a music to play..."), PICK_FILE_REQUEST);
    }

    public void onClickBindedService(View view) {
        if (mBound) {
            String hour = bindedService.getHour();
            if( hour != null)
                Toast.makeText(this, "hour: " + hour, Toast.LENGTH_SHORT).show();
        }
    }

    public void requestPermissionWriteExternalStorage() {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        0);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = new Intent(MainActivity.this, MonitorChangeBindedService.class);
        intent.putExtra(MonitorChangeBindedService.EXTRA_URL, "http://www.quehorassao.com.br/");
        intent.putExtra(MonitorChangeBindedService.EXTRA_ID_ELEMENT, "cas1");
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

        Toast.makeText(this, "Binded Sevice started!", Toast.LENGTH_SHORT).show();
//        startService(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LocalBinder binder = (LocalBinder) service;
            bindedService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };
}
