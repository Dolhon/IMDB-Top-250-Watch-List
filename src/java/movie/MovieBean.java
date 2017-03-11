/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movie;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.HashMap;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.PieChartModel;

@ManagedBean(name="top250")
@SessionScoped
/**
 *
 * @author Sami Antila
 * 
 */
public class MovieBean {
    private static ArrayList<Movie> movies = new ArrayList<>();
    private static ArrayList<Movie> filteredMovies = new ArrayList<>();
    private static ArrayList<Movie> dtMoviesNotWatched = new ArrayList<>();
    private static ArrayList<Movie> dtMoviesWatched = new ArrayList<>();
    private static ArrayList<WatchedMovie> moviesWatched = new ArrayList<>();
    private static User activeUser;
    private static int userID = 1;
    private static Database dbConn = new Database();
    private static Stats stats;
    private PieChartModel pieModel;
    private LineChartModel dateModel;
    public String init(){
        try {
            movies = dbConn.getMoviesFromDatabase();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MovieBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MovieBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            moviesWatched = dbConn.getMoviesWatchedFromDatabase(userID);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MovieBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MovieBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(MovieBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.stats = new Stats(movies, moviesWatched);
        createPieModel();
        createDateModel();
        populateDtArrayLists();
        return "";
    }
    private void createPieModel() {
        setPieModel(new PieChartModel());
         
        getPieModel().set("Watched ", stats.getWatched());
        getPieModel().set("Not Watched", 250-stats.getWatched());
         
        getPieModel().setTitle("Watched movies");
        getPieModel().setLegendPosition("e");
        pieModel.setShowDataLabels(true);
    }
    private void createDateModel() {
        dateModel = new LineChartModel();
        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Series 1");
        
        HashMap<String, Integer> watchedDates = stats.getWatchedDates();
        
        for(String date: watchedDates.keySet()) {
            series1.set(date, watchedDates.get(date));
            //System.out.println(watchedDates.get(date));
        }
        
        
        /*series1.set("2014-01-01", 51);
        series1.set("2014-01-06", 22);
        series1.set("2014-01-12", 65);
        series1.set("2014-01-18", 74);
        series1.set("2014-01-24", 24);
        series1.set("2014-01-30", 51);*/
 
        dateModel.addSeries(series1);
        
        dateModel.setTitle("Movies watched by date");
        dateModel.setZoom(true);
        dateModel.getAxis(AxisType.Y).setLabel("Movies watched");
        DateAxis axis = new DateAxis("Dates");
        axis.setTickAngle(-50);
        //axis.setMax("2014-02-01");
        axis.setTickFormat("%b %#d, %y");
        dateModel.getAxes().put(AxisType.X, axis);
    }
    private void populateDtArrayLists() {
        dtMoviesWatched.clear();
        dtMoviesNotWatched.clear();
        for(int i=0; i<movies.size(); i++){
            if(checkIfMovieWatched(movies.get(i).getId())) {
                dtMoviesWatched.add(movies.get(i));
            } else {
                dtMoviesNotWatched.add(movies.get(i));
            }         
        }
    }   
    public boolean checkIfMovieWatched(int movieID){
        for(int i=0;i<moviesWatched.size();i++) {
            if(moviesWatched.get(i).getMovie_key() == movieID) {
                return true;
            }
        }
        return false;
    }    
    public static ArrayList<Movie> getDtMoviesNotWatched() {
        return dtMoviesNotWatched;
    }
    public static void setDtMoviesNotWatched(ArrayList<Movie> aDtMoviesNotWatched) {
        dtMoviesNotWatched = aDtMoviesNotWatched;
    }
    public static ArrayList<Movie> getDtMoviesWatched() {
        return dtMoviesWatched;
    }
    public static void setDtMoviesWatched(ArrayList<Movie> aDtMoviesWatched) {
        dtMoviesWatched = aDtMoviesWatched;
    }
    public static ArrayList<Movie> getFilteredMovies() {
        return filteredMovies;
    }
    public static void setFilteredMovies(ArrayList<Movie> aFilteredMovies) {
        filteredMovies = aFilteredMovies;
    }
    public static ArrayList<Movie> getMovies() {
        return movies;
    }
    public static void setMovies(ArrayList<Movie> aMovies) {
        movies = aMovies;
    }
    public void setMovieWatched(int p_movieID){
        try {
            int movieID = p_movieID;
            int userID = this.activeUser.getId();
            dbConn.setMovieWatchedInDatabase(userID, movieID);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MovieBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MovieBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void setMovieNotWatched(int p_movieID){
        try {
            int movieID = p_movieID;
            int userID = this.activeUser.getId();
            dbConn.setMovieNotWatchedInDatabase(userID, movieID);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MovieBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MovieBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static User getActiveUser() {
        try {
            activeUser = dbConn.getUserFromDatabase(userID);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MovieBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MovieBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return activeUser;
    }
    public int getWatchedMovieSize() {
        return moviesWatched.size();
    }
    public String GetMovieWatchedDate(int movieID){
        for(int i=0;i<moviesWatched.size();i++) {
            if(moviesWatched.get(i).getMovie_key() == movieID) {
                DateFormat parser = new SimpleDateFormat("dd-MM-yyyy");
                return parser.format(moviesWatched.get(i).getDate());
            }
        }
        return "Not yet";
    }
    public PieChartModel getPieModel() {
        return pieModel;
    }
    public void setPieModel(PieChartModel pieModel) {
        this.pieModel = pieModel;
    }   
    public LineChartModel getDateModel() {
        return dateModel;
    }    
}
