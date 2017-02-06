package com.jordic.marvelapp.Utils;

import android.widget.TextView;

import com.jordic.marvelapp.classes.Comic;

import java.lang.ref.WeakReference;

/**
 * Created by J on 05/02/2017.
 */

public interface ComicClickListener {
    void onComicClickListener(Comic comic);
}
