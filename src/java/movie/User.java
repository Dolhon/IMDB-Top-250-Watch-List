package movie;
/*
 * @author Sami Antila
 * Class for storing user information
 */
public class User {
    private int id;
    private String name;
    //Constructor for user info from database
    public User(int p_id, String p_name) {
        this.id = p_id;
        this.name = p_name;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
}
