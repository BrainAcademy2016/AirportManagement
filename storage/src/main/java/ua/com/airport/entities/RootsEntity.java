package ua.com.airport.entities;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RootsEntity {
    private SimpleIntegerProperty id;
    private SimpleStringProperty rootName;
    private SimpleStringProperty login;
    private SimpleStringProperty password;

    public RootsEntity(String rootName, String login, String password){
        this.rootName = new SimpleStringProperty(rootName);
        this.login = new SimpleStringProperty(login);
        this.password =new SimpleStringProperty(password);
    }

    public RootsEntity (int idRoots, String rootName, String login, String password){
        this.id = new SimpleIntegerProperty(idRoots);
        this.rootName = new SimpleStringProperty(rootName);
        this.login = new SimpleStringProperty(login);
        this.password =new SimpleStringProperty(password);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id = new SimpleIntegerProperty(id);
    }

    public String getRootName() {
        return rootName.get();
    }

    public void setRootName(String rootName) {
        this.rootName = new SimpleStringProperty(rootName);
    }

    public String getLogin() {
        return login.get();
    }

    public void setLogin(String login) {
        this.login = new SimpleStringProperty(login);
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password = new SimpleStringProperty(password);
    }

    public SimpleIntegerProperty id(){
        return id;
    }

    public StringProperty rootNameProperty(){
        return rootName;
    }

    public StringProperty loginProperty(){
        return login;
    }

    public StringProperty passwordProperty(){
        return password;
    }

}
