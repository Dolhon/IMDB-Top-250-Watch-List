package movie;
import java.io.*;
import java.net.*;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Sami Antila
 * 
 * Rotten Tomatoes API 1.0 käyttö
 */
public class RottenTomatoesAPI {
    private String apiKey = "6f2m9j5zwbjmxya52rcqwrsu";
    private String testURL = "http://api.rottentomatoes.com/api/public/v1.0.xml?apikey=" + apiKey;
    private String MovieListURL = "http://api.rottentomatoes.com/api/public/v1.0/lists/dvds/current_releases.json?limit=1&apikey=" + apiKey;
    
    //metodi yhteyden testaamiseen
    private String getHTML() {
      URL url;
      HttpURLConnection conn;
      BufferedReader rd;
      String line;
      String result = "";
      try {
         url = new URL(testURL);
         conn = (HttpURLConnection) url.openConnection();
         conn.setRequestMethod("GET");
         rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
         while ((line = rd.readLine()) != null) {
            result += line;
         }
         rd.close();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      }
      return result;
    }
    
    //metodi jolla haetaan elokuva lista
    private String getMovieList() {
      URL url;
      HttpURLConnection conn;
      BufferedReader rd;
      String line;
      String result = "";
      try {
         url = new URL(MovieListURL);
         conn = (HttpURLConnection) url.openConnection();
         conn.setRequestMethod("GET");
         rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
         while ((line = rd.readLine()) != null) {
            //System.out.println(line);
            result += line;
         }
         rd.close();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      }
      return result;
    }
    
    public static void main(String args[]) throws ParseException
    {
        RottenTomatoesAPI c = new RottenTomatoesAPI();
        System.out.println(c.getHTML()); //args[0]
        System.out.println(c.getMovieList()); //args[0] //palauttaa JSON pötkön
        String s = c.getMovieList();
        
        
        String jsonText = s;
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject)parser.parse(s); //test 20.11.
        String oid = obj.get("movies").toString();
        System.out.println("oid: " + oid);
        /*
        JSONObject obj2 = (JSONObject)parser.parse(oid);
        String oid2 = obj2.get("year").toString();
        System.out.println("oid: " + oid2);
        */
        ContainerFactory containerFactory = new ContainerFactory(){
          public List creatArrayContainer() {
            return new LinkedList();
          }

          public Map createObjectContainer() {
            return new LinkedHashMap();
          }

        };
                
        try{
          Map json = (Map)parser.parse(jsonText, containerFactory);
          Iterator iter = json.entrySet().iterator();
          System.out.println("==iterate result==");
          while(iter.hasNext()){
            Map.Entry entry = (Map.Entry)iter.next();
            System.out.println(entry.getKey() + "=>" + entry.getValue());
          }

              System.out.println("==toJSONString()==");
              System.out.println(JSONValue.toJSONString(json));
              jsonText = JSONValue.toJSONString(json);
            }
            catch(ParseException pe){
              System.out.println(pe);
        }
        try{
          Map json = (Map)parser.parse(jsonText, containerFactory);
          Iterator iter = json.entrySet().iterator();
          System.out.println("==iterate result==");
          while(iter.hasNext()){
            Map.Entry entry = (Map.Entry)iter.next();
            System.out.println(entry.getKey() + "=>" + entry.getValue());
          }

              System.out.println("==toJSONString()==");
              System.out.println(JSONValue.toJSONString(json));
            }
            catch(ParseException pe){
              System.out.println(pe);
        }
    }
            

}
