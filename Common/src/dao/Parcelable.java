package dao;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Parcelable {
    void parseResultSet(ResultSet resultSet) throws SQLException;
    JSONObject toJSON() throws JSONException;
    void parseJSON(JSONObject jsonObject) throws JSONException;
}