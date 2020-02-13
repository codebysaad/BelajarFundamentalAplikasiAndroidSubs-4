package xyz.webflutter.moviecatalogue.rest;

import retrofit2.Call;
import retrofit2.http.GET;
import xyz.webflutter.moviecatalogue.BuildConfig;
import xyz.webflutter.moviecatalogue.models.ModelMovie;
import xyz.webflutter.moviecatalogue.models.ModelTvShow;

public interface ApiServices {
    @GET("movie?api_key=" + BuildConfig.API_KEY + "&language=en-US")
    Call<ModelMovie> getDataMovie();

    @GET("tv?api_key=" + BuildConfig.API_KEY + "&language=en-US")
    Call<ModelTvShow> getTvShowData();
}