package dao;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Audience extends BaseDAO {
    private static final String SELECT_ALL = "SELECT * FROM audience";

    private String ageCategory;
    private String description;
    private String emblem;

    public String getAgeCategory() {
        return ageCategory;
    }

    public void setAgeCategory(String ageCategory) {
        this.ageCategory = ageCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmblem() {
        return emblem;
    }

    public void setEmblem(String emblem) {
        this.emblem = emblem;
    }

    @Override
    public String toString() {
        return "Audience{" +
                "ageCategory='" + ageCategory + '\'' +
                ", description='" + description + '\'' +
                ", emblem='" + emblem + '\'' +
                '}';
    }

    @Override
    public String getSelectAllQuery() {
        return SELECT_ALL;
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        return null;
    }

    @Override
    public Audience parseResultSet(ResultSet resultSet) throws SQLException {
        return null;
    }

    @Override
    public Audience parseJSON(JSONObject jsonObject) throws JSONException {
        return null;
    }
}
