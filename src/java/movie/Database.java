package movie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sami Antila
 * Database business logic
 */
public class Database {
    private static final String dbClassName = "com.mysql.jdbc.Driver";
    private static final String CONN = "jdbc:mysql://127.0.0.1/jee";
    
    private Connection MySQLConn()
            throws ClassNotFoundException,SQLException {
        Class.forName(dbClassName);
        Properties p = new Properties();
        p.put("user","root");
        p.put("password","");
        Connection c = DriverManager.getConnection(CONN,p);
        return c;
    }
    
    public void insertMoviesToDatabase(ArrayList<Movie> p_movies) 
            throws ClassNotFoundException,SQLException {
        Connection c = MySQLConn();
        for(int i = 0;i<p_movies.size();i++) {
            String query = "INSERT INTO movies (movie_name, movie_rating)" 
                    + " values (?, ?)";
            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = c.prepareStatement(query);
            preparedStmt.setString (1, p_movies.get(i).getName());
            preparedStmt.setDouble (2, p_movies.get(i).getRating());
 
            // execute the preparedstatement
            preparedStmt.execute();
        }
        c.close();
    }
    public ArrayList<Movie> getMoviesFromDatabase()
            throws ClassNotFoundException,SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        ArrayList<Movie> movies = new ArrayList<>();
        Connection c = MySQLConn();
        stmt = c.createStatement();
        rs = stmt.executeQuery("SELECT movie_key, movie_name, movie_rating FROM movies");
        while(rs.next()){
            Movie movie = new Movie(rs.getInt("movie_key"),rs.getString("movie_name"),rs.getDouble("movie_rating"));
            movies.add(movie);
        }
        rs.close();
        c.close();
        return movies;
    }
    public User getUserFromDatabase(int p_userID)
            throws ClassNotFoundException,SQLException {
        String id = Integer.toString(p_userID);
        ResultSet rs = null;
        User user = new User(0, "Not found");
        Connection c = MySQLConn();
        String query = "SELECT user_key, user_name FROM users WHERE user_key = " 
                    + "(?)";
        PreparedStatement preparedStmt = c.prepareStatement(query);
            preparedStmt.setString (1,id);
        rs = preparedStmt.executeQuery();  
        while(rs.next()){
            user.setId(rs.getInt("user_key"));
            user.setName(rs.getString("user_name"));
        }
        rs.close();
        c.close();
        return user;
    }
    public ArrayList<WatchedMovie> getMoviesWatchedFromDatabase(int p_userID)
            throws ClassNotFoundException,SQLException, ParseException {
        String id = Integer.toString(p_userID);
        ResultSet rs = null;
        ArrayList<WatchedMovie> watchedMovies = new ArrayList<>();
        Connection c = MySQLConn();
        String query = "SELECT watchedMovies_key, movie_fkey, user_fkey, date FROM watchedMovies WHERE user_fkey = " 
                    + "(?)";
        PreparedStatement preparedStmt = c.prepareStatement(query);
            preparedStmt.setInt (1,1);
        rs = preparedStmt.executeQuery();  
        while(rs.next()){WatchedMovie movie = new WatchedMovie(rs.getInt("watchedMovies_key"),
                    rs.getInt("movie_fkey"),rs.getInt("user_fkey"),rs.getTimestamp("date"));
            watchedMovies.add(movie);
            System.out.println(movie.getDate());
        }
        rs.close();
        c.close();
        return watchedMovies;
        
    }
    public void setMovieWatchedInDatabase(int p_userID, int p_movieID) 
            throws ClassNotFoundException,SQLException {
        Connection c = MySQLConn();
        int user_fkey = p_userID;
        int movie_fkey = p_movieID;
        String query = "INSERT INTO watchedmovies (movie_fkey, user_fkey)" 
                    + " values (?, ?)";
        // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = c.prepareStatement(query);
            preparedStmt.setInt (1, movie_fkey);
            preparedStmt.setInt (2, user_fkey);
 
            // execute the preparedstatement
            preparedStmt.execute();
        c.close();
    }
    public void setMovieNotWatchedInDatabase(int p_userID, int p_movieID) 
            throws ClassNotFoundException,SQLException {
        Connection c = MySQLConn();
        int user_fkey = p_userID;
        int movie_fkey = p_movieID;
        String query = "DELETE FROM watchedmovies WHERE movie_fkey = ? AND user_fkey = ?;";
        // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = c.prepareStatement(query);
            preparedStmt.setInt (1, movie_fkey);
            preparedStmt.setInt (2, user_fkey);
 
            // execute the preparedstatement
            preparedStmt.execute();
        c.close();
    }
    /* for testing purposes only*/
    /*public static void main(String[] args) {
        try {
            Database dbConn = new Database();
            dbConn.getMoviesFromDatabase();
            System.out.println(dbConn.getMoviesFromDatabase().size());
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ImdbData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ImdbData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
}
