package connection;

import dao.BaseDAO;
import org.json.JSONArray;

import java.sql.*;
import java.util.ArrayList;

public class SQLHelper {
    private static Connection con;
    private static SQLHelper instance;

    private SQLHelper() throws ClassNotFoundException, SQLException {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/tv_programs";
            con = DriverManager.getConnection(url, "viktor-vovk", "VovkViktor2281488");
//        con = DriverManager.getConnection(url, "root", "root");
            instance = this;
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

    public ArrayList<BaseDAO> getArrayListFor(Class<? extends BaseDAO> type) throws IllegalAccessException, InstantiationException, SQLException {
        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery(type.newInstance().getSelectAllQuery());

        ArrayList<BaseDAO> arrayList = new ArrayList<>();

        while(resultSet.next()) {
            arrayList.add(type.newInstance().parseResultSet(resultSet));
        }
        return arrayList;
    }

    public JSONArray getJSONArrayFor(Class<? extends BaseDAO> type) throws SQLException, IllegalAccessException, InstantiationException {
        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery(type.newInstance().getSelectAllQuery());

        JSONArray jsonArray = new JSONArray();

        while (resultSet.next()) {
            jsonArray.put(type.newInstance().parseResultSet(resultSet).toJSON());
        }

        return jsonArray;
    }

    public void insertArrayList(ArrayList<? extends BaseDAO> list) throws SQLException {
        Statement statement = con.createStatement();

        for (BaseDAO baseDAO : list) {
            baseDAO = list.get(0);
            statement.executeUpdate(baseDAO.getInsertQuery());
        }
    }

    public void updateArrayList(ArrayList<? extends BaseDAO> list) throws SQLException {
        Statement statement = con.createStatement();

        for (BaseDAO baseDAO : list) {
            baseDAO = list.get(0);
            statement.executeUpdate(baseDAO.getUpdateQuery());
        }
    }

    public void deleteObject(ArrayList<? extends BaseDAO> list) throws SQLException {
        Statement statement = con.createStatement();

        for (BaseDAO baseDAO : list) {
            baseDAO = list.get(0);
            statement.executeUpdate(baseDAO.getDeleteQuery());
        }
    }
}