package connection;

import dao.BaseDAO;
import org.json.JSONArray;

import java.sql.*;

public class SQLHelper {
    private static Connection con;
    private static SQLHelper instance;

    private SQLHelper() throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/tv_programs";
            con = DriverManager.getConnection(url, "root", "VovkViktor2281488");
        } catch (ClassNotFoundException | SQLException e) {
            throw new Exception(e);
        }
    }

    public static synchronized SQLHelper getInstance() throws Exception {
        if (instance == null) {
            instance = new SQLHelper();
        }
        return instance;
    }

    public ResultSet getResultSetFor(Class<? extends BaseDAO> type) throws SQLException, IllegalAccessException, InstantiationException {
        Statement statement = con.createStatement();
        return statement.executeQuery(type.newInstance().getSelectAllQuery());
    }

    public JSONArray getJSONArrayFor(Class<? extends BaseDAO> type) throws SQLException, IllegalAccessException, InstantiationException {
        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery(type.newInstance().getSelectAllQuery());

        JSONArray jsonArray = new JSONArray();

        while (resultSet.next()) {
            jsonArray.put(type.newInstance().parseResultSet(resultSet));
        }

        return jsonArray;
    }
}