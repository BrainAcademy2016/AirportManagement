package ua.com.airport.entities;

import javafx.beans.property.SimpleStringProperty;

public class ClassTypeEntity {
    private SimpleStringProperty className;

    public ClassTypeEntity() {
    }

    public ClassTypeEntity(String className) {
        this.className = new SimpleStringProperty(className);
    }

    public String getClassName() {
        return className.get();
    }

    public void setClassName(String className) {
        this.className.set(className);
    }
}
