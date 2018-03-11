package connection;

import dao.BaseDAO;
import dao.Program;

import java.sql.*;
import java.util.ArrayList;

public class SQLHelper {
    private static Connection con;
    private static SQLHelper instance;

    private SQLHelper() throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/tv_programs";
            con = DriverManager.getConnection(url, "root", "root");
        } catch (ClassNotFoundException e) {
            throw new Exception(e);
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public static synchronized SQLHelper getInstance() throws Exception {
        if (instance == null) {
            instance = new SQLHelper();
        }
        return instance;
    }

    public static ResultSet getDataFor(BaseDAO baseDAO) throws SQLException {
        Statement statement = con.createStatement();
        return statement.executeQuery(baseDAO.getSelectAllQuery());
    }
}
