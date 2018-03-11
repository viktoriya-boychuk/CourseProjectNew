package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Parcelable {
    void parseResultSet(ResultSet resultSet) throws SQLException;
}