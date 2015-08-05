package mx.saudade.popularmoviesapp.models;

import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by angelicamendezvega on 8/4/15.
 */
public class Movie {

    private boolean adult;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("genre_ids")
    private int[] genreIds;

    private int id;

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("original_title")
    private String originalTitle;

    private String overview;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("poster_path")
    private String posterPath;

    private float popularity;

    private String title;

    private boolean video;

    @SerializedName("vote_average")
    private float voteAverage;

    @SerializedName("vote_count")
    private int voteCount;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("adult", adult)
                .append("backdropPath", backdropPath)
                .append("genreIds", genreIds)
                .append("id", id)
                .append("originalLanguage", originalLanguage)
                .append("originalTitle", originalTitle)
                .append("overview", overview)
                .append("releaseDate", releaseDate)
                .append("posterPath", posterPath)
                .append("popularity", popularity)
                .append("title", title)
                .append("video", video)
                .append("voteAverage", voteAverage)
                .append("voteCount", voteCount)
                .toString();
    }
}
