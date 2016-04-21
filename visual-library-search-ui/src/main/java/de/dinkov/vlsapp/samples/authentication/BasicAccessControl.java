package de.dinkov.vlsapp.samples.authentication;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

import com.vaadin.ui.Notification;
import org.apache.log4j.net.SyslogAppender;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Default mock implementation of {@link AccessControl}. This implementation
 * accepts any string as a password, and considers the user "admin" as the only
 * administrator.
 */
public class BasicAccessControl implements AccessControl {
    protected Properties prop = new Properties();
    private static final String propFileName = "mysql.properties";

    private void readProperties() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
        if (inputStream != null) {
            try {
                prop.load(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean signUp(String username, String password) {
        boolean isAuthenticated = false;
          /*  Connection conn;
        String userName = "";

        try {
            readProperties();
            Class.forName(prop.getProperty("JDBC_DRIVER"));
            conn = DriverManager.getConnection(prop.getProperty("DB_URL"), prop.getProperty("USER"), prop.getProperty("PASS"));
            String sql = "SELECT user_name FROM users WHERE user_name = ? AND user_pass = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            if (rs.next()){
                userName  = rs.getString("user_name");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
*/
         String hashedPassword = generateHash(password);

        //init
        Connection conn = null;

        try{
            readProperties();
            Class.forName(prop.getProperty("JDBC_DRIVER"));
            conn = DriverManager.getConnection(prop.getProperty("DB_URL"), prop.getProperty("USER"), prop.getProperty("PASS"));

            String sql = "INSERT INTO users ("
                    + " user_name,"
                    + " user_pass"
                    + ") VALUES ("
                    + "?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, hashedPassword);
            statement.executeUpdate();

            isAuthenticated = true;
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{

            try{
                if(conn!=null)
                    conn.close();
                System.out.print("Connection Closed!");
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
      //  System.out.println("Goodbye!");

        return isAuthenticated;
    }

    @Override
    public boolean signIn(String username, String password, Boolean rememberme) {

        Boolean isSigned = false;
        if (!(username == null || username.isEmpty()))
        {
            Subject currentUser = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            //  token.setRememberMe(rememberme);

            try {
                currentUser.login(token); //tries to authenticate user
                CurrentUser.set(username);
                isSigned = true;
            } catch (Exception ex) { //if authentication is unsuccessful
               // Notification.show("Login Error:", "Invalid username/password combination.", Notification.Type.ERROR_MESSAGE);
            }

        }

        return isSigned;
    }

    @Override
    public boolean isUserSignedIn() {
        return !CurrentUser.get().isEmpty();
    }

    @Override
    public boolean isUserInRole(String role) {
        if ("admin".equals(role)) {
            // Only the "admin" user is in the "admin" role
            return getPrincipalName().equals("admin");
        }

        // All users are in all non-admin roles
        return true;
    }

    @Override
    public String getPrincipalName() {
        return CurrentUser.get();
    }

    public static String generateHash(String input) {
        StringBuilder hash = new StringBuilder();

        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-512");
            byte[] hashedBytes = sha.digest(input.getBytes());
            char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'a', 'b', 'c', 'd', 'e', 'f' };
            for (int idx = 0; idx < hashedBytes.length; idx++) {
                byte b = hashedBytes[idx];
                hash.append(digits[(b & 0xf0) >> 4]);
                hash.append(digits[b & 0x0f]);
            }
        } catch (NoSuchAlgorithmException e) {
            // handle error here.
        }

        return hash.toString();
    }

}
