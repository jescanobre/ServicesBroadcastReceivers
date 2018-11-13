package com.example.lucas.servicebroadcastreceivers.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.net.Uri;

import com.example.lucas.servicebroadcastreceivers.PlayMusic;

public class PlayMusicService extends IntentService {

    public final static String EXTRA_URI = "uri";

    public PlayMusicService() {
        super("PlayMusicService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            Uri music = intent.getParcelableExtra(EXTRA_URI);
            PlayMusic playMusic = new PlayMusic(getApplicationContext());
            playMusic.play(music);
        }
    }

}
