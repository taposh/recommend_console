/**
 * Author: Taposh
 * Date: 9/24/13
 * Time: 9:04 PM
 */
public class ratingsData {

    public int userId;
    public int movieId;
    public double ratingValue;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public double getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(double ratingValue) {
        this.ratingValue = ratingValue;
    }
}
