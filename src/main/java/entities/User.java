package entities;

import helpers.Environment;

/**
 * User entity
 */
public class User {
    
    public static final User ADMINISTRATOR = new User()
            .setUserName(Environment.getAdminUsername())
            .setPassword(Environment.getAdminPassword());
    
    private String userName;
    private String password;
    
    public String getUserName() {
        return userName;
    }
    
    public User setUserName(String userName) {
        this.userName = userName;
        return this;
    }
    
    public String getPassword() {
        return password;
    }
    
    public User setPassword(String password) {
        this.password = password;
        return this;
    }
}
