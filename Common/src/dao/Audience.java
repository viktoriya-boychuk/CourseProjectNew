package dao;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

public class Audience extends BaseDAO {
    private static final String KEY_ID = "au_id";
    private static final String KEY_NAME = "au_name";
    private static final String KEY_CATEGORY = "au_age_category";
    private static final String KEY_DESCRIPTION = "au_description";
    private static final String KEY_EMBLEM = "au_emblem";

    private static final String SELECT_ALL = "SELECT * FROM audience";
    private static final String DELETE = "DELETE FROM `tv_programs`.`audience` WHERE `audience`.`au_id` = %d";

    private String ageCategory;
    private String description;
    private String emblem;

    public Audience() {
    }

    public Audience(Integer id, String name, String category, String description, String emblem) {
        this.setId(id);
        this.setName(name);
        this.ageCategory = category;
        this.description = description;
        this.emblem = emblem;
    }

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
    public String getInsertQuery() {
        return null;
    }

    @Override
    public String getDeleteQuery() {
        return String.format(DELETE, this.getId());
    }

    @Override
    public String getUpdateQuery() {
        return null;
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(KEY_ID, this.getId());
        jsonObject.put(KEY_NAME, this.getName());
        jsonObject.put(KEY_CATEGORY, this.getAgeCategory());
        jsonObject.put(KEY_DESCRIPTION, (this.getDescription() != null) ? this.getDescription() : " ");
        jsonObject.put(KEY_EMBLEM, (this.getEmblem() != null) ? this.getEmblem() : " ");
        return jsonObject;
    }

    @Override
    public String getObjectName() {
        return "Audience";
    }

    @Override
    public Audience parseResultSet(ResultSet resultSet) throws SQLException {
        Blob image = resultSet.getBlob(KEY_EMBLEM);
        parseData(resultSet.getInt(KEY_ID),
                resultSet.getString(KEY_NAME),
                resultSet.getString(KEY_CATEGORY),
                resultSet.getString(KEY_DESCRIPTION),
                (image != null) ? Base64.getEncoder().encodeToString(image.getBytes(1, (int) image.length()))
        : "NO-IMAGE");
        return this;
    }

    @Override
    public Audience parseJSON(JSONObject jsonObject) throws JSONException {
        parseData(jsonObject.getInt(KEY_ID),
                jsonObject.getString(KEY_NAME),
                jsonObject.getString(KEY_CATEGORY),
                jsonObject.getString(KEY_DESCRIPTION),
                jsonObject.getString(KEY_EMBLEM));
        return this;
    }

    private void parseData(int anInt, String string, String string2, String string3, String string4) {
        this.setId(anInt);
        this.setName(string);
        this.setAgeCategory(string2);
        this.setDescription(string3);
        this.setEmblem(string4);
    }
}
