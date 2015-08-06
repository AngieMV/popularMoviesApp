package mx.saudade.popularmoviesapp.models;

/**
 * Created by angelicamendezvega on 8/5/15.
 */
public enum ImageSize {

    W92("w92")
    , W154("w154")
    , W185("w185")
    , W342("w342")
    , W500("w500")
    , W780("w780")
    , ORIGINAL("original");

    private String size;

    private ImageSize(String size) {
        this.size = size;
    }

    public String getSize() {
        return size;
    }
}
