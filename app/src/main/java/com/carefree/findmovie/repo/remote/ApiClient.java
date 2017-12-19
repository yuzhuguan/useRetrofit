package com.carefree.findmovie.repo.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by CoderF1 on 2017/12/18.
 */

public class ApiClient {
    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    public static final String IMAGE = "https://image.tmdb.org/t/p/w300";
    private static Retrofit retrofit = null;

    private ApiClient() {
    }

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
