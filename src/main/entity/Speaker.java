package entity;

import java.util.Objects;

public class Speaker extends User {
    private int rating;

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Speaker speaker = (Speaker) o;
        return rating == speaker.rating;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), rating);
    }
}
