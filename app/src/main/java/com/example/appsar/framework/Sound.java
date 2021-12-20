package com.example.appsar.framework;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import com.example.appsar.R;

public class Sound {


    public int soundCollectingItem;
    public int soundWinning;
    public int soundGettingHit;
    public int soundChangingLevel;
    public int soundJumping;
    public int soundDying;
    public int soundBackgroundMusic;
    private Context context;
    private SoundPool soundPool;


    public Sound(Context context) {
        this.context=context;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC,0);

        getSounds();

    }

    private void getSounds(){
        soundCollectingItem = soundPool.load(context, R.raw.collectingitem, 1);
        soundWinning = soundPool.load(context, R.raw.winning, 1);
        soundGettingHit = soundPool.load(context, R.raw.gettinghit, 1);
        soundJumping = soundPool.load(context, R.raw.jumping, 1);
        soundDying = soundPool.load(context, R.raw.dying, 1);
        soundChangingLevel = soundPool.load(context, R.raw.changinglevel, 1);


    }

    public int playSound(int sound, int loop){
        return soundPool.play(sound,1,1,0,loop,1);
    }


    public void stopSound(int sound){
        soundPool.pause(sound);
    }
}
