package com.jordic.marvelapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jordic.marvelapp.R;
import com.jordic.marvelapp.classes.Comic;
import com.jordic.marvelapp.mvp.ComicListPresenter;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.jordic.marvelapp.R.id.comicThumbnail;
import static com.jordic.marvelapp.R.id.title;
import static com.jordic.marvelapp.R.id.titleTextView;

/**
 * Created by Jordi on 04/02/2017.
 */

public class ComicDetailsFragment extends Fragment
{
    @BindView(R.id.imageView)
    ImageView imageView;

    @BindView(R.id.titleTextView)
    TextView titleTextView;

    @BindView(R.id.descriptionTextView)
    TextView descriptionTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.comicdetails_fragment, container,false);
        ButterKnife.bind(this,view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Comic comic = getArguments().getParcelable("comic");

        if(getContext()!=null)Picasso.with(getContext()).load(comic.getImage_url()).into(imageView);
        titleTextView.setText(comic.getTitle());
        descriptionTextView.setText(comic.getDescription());
    }

}
