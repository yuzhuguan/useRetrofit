package com.carefree.findmovie.repo.remote;

import com.carefree.findmovie.repo.model.Movie;
import com.carefree.findmovie.repo.model.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by CoderF1 on 2017/12/18.
 */

public interface ApiInterface {

    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRateMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<Movie> getMovieDetails(@Part("id") int id, @Query("api_key") String apiKey);
}
