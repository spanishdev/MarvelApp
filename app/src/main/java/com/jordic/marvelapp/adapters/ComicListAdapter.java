package com.jordic.marvelapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jordic.marvelapp.R;
import com.jordic.marvelapp.Utils.ComicClickListener;
import com.jordic.marvelapp.classes.Comic;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by J on 05/02/2017.
 */

public class ComicListAdapter extends RecyclerView.Adapter<ComicListAdapter.ViewHolder> {

    private Context context;
    private List<Comic> comics;
    private ComicClickListener comicClickListener;

    public ComicListAdapter(Context context,ComicClickListener comicClickListener)
    {
        this.context=context;
        this.comicClickListener=comicClickListener;
        comics=new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comiclist_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String thumbnail_url = comics.get(position).getThumbnail_url();
        String title = comics.get(position).getTitle();
        holder.setViewValues(thumbnail_url,title);
    }

    @Override
    public int getItemCount() {
        return comics.size();
    }

    //CUSTOM FUNCTIONS

    /**
     * Adds a new comic to the list
     * @param comic Comic to add
     */
    public void addComic(Comic comic)
    {
        comics.add(comic);
    }

    /**
     * Returns the Comic at index "Index"
     * @param index Index of the comic
     * @return The comic
     */
    public Comic getComic(int index)
    {
        if(index<0 || index>=comics.size())
            throw new IllegalArgumentException("Index out of range");

        return comics.get(index);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.comicThumbnail)
        ImageView comicThumbnail;

        @BindView(R.id.comicTitle)
        TextView comicTitle;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);

            itemView.setOnClickListener(this);
        }

        public void setViewValues(String thumbnail_url, String title)
        {
            if(context!=null)Picasso.with(context).load(thumbnail_url).into(comicThumbnail);
            comicTitle.setText(title);
        }

        @Override
        public void onClick(View view) {
            comicClickListener.onComicClickListener(getComic(getAdapterPosition()));
        }
    }
}
