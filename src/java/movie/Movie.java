package movie;

import java.util.Date;

public class Movie {
    private int id;
    private String name;
    private double rating;
    //constructor for movies from database (primary key as id)
    public Movie(int p_id, String p_name, double p_rating ){
        this.id = p_id;
        this.name = p_name;
        this.rating = p_rating;
    }
    //constructor for movies from file (no id)
    public Movie(String p_name, double p_rating ){
        this.id = 0;
        this.name = p_name;
        this.rating = p_rating;
    }
    public String getName() {
        return name;
    }
    public double getRating() {
        return rating;
    }
    public int getId() {
        return id;
    }
}
