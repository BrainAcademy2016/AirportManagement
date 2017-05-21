package ua.com.airport.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PassengersEntity {
    private SimpleIntegerProperty id;
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private SimpleStringProperty nationality;
    private SimpleStringProperty passport;
    private SimpleStringProperty birthday;
    private SimpleStringProperty sex;
    private SimpleStringProperty classType;
    private SimpleStringProperty flightNum;

    public PassengersEntity() {
    }

    public PassengersEntity(String firstName, String lastName, String nationality, String passport, String birthday, String sex, String classType, String flightNum) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.nationality = new SimpleStringProperty(nationality);
        this.passport = new SimpleStringProperty(passport);
        this.birthday = new SimpleStringProperty(birthday);
        this.sex = new SimpleStringProperty(sex);
        this.classType = new SimpleStringProperty(classType);
        this.flightNum = new SimpleStringProperty(flightNum);
    }

    public PassengersEntity(int id, String firstName, String lastName, String nationality, String passport, String birthday, String sex, String classType, String flightNum) {
        this.id = new SimpleIntegerProperty(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.nationality = new SimpleStringProperty(nationality);
        this.passport = new SimpleStringProperty(passport);
        this.birthday = new SimpleStringProperty(birthday);
        this.sex = new SimpleStringProperty(sex);
        this.classType = new SimpleStringProperty(classType);
        this.flightNum = new SimpleStringProperty(flightNum);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName = new SimpleStringProperty(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName = new SimpleStringProperty(lastName);
    }

    public String getNationality() {
        return nationality.get();
    }

    public void setNationality(String nationality) {
        this.nationality = new SimpleStringProperty(nationality);
    }

    public String getPassport() {
        return passport.get();
    }

    public void setPassport(String passport) {
        this.passport = new SimpleStringProperty(passport);
    }

    public String getBirthday() {
        return birthday.get();
    }

    public void setBirthday(String birthday) {
        this.birthday = new SimpleStringProperty(birthday);
    }

    public String getSex() {
        return sex.get();
    }

    public void setSex(String sex) {
        this.sex = new SimpleStringProperty(sex);
    }

    public String getClassType() {
        return classType.get();
    }

    public void setClassType(String classType) {
        this.classType = new SimpleStringProperty(classType);
    }

    public String getFlightNum() {
        return flightNum.get();
    }

    public void setFlightNum(String flightNum) {
        this.flightNum = new SimpleStringProperty(flightNum);
    }

    public IntegerProperty idProperty(){
        return id;
    }

    public StringProperty firstNameProperty(){
        return firstName;
    }

    public StringProperty lastNameProperty(){
        return lastName;
    }

    public StringProperty nationalityProperty(){
        return nationality;
    }

    public StringProperty passportProperty(){
        return passport;
    }

    public StringProperty birthdayProperty(){
        return birthday;
    }

    public StringProperty sexProperty(){
        return sex;
    }

    public StringProperty classTypeProperty(){
        return classType;
    }

    public StringProperty flightNumProperty(){
        return flightNum;
    }
}
