package com.jordic.marvelapp.mvp;

import com.jordic.marvelapp.classes.Comic;

/**
 * Created by Jordi on 04/02/2017.
 */

public interface ComicListView
{
    void showloading(int offset);

    void hideLoading();

    void showMessage(int resource);

    void showMessage(String message);

    String getResourceString(int id);

    void addComic(Comic comic);

    void notifyDataSetChanged();
}
