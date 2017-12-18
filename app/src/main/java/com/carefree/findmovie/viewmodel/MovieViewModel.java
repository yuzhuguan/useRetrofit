package com.carefree.findmovie.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.carefree.findmovie.repo.model.Movie;
import com.carefree.findmovie.repo.model.MoviesResponse;
import com.carefree.findmovie.repo.remote.ApiClient;
import com.carefree.findmovie.repo.remote.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by CoderF1 on 2017/12/18.
 */

public class MovieViewModel extends AndroidViewModel {
    private static final String API_KEY = "7e951d11feaa9ede51af874020618412";
    private static final String TAG = "MovieViewModel";

    private ApiInterface apiService;
    private MutableLiveData<List<Movie>> mMovies;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    public LiveData<List<Movie>> getMovies() {
        if (mMovies == null) {
            mMovies = new MutableLiveData<>();
            loadMovies();
        }
        return mMovies;
    }

    private void loadMovies() {
        Call<MoviesResponse> call = apiService.getTopRateMovies(API_KEY);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                List<Movie> movies = response.body().getResults();
                Log.d(TAG, "Number of movies received: " + movies.size());
                mMovies.setValue(movies);
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }
}
