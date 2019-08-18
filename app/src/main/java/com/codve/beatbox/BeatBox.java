package com.codve.beatbox;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BeatBox {

    private static final String TAG = "BeatBox";

    private static final String SOUNDS_FOLDER = "sample_sounds";

    private AssetManager mAssets; // 资源管理器
    private List<Sound> mSounds = new ArrayList<>();

    public BeatBox(Context context) {
        mAssets = context.getAssets();
        loadSounds();
    }

    private void loadSounds() {
        String[] soundNames;
        try {
            // 获取指定目录下的所有文件名
            soundNames = mAssets.list(SOUNDS_FOLDER);
            for (String filename : soundNames) {
                String assetPath = SOUNDS_FOLDER + "/" + filename;
                Sound sound = new Sound(assetPath);
                mSounds.add(sound);
            }
        } catch (IOException e) {
            Log.e(TAG, "Cound not list assets", e);
        }
    }

    public List<Sound> getSounds() {
        return mSounds;
    }
}
