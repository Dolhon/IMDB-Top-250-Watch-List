package movie;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Sami Antila
 * Imdb top 250 tuonti tiedostosta ja datan siirto tietokantaan
 */
public class ImdbData {
    
    private static final String dbClassName = "com.mysql.jdbc.Driver";
    private static final String CONN ="jdbc:mysql://127.0.0.1/jee";
    
    private static ArrayList<Movie> importMoviesFromFile(){
        ArrayList<Movie> movieList = new ArrayList<>();
        try {
            File file = new File("D:\\ratings.list");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String [] splitString = line.split("  ");
                System.out.println(splitString[2]);
                System.out.println(splitString[3]);
                movieList.add(new Movie(splitString[3], Double.parseDouble(splitString[2])));
                
		stringBuffer.append(line);
		stringBuffer.append("\n");
            }
            fileReader.close();
            //System.out.println("Contents of file:");
            //System.out.println(stringBuffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i=0;i < movieList.size();i++) {
            System.out.println(movieList.get(i).getName() + " " + movieList.get(i).getRating());
        }
        return movieList;
    }
    
    
    public static void main(String[] args) {
        try {
            Database dbConn = new Database();
            dbConn.insertMoviesToDatabase(importMoviesFromFile());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ImdbData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ImdbData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

/*
    private static void saveMoviesToDatabase(ArrayList<Movie> p_movies) 
            throws ClassNotFoundException,SQLException {
        
        Class.forName(dbClassName);
        Properties p = new Properties();
        Statement stmt = null;
        ResultSet rs = null;
        p.put("user","root");
        p.put("password","");
        Connection c = DriverManager.getConnection(CONN,p);
        stmt = c.createStatement();
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
    }*/
