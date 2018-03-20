package dao;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Parcelable {
    BaseDAO parseResultSet(ResultSet resultSet) throws SQLException;
    BaseDAO parseJSON(JSONObject jsonObject) throws JSONException;
    JSONObject toJSON() throws JSONException;
    String getObjectName();
}