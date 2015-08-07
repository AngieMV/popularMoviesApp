package mx.saudade.popularmoviesapp.models;

import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by angelicamendezvega on 8/4/15.
 */
public class Results implements Serializable {

    private int page;

    @SerializedName("results")
    private List<Movie> movies;

    public Results() {

    }

    public Results(List<Movie> movies) {
        this.movies = movies;
    }

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
