package app.myapp.restuantadmin.Model;

public class User {


    private String token;


    public User() {
    }

    public User(String token) {
        this.token = token;
    }




    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
