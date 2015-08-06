package mx.saudade.popularmoviesapp.models;

import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by angelicamendezvega on 8/4/15.
 */
public class Results {

    private int page;

    @SerializedName("results")
    private List<Movie> movies;

    public List<Movie> getMovies() {
        return movies;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("page", page)
                .append("movies", movies)
                .toString();
    }

}
