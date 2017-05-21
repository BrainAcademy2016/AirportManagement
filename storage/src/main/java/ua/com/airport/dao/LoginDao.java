package ua.com.airport.dao;

import ua.com.airport.entities.RootsEntity;

public interface LoginDao {
    boolean checkAppUser(String appUserLogin, String appUserPassword);
    RootsEntity getAppUser();
}
