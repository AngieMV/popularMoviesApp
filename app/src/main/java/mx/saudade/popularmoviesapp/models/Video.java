package mx.saudade.popularmoviesapp.models;

import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by angelicamendezvega on 8/24/15.
 */
public class Video implements Serializable {

    private String id;

    @SerializedName("iso_639_1")
    private String iso;

    private String key;

    private String name;

    private String site;

    private String size;

    private String type;

    public String getId() {
        return id;
    }

    public String getIso() {
        return iso;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public String getSize() {
        return size;
    }

    public String getSite() {
        return site;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        return new StringBuilder()
                .append("https://www.youtube.com/watch?v=")
                .append(key)
                .toString();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("iso", iso)
                .append("key", key)
                .append("name", name)
                .append("site", site)
                .append("size", size)
                .append("type", type)
                .toString();
    }
}
