package mx.saudade.popularmoviesapp.interfaces;

import mx.saudade.popularmoviesapp.callbacks.MoviesCallback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by angelicamendezvega on 8/4/15.
 */
public interface MoviesInterface {

    @GET("/3/discover/movie")
    public void getMovies(@Query("sort_by") String sortBy, @Query("api_key") String apiKey
            , @Query("page") String page, MoviesCallback response);

}
