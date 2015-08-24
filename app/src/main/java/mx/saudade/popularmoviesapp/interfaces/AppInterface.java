package mx.saudade.popularmoviesapp.interfaces;

import mx.saudade.popularmoviesapp.callbacks.AppCallback;
import mx.saudade.popularmoviesapp.models.Movie;
import mx.saudade.popularmoviesapp.models.Review;
import mx.saudade.popularmoviesapp.models.Video;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by angelicamendezvega on 8/4/15.
 */
public interface AppInterface {

    @GET("/3/discover/movie")
    public void getMovies(@Query("sort_by") String sortBy, @Query("api_key") String apiKey
            , @Query("page") String page, AppCallback<Movie> response);

    @GET("/3/movie/{movieId}/reviews")
    public void getReviews(@Path("movieId") int movieId, @Query("api_key") String apiKey, AppCallback<Review> response);

    @GET("/3/movie/{movieId}/videos")
    public void getVideos(@Path("movieId") int movieId, @Query("api_key") String apiKey, AppCallback<Video> response);

}
