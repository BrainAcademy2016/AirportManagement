package ua.com.airport.daoimpl;

import ua.com.airport.dbUtils.GuiFilter;
import javafx.scene.control.Alert;
import ua.com.airport.dbUtils.DataBaseUtil;
import ua.com.airport.dao.FilterDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FiltersDaoImpl extends DataBaseUtil implements FilterDao {

    private Connection con;
    private PreparedStatement pstmt;
    private ResultSet rs;

    @Override
    public void getFilterItems(GuiFilter filter) {
        try {
            con = getConnectionDb();
            if (con != null) {
                String field = filter.getSqlField();
                String table = filter.getSqlTable();
                String query = "SELECT DISTINCT " + field + " FROM " + table + " ORDER BY " + field;
                pstmt = con.prepareStatement(query);
                rs = pstmt.executeQuery();
                if (!rs.isBeforeFirst()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("No data from DB");
                    alert.showAndWait();
                }
                int i = 1;
                while (rs.next()) {
                    String item = rs.getString(1);
                    filter.getFilterValues().add(item);
                }
            }

        } catch (SQLException sqlEx){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Problems to get the data from DB");
            alert.showAndWait();
        } finally {
            try { if(con!=null){con.close();} } catch(SQLException se) {/*can't do anything */ }
            try { if(con!=null){pstmt.close();} } catch(SQLException se) { /*can't do anything */}
            try { if(con!=null){rs.close();} } catch(SQLException se) {/*can't do anything */ }
        }

    }

}
