package mx.saudade.popularmoviesapp.models;

import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by angelicamendezvega on 8/4/15.
 */
public class Movie implements Serializable {

    private static final String BASE_URL_IMAGES = "http://image.tmdb.org/t/p/";

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

    public boolean isAdult() {
        return adult;
    }

    public String getBackdropPath() {
        return StringUtils.isEmpty(backdropPath) ? StringUtils.EMPTY : backdropPath;
    }

    public int[] getGenreIds() {
        return genreIds;
    }

    public int getId() {
        return id;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getOriginalTitle() {
        return StringUtils.isEmpty(originalTitle) ? StringUtils.EMPTY : originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return StringUtils.isEmpty(releaseDate) ? "Not available" : releaseDate;
    }

    public String getReleaseMessage() {
        return "Release date: " + this.getReleaseDate();
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getThumbPosterPath() {
        if (StringUtils.isEmpty(posterPath)) {
            return null;
        }
        StringBuilder url = new StringBuilder()
                .append(BASE_URL_IMAGES)
                .append(ImageSize.W342.getSize())
                .append(posterPath);

        return url.toString();
    }

    public String getPosterPosterPath() {
        if (StringUtils.isEmpty(posterPath)) {
            return null;
        }
        StringBuilder url = new StringBuilder()
                .append(BASE_URL_IMAGES)
                .append(ImageSize.W500.getSize())
                .append(posterPath);
        return url.toString();
    }

    public float getPopularity() {
        return popularity;
    }

    public String getTitle() {
        return title;
    }

    public boolean isVideo() {
        return video;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public String getVoteAverageMessage() {
        return this.getVoteAverage() + " / 10";
    }

    public String getShareMessage() {
        StringBuilder message = new StringBuilder();
        message.append("Check ")
                .append(this.getOriginalTitle())
                .append(" (")
                .append(this.getReleaseDate())
                .append("). From Popular Movies App");
        return message.toString();
    }

    public int getVoteCount() {
        return voteCount;
    }

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
