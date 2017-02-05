package com.jordic.marvelapp.retrofit;

import com.jordic.marvelapp.classes.Comic;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by J on 05/02/2017.
 */

public interface ComicService {



    @GET("/v1/public/characters/{characterId}/comics")
    Call<ResponseBody> getComics(@Path("characterId") int characterId, @Query("offset") int offset, @Query("limit") int limit,
                                 @Query("ts") long ts, @Query("apikey") String apikey, @Query("hash") String hash);
}
