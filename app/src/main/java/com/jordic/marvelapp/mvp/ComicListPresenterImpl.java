package com.jordic.marvelapp.mvp;

import android.util.Log;

import com.jordic.marvelapp.R;
import com.jordic.marvelapp.Utils.NetworkUtils;
import com.jordic.marvelapp.classes.Comic;
import com.jordic.marvelapp.fragments.ComicListFragment;
import com.jordic.marvelapp.retrofit.ComicService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Jordi on 04/02/2017.
 */

public class ComicListPresenterImpl implements ComicListPresenter{

    //The ID in that case is Captain America
    final int HERO_ID = 1009220;

    //Referring to the view, we use this to update the screen
    ComicListView comicListView;

    public ComicListPresenterImpl(ComicListView comicListView)
    {
        this.comicListView=comicListView;
    }

    /**
     * Calls the Webservice using and offset and a limit
     * @param offset Index from where we want to get the comics
     * @param limit Number of comics to get
     */
    @Override
    public void loadComics(int offset, int limit) {

        //First, we show a Progress Bar
        comicListView.showloading(offset);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkUtils.BASE_URL)
                .build();

        ComicService comicService = retrofit.create(ComicService.class);

        String apiKey = comicListView.getResourceString(R.string.publickey);
        String privateKey = comicListView.getResourceString(R.string.privatekey);
        long ts =System.currentTimeMillis();

        String hash = NetworkUtils.getHash(ts,privateKey,apiKey);

        Call<ResponseBody> call = comicService.getComics(HERO_ID,offset,limit,ts,apiKey,hash);

        Log.d("REQUEST",call.request().url().toString());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                comicListView.hideLoading();
                try
                {
                    if(response.isSuccessful())
                    {
                        String responseString = response.body().string();
                        Log.d("RESPONSE",responseString);

                        try {
                            JSONObject responseJSON = new JSONObject(responseString);
                            JSONObject dataJSON = responseJSON.getJSONObject("data");

                            JSONArray comicsJSON = dataJSON.getJSONArray("results");

                            for(int i=0; i<comicsJSON.length();i++)
                            {
                                JSONObject json = comicsJSON.getJSONObject(i);
                                String title = json.getString("title");
                                String description = json.getString("description");

                                JSONObject thumbnailJSON =  json.getJSONObject("thumbnail");
                                String thumbnail_url = thumbnailJSON.getString("path")+"."+thumbnailJSON.getString("extension");

                                JSONArray imagesJSONArray =  json.getJSONArray("images");

                                Random random = new Random();
                                int randomIndex = random.nextInt(imagesJSONArray.length());

                                JSONObject imageJSON =  imagesJSONArray.getJSONObject(randomIndex);
                                String image_url = imageJSON.getString("path")+"."+imageJSON.getString("extension");

                                Comic comic = new Comic(title,thumbnail_url,description,image_url);

                                comicListView.addComic(comic);
                            }
                            comicListView.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    else
                    {
                        Log.d("RESPONSE UNSUCCSESSFUL",response.errorBody().string());
                        comicListView.showMessage("There server petition was unsuccessful. Code: "+response.code());
                    }

                }
                catch (IOException e) {
                    e.printStackTrace();
                    Log.d("ERROR","CONNECTION PROBLEM");
                    comicListView.showMessage("There was a connection problem");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                comicListView.hideLoading();
                comicListView.showMessage("There was a connection problem");
            }
        });


    }

}
