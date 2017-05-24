package ua.com.airport.daoimpl;

import ua.com.airport.dbUtils.GuiFilter;
import ua.com.airport.dbUtils.DataBaseUtil;
import ua.com.airport.dao.RootsDao;
import ua.com.airport.entities.RootsEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RootsDaoImpl extends DataBaseUtil implements RootsDao{
    private Connection con;
    private PreparedStatement prst;
    private ResultSet rs;
    private String query = "SELECT idRoots, " +
            "RootName,  " +
            "Login, Password " +
            "FROM Roots";

    private List<RootsEntity> rootsEntityList = new ArrayList<>();

    @Override
    public List<RootsEntity> getAllRoots() {
        try{
            con = getConnectionDb();
            if(con != null) {
                prst = con.prepareStatement(query);
                rs = prst.executeQuery();
                if (!rs.isBeforeFirst()) {
                    System.out.printf("No data");
                    return null;
                }
                while (rs.next()) {
                    int idRoots = rs.getInt(1);
                    String rootName = rs.getString(2);
                    String login = rs.getString(3);
                    String password = rs.getString(4);

                    RootsEntity currentEmployee = new RootsEntity(
                            idRoots,
                            rootName,
                            login,
                            password
                                                );
                    rootsEntityList.add(currentEmployee);
                }
                return rootsEntityList;
            }

        } catch (SQLException sqlE){
            System.out.printf("Connection problem");
        } finally {
            try { if(con!=null){con.close();} } catch(SQLException se) { /*can't do anything */ }
            try { if(con!=null){prst.close();} } catch(SQLException se) { /*can't do anything */ }
            try { if(con!=null){rs.close();} } catch(SQLException se) { /*can't do anything */ }
        }
        return null;
    }

    @Override
    public List<RootsEntity> getRootsByName(String rootName) {
        return null;
    }

    @Override
    public void deleteRoot(int id) {
        String query = "DELETE FROM Roots WHERE idRoots = ?";
        try{
            con = getConnectionDb();
            if(con != null) {
                prst = con.prepareStatement(query);
                prst.setInt(1, id);
                prst.executeUpdate();
            }
        } catch (SQLException sqlE){
            System.out.printf("Connection problem");
            System.out.println(sqlE);
        } finally {
            try {
                if (con != null) {
                    prst.close();
                }
            } catch (SQLException se) {}
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException se) {
            }
        }
    }

    @Override
    public void updateRoot(RootsEntity rootsEntity) {
        String query = "UPDATE Roots SET RootName = ?, Login = ?, Password = ?" +
                "WHERE idRoots = ?";
        try{
            con = getConnectionDb();
            prst = con.prepareStatement(query);
            prst.setString(1, rootsEntity.getRootName());
            prst.setString(2, rootsEntity.getLogin());
            prst.setString(3, rootsEntity.getPassword());
            prst.setInt(4, rootsEntity.getId());
            prst.executeUpdate();
        } catch (SQLException sqlE){
            System.out.printf("Connection problem");
            System.out.println(sqlE);
        } finally {
            try{
                if (con != null){
                    prst.close();
                }
            } catch (SQLException se){}
            try{
                if (con != null){
                    con.close();
                }
            } catch (SQLException se){}
        }
    }

    @Override
    public void createRoot(RootsEntity currentEmployee) {
        String query = "INSERT INTO Roots (RootName, " + "Login, Password)" +
                "VALUE (?, ?, ?)";
        try{
            con = getConnectionDb();
            prst = con.prepareStatement(query);
            prst.setString(1, currentEmployee.getRootName());
            prst.setString(2, currentEmployee.getLogin());
            prst.setString(3, currentEmployee.getPassword());
            prst.executeUpdate();
        }catch (SQLException sqlE){
            System.out.printf("Connection problem");
            System.out.println(sqlE);
            if(sqlE instanceof SQLIntegrityConstraintViolationException) {
                // Duplicate entry
            } else {
                // Other SQL Exception
            }
        } finally {
            try{
                if (con != null){
                    prst.close();
                }
            } catch (SQLException se){}
            try{
                if (con != null){
                    con.close();
                }
            } catch (SQLException se){}
            rootsEntityList.add(currentEmployee);
        }
    }

    public List<RootsEntity> getAllFilteredRoots(List<GuiFilter> filtersList){
        ArrayList<String> filterValuers = new ArrayList<>();
        filtersList.forEach(filter->{
            if (filter.getSelectedValue()!=null) {
                String filteredString;
                filterValuers.add(filter.getSelectedValue());
//                String tableShortName = "a.";
//                if (filter.getSqlField().equals("ClassType") || filter.getSqlField().equals("Price")){
//                    tableShortName = "b.";
//                }
                if (filterValuers.size() == 1) {
                    filteredString = " WHERE " + filter.getSqlField() + "='"+ filter.getSelectedValue()+"'";
                } else {
                    filteredString = " AND " + filter.getSqlField() + "='"+ filter.getSelectedValue()+"'";
                }
                query = query+filteredString;
                // System.out.println(query)
            }
        });
        return getAllRoots();
    }
}
