package movie;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Sami Antila
 */
public class Stats {
    private double percent;
    private int totalMovies = 250;
    private int totalMoviesWatched = 0;
    private int highestMovieNotWatched;
    private int lowestMovieNotWatched;
    ArrayList<Movie> movies = new ArrayList<>();
    ArrayList<WatchedMovie> moviesWatched = new ArrayList<>();
    private HashMap<String, Integer> watchedDates = new HashMap<>();
    
    public Stats(ArrayList<Movie> p_movies, ArrayList<WatchedMovie> p_watchedMovies) {
        this.movies = p_movies;
        this.moviesWatched = p_watchedMovies;
        TotalMoviesWatched();
        CalculatePercentage();
        calculateMoviesWatchedByDate();
        
    }
    private void CalculatePercentage(){
        this.percent = (double) (totalMoviesWatched / totalMovies * 100);
    }
    private void TotalMoviesWatched(){
        for(int j = 0;j<moviesWatched.size();j++) {
            totalMoviesWatched += 1;
        }
    }
    public double getPercent() {
        return percent;
    }
    public int getWatched() {
        return totalMoviesWatched;
    }
    public int getTotalMovies() {
        return totalMovies;
    }
    public int getHighestMovieNotWatched() {
        return highestMovieNotWatched;
    }
    public int getLowestMovieNotWatched() {
        return lowestMovieNotWatched;
    }
    
    public void calculateMoviesWatchedByDate(){
        for(int i=0;i<moviesWatched.size();i++) {
            DateFormat parser = new SimpleDateFormat("yyyy-MM-dd"); //"2014-01-30"
            String date = parser.format(moviesWatched.get(i).getDate());
            if(watchedDates.containsKey(date)) {
                watchedDates.put(date, watchedDates.get(date)+1);
                System.out.println("jaa");
            } else {
                watchedDates.put(date, 1);
                System.out.println("joo");
            }
        }
    }

    public HashMap<String, Integer> getWatchedDates() {
        return watchedDates;
    }

    public void setWatchedDates(HashMap<String, Integer> watchedDates) {
        this.watchedDates = watchedDates;
    }
}
