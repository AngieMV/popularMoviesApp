package mx.saudade.popularmoviesapp.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by angelicamendezvega on 8/24/15.
 */
public class Review implements Serializable {

    private String id;

    private String author;

    private String content;

    private String url;

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("author", author)
                .append("content", content)
                .append("url", url)
                .toString();
    }

}
