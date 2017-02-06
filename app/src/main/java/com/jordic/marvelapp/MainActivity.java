package com.jordic.marvelapp;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jordic.marvelapp.classes.Comic;
import com.jordic.marvelapp.fragments.ComicDetailsFragment;
import com.jordic.marvelapp.fragments.ComicListFragment;

import java.lang.ref.WeakReference;

import butterknife.BindView;

import static android.R.attr.fragment;
import static com.jordic.marvelapp.R.id.frameLayout;

public class MainActivity extends AppCompatActivity {

    final String COMIC_LIST_TAG="COMIC_LIST";
    final String COMIC_DETAILS_TAG="COMIC_DETAILS";

    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameLayout= (FrameLayout) findViewById(R.id.frameLayout);

        loadComicListFragment();
    }

    private void loadComicListFragment()
    {
        Fragment fragment = new ComicListFragment();

        if(frameLayout!=null)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.frameLayout, fragment, COMIC_LIST_TAG)
                    .commit();
        }

    }

    public void loadComicDetailsFragment(Comic comic)
    {
        Fragment fragment = new ComicDetailsFragment();

        Bundle args = new Bundle();
        args.putParcelable("comic", comic);
        fragment.setArguments(args);

        fragment.setEnterTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.explode));

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frameLayout, fragment, COMIC_DETAILS_TAG)
                .addToBackStack(null)
                .commit();
    }

}
