package com.codve.beatbox;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BeatBox {

    private static final String TAG = "BeatBox";

    private static final String SOUNDS_FOLDER = "sample_sounds";
    private static final int MAX_SOUNDS = 5;

    private AssetManager mAssets; // 资源管理器
    private List<Sound> mSounds = new ArrayList<>();
    private SoundPool mSoundPool;

    public BeatBox(Context context) {
        mAssets = context.getAssets();
        mSoundPool = new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0);
        loadSounds();
    }

    private void loadSounds() {
        String[] soundNames;
        try {
            // 获取指定目录下的所有文件名
            soundNames = mAssets.list(SOUNDS_FOLDER);
            for (String filename : soundNames) {
                try {
                    String assetPath = SOUNDS_FOLDER + "/" + filename;
                    Sound sound = new Sound(assetPath);
                    load(sound);
                    mSounds.add(sound);
                } catch (IOException e) {
                    Log.e(TAG, "Could not load sound " + filename, e);
                }

            }
        } catch (IOException e) {
            Log.e(TAG, "Cound not list assets", e);
        }
    }

    // 载入音乐
    private void load(Sound sound) throws IOException {
        AssetFileDescriptor afd = mAssets.openFd(sound.getAssetPath());
        int soundId = mSoundPool.load(afd, 1);
        sound.setSoundId(soundId);
    }

    // 播放音乐
    public void play(Sound sound) {
        Integer soundId = sound.getSoundId();
        if (soundId == null) {
            return;
        }
        mSoundPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public List<Sound> getSounds() {
        return mSounds;
    }

    // 释放 SoundPool
    public void release() {
        mSoundPool.release();
    }
}
