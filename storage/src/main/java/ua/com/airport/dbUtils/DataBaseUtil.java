package ua.com.airport.dbUtils;

import javafx.scene.control.Alert;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public abstract class DataBaseUtil{
    private String url;
    private String user;
    private String password;

    public DataBaseUtil() {
        setDataBaseProperties();
    }

    public Connection getConnectionDb(){
        try {
            return DriverManager.getConnection(this.url, this.user, this.password);
        } catch(SQLException sqlEx){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Can't connect to the DataBase");
            alert.showAndWait();
            return null;
        }
    }

    private void setDataBaseProperties() {

        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream("mysql.properties");
            prop.load(input);

            this.url = prop.getProperty("database");
            this.user = prop.getProperty("dbuser");
            this.password = prop.getProperty("dbpassword");

        } catch (IOException ex) {
           // ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Can't read MySQL property file");
            alert.showAndWait();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    //e.printStackTrace();
                }
            }
        }
    }

    public static void openConnection(){

    }

    public static void closeConnetcion(){

    }
}
