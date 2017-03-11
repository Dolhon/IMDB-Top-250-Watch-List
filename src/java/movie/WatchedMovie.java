package movie;
import java.util.Date;/**
 *
 * @author Sami Antila
 * Class for WatchedMovies table from database
 */

/**
 *
 * @author Sami Antila
 Class for WatchedMovie table from database
 */
public class WatchedMovie {
    //watchedMovies_key, movie_fkey, user_fkey, date WHERE user_key = " 
    private int id;
    private int movie_key;
    private int user_key;
    private Date date;
    //constructor for WAtchedMovies from database
    public WatchedMovie(int p_id, int p_movie_key, int p_user_key, Date p_date) {
        this.id = p_id;
        this.movie_key = p_movie_key;
        this.user_key = p_user_key;
        this.date = p_date;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getMovie_key() {
        return movie_key;
    }
    public void setMovie_key(int movie_key) {
        this.movie_key = movie_key;
    }
    public int getUser_key() {
        return user_key;
    }
    public void setUser_key(int user_key) {
        this.user_key = user_key;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
}
