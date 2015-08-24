package mx.saudade.popularmoviesapp.models;

import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by angelicamendezvega on 8/4/15.
 */
public class Results<T> implements Serializable {

    private int page;

    @SerializedName("results")
    private List<T> results;

    public Results() {
    }

    public Results(List<T> results) {
        this.results = results;
    }

    public List<T> getResults() {
        return results;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("page", page)
                .append("results", results)
                .toString();
    }

}
