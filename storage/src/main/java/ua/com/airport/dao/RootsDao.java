package ua.com.airport.dao;

import ua.com.airport.entities.RootsEntity;

import java.util.List;

public interface RootsDao {
    /**
     * @return list that contains all roots
     */
    List<RootsEntity> getAllRoots();

    /**
     * @param rootName
     * @return list that contains roots by selected root name
     */
    List<RootsEntity> getRootsByName(String rootName);

    void deleteRoot(int id);

    void updateRoot(RootsEntity rootsEntity);

    void createRoot(RootsEntity rootsEntity);

}
