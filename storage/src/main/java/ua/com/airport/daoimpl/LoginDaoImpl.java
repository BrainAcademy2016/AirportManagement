package ua.com.airport.daoimpl;

import ua.com.airport.dbUtils.DataBaseUtil;
import ua.com.airport.dao.LoginDao;
import ua.com.airport.entities.RootsEntity;

import java.sql.*;

public class LoginDaoImpl extends DataBaseUtil implements LoginDao {

    private RootsEntity appUser;
    private Connection con;
    private PreparedStatement pstmt;
    private ResultSet rs;

    @Override
    public boolean checkAppUser(String appUserLogin, String appUserPassword) {

        String query = "SELECT idRoots, RootName, Login, Password FROM Roots WHERE Login=? AND Password=?";

        try {

            con = getConnectionDb();

            if (con != null){

                pstmt = con.prepareStatement(query);
                pstmt.setString(1, appUserLogin);
                pstmt.setString(2, appUserPassword);

                rs = pstmt.executeQuery();

                if (!rs.isBeforeFirst() ) {
                    System.out.println("No data");
                    return false;
                }


                while (rs.next()) {
                    int userId = rs.getInt(1);
                    String userName = rs.getString(2);
                    String userLogin = rs.getString(3);
                    String userPassword = rs.getString(4);
                    if (appUserLogin.equals(userLogin) && appUserPassword.equals(userPassword)) {
                        appUser = new RootsEntity(userId ,userName, userLogin, userPassword);
                        return true;
                    }
                }
            }

        } catch (SQLException sqlEx) {
            //sqlEx.printStackTrace();
            System.out.println("Problems with connection");
            return false;
        } finally {
            try { if(con!=null){con.close();} } catch(SQLException se) { /*can't do anything */ }
            try { if(con!=null){pstmt.close();} } catch(SQLException se) { /*can't do anything */ }
            try { if(con!=null){rs.close();} } catch(SQLException se) { /*can't do anything */ }
        }
        return false;
    }

    @Override
    public RootsEntity getAppUser() {
        return this.appUser;
    }
}
