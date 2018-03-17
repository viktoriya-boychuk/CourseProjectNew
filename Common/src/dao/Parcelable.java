package dao;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Parcelable {
    Parcelable parseResultSet(ResultSet resultSet) throws SQLException;
    Parcelable parseJSON(JSONObject jsonObject) throws JSONException;
    JSONObject toJSON() throws JSONException;
}