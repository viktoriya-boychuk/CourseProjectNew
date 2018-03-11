package dao;

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

    private String category;
    private String genre;
    private Integer duration;
    private String country;
    private String authorOrProducer;
    private String description;
    private Boolean originality;
    private Integer audienceID;

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
        return "Program{" +
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
    public void parseResultSet(ResultSet resultSet) throws SQLException {
        this.setId(resultSet.getInt(KEY_ID));
        this.setName(resultSet.getString(KEY_NAME));
        this.setGenre(resultSet.getString(KEY_GENRE));
        this.setCategory(resultSet.getString(KEY_CATEGORY));
        this.setDuration(resultSet.getInt(KEY_DURATION));
        this.setCountry(resultSet.getString(KEY_COUNTRY));
        this.setAuthorOrProducer(resultSet.getString(KEY_AUTHOR));
        this.setDescription(resultSet.getString(KEY_DESCRIPTION));
        this.setOriginality(resultSet.getBoolean(KEY_ORIGINALITY));
        this.setAudienceID(resultSet.getInt(KEY_AUDIENCE_ID));
    }
}
