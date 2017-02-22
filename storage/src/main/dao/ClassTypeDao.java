package main.dao;


import main.entities.ClassTypeEntity;

import java.util.List;

public interface ClassTypeDao {
    /**
     * @return list that contains all class types
     */
    List<String> getClassType();

    void deleteClassType(String className);

    void createClassType(ClassTypeEntity classTypeEntity);

    void updateClassType(ClassTypeEntity classTypeEntity);

}