package entity;

public class Speaker extends User {
    int rating;

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

//    @Override
//    public String toString() {
//        return super.toString() + rating;
//    }


    @Override
    public String toString() {
        return "Speaker{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", surname='" + getSurname() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", position='" + getPosition() + '\'' +
                ", rating='" + getRating() + '\'' +
                '}';
    }
}
