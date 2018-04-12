package dao;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Program extends BaseDAO {
    private static final String KEY_ID = "pr_id";
    private static final String KEY_NAME = "pr_name";
    private static final String KEY_CATEGORY = "pr_category";
    private static final String KEY_GENRE = "pr_genre";
    private static final String KEY_DURATION = "pr_duration";
    private static final String KEY_COUNTRY = "pr_country";
    private static final String KEY_AUTHOR = "pr_author/producer";
    private static final String KEY_DESCRIPTION = "pr_description";
    private static final String KEY_ORIGINALITY = "pr_own_idea";
    private static final String KEY_AUDIENCE_ID = "pr_au_id";

    private static final String SELECT_ALL = "SELECT * FROM programs";
    private static final String DELETE = "DELETE FROM `tv_programs`.`programs` WHERE `programs`.`pr_id` = %d";

    private String category;
    private String genre;
    private Integer duration;
    private String country;
    private String authorOrProducer;
    private String description;
    private Boolean originality;
    private Integer audienceID;

    public Program() {
    }

    public Program(Integer id, String name, String category, String genre, Integer duration, String country, String authorOrProducer, String description, Boolean originality, Integer audienceID) {
        this.setId(id);
        this.setName(name);
        this.category = category;
        this.genre = genre;
        this.duration = duration;
        this.country = country;
        this.authorOrProducer = authorOrProducer;
        this.description = description;
        this.originality = originality;
        this.audienceID = audienceID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAuthorOrProducer() {
        return authorOrProducer;
    }

    public void setAuthorOrProducer(String authorOrProducer) {
        this.authorOrProducer = authorOrProducer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getOriginality() {
        return originality;
    }

    public void setOriginality(Boolean originality) {
        this.originality = originality;
    }

    public Integer getAudienceID() {
        return audienceID;
    }

    public void setAudienceID(Integer audienceID) {
        this.audienceID = audienceID;
    }

    @Override
    public String toString() {
        return "Program{" + super.toString() +
                "category='" + category + '\'' +
                ", genre='" + genre + '\'' +
                ", duration=" + duration +
                ", country='" + country + '\'' +
                ", authorOrProducer='" + authorOrProducer + '\'' +
                ", description='" + description + '\'' +
                ", originality=" + originality +
                ", audienceID=" + audienceID +
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
        jsonObject.put(KEY_CATEGORY, this.getCategory());
        jsonObject.put(KEY_GENRE, (this.getGenre() == null) ? "null" : this.getGenre());
        jsonObject.put(KEY_DURATION, this.getDuration());
        jsonObject.put(KEY_COUNTRY, this.getCountry());
        jsonObject.put(KEY_AUTHOR, this.getAuthorOrProducer());
        jsonObject.put(KEY_DESCRIPTION, this.getDescription());
        jsonObject.put(KEY_ORIGINALITY, this.getOriginality());
        jsonObject.put(KEY_AUDIENCE_ID, this.getAudienceID());
        return jsonObject;
    }

    @Override
    public String getObjectName() {
        return "Program";
    }

    @Override
    public Program parseResultSet(ResultSet resultSet) throws SQLException {
        parseData(resultSet.getInt(KEY_ID),
                resultSet.getString(KEY_NAME),
                resultSet.getString(KEY_GENRE),
                resultSet.getString(KEY_CATEGORY),
                resultSet.getInt(KEY_DURATION),
                resultSet.getString(KEY_COUNTRY),
                resultSet.getString(KEY_AUTHOR),
                resultSet.getString(KEY_DESCRIPTION),
                resultSet.getBoolean(KEY_ORIGINALITY),
                resultSet.getInt(KEY_AUDIENCE_ID));
        return this;
    }

    @Override
    public Program parseJSON(JSONObject jsonObject) throws JSONException {
        parseData(jsonObject.getInt(KEY_ID),
                jsonObject.getString(KEY_NAME),
                jsonObject.getString(KEY_GENRE),
                jsonObject.getString(KEY_CATEGORY),
                jsonObject.getInt(KEY_DURATION),
                jsonObject.getString(KEY_COUNTRY),
                jsonObject.getString(KEY_AUTHOR),
                jsonObject.getString(KEY_DESCRIPTION),
                jsonObject.getBoolean(KEY_ORIGINALITY),
                jsonObject.getInt(KEY_AUDIENCE_ID));
        return this;
    }

    private void parseData(int anInt, String string, String string2, String string3, int anInt2, String string4, String string5, String string6, boolean aBoolean, int anInt3) {
        this.setId(anInt);
        this.setName(string);
        this.setGenre(string2 == null ? "null" : string2);
        this.setCategory(string3);
        this.setDuration(anInt2);
        this.setCountry(string4);
        this.setAuthorOrProducer(string5);
        this.setDescription(string6 == null ? "null" : string6);
        this.setOriginality(aBoolean);
        this.setAudienceID(anInt3);
    }
}
