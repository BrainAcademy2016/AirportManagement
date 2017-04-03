package ua.com.airport.daoimpl;

import ua.com.airport.dbUtils.GuiFilter;
import ua.com.airport.dbUtils.DataBaseUtil;
import ua.com.airport.dao.RootsDao;
import ua.com.airport.entities.RootsEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    }

    @Override
    public void updateRoot(RootsEntity rootsEntity) {

    }

    @Override
    public void createRoot(RootsEntity rootsEntity) {

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
