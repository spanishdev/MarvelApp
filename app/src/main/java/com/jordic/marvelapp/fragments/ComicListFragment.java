package com.jordic.marvelapp.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jordic.marvelapp.MainActivity;
import com.jordic.marvelapp.R;
import com.jordic.marvelapp.Utils.ComicClickListener;
import com.jordic.marvelapp.adapters.ComicListAdapter;
import com.jordic.marvelapp.classes.Comic;
import com.jordic.marvelapp.mvp.ComicListPresenter;
import com.jordic.marvelapp.mvp.ComicListPresenterImpl;
import com.jordic.marvelapp.mvp.ComicListView;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.data;
import static android.R.attr.fragment;
import static com.jordic.marvelapp.R.id.progressBar;
import static com.jordic.marvelapp.R.id.progressBarLoadMore;
import static com.jordic.marvelapp.R.id.titleTextView;

/**
 * Created by Jordi on 04/02/2017.
 */

public class ComicListFragment extends Fragment implements ComicListView,ComicClickListener {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.progressBarLoadMore)
    ProgressBar progressBarLoadMore;

    @BindView(R.id.messageTextView)
    TextView messageTextView;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private final int GRID_COLUMNS = 2;
    private final int LIMIT = 20;

    ComicListPresenter presenter;

    //When we get comics from the webservice, loading is true
    boolean isLoading=false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.comiclist_fragment, container,false);
        ButterKnife.bind(this,view);

        //initialize presenter
        presenter = new ComicListPresenterImpl(this);

        //We set the grid layout manager and the adapter
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(),GRID_COLUMNS,
                LinearLayoutManager.VERTICAL,false);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(new ComicListAdapter(getContext(),this));

        //We add a scroll listener, so when the user scrolls to bottom, then the application will download the next comics
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(!isLoading)
                {
                    GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                    int itemCount= recyclerView.getAdapter().getItemCount();

                    if(gridLayoutManager.findLastCompletelyVisibleItemPosition()==itemCount-1)
                    {
                        isLoading=true;
                        presenter.loadComics(itemCount,LIMIT);
                    }
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        //We begin loading the first comics (in that case, 20)
        presenter.loadComics(0,LIMIT);

        return view;
    }

    @Override
    public void showloading(int offset) {

        isLoading=true;
        if(offset==0) progressBar.setVisibility(View.VISIBLE);
        else progressBarLoadMore.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        isLoading=false;
        progressBar.setVisibility(View.GONE);
        progressBarLoadMore.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showMessage(int resource) {
        messageTextView.setText(resource);
    }

    @Override
    public void showMessage(String message) {
        messageTextView.setText(message);
    }

    @Override
    public String getResourceString(int id) {
        return getString(id);
    }

    @Override
    public void addComic(Comic comic) {
        if(recyclerView!=null)
        {
            ((ComicListAdapter)recyclerView.getAdapter()).addComic(comic);
        }
    }

    @Override
    public void notifyDataSetChanged() {
        if(recyclerView!=null)
        {
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }


    @Override
    public void onComicClickListener(Comic comic) {
        setExitTransition(TransitionInflater.from(getActivity()).inflateTransition(android.R.transition.explode));

        ((MainActivity)getActivity()).loadComicDetailsFragment(comic);
    }
}
