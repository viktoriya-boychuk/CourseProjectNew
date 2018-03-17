package dao;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Announcer extends BaseDAO {
    private static final String SELECT_ALL = "SELECT * FROM announcers";

    private Integer careerBeginYear;
    private Integer careerEndYear;
    private Date birthDate;
    private String education;
    private String description;

    private enum Sex {male, female;}

    private Sex sex;

    public Integer getCareerBeginYear() {
        return careerBeginYear;
    }

    public void setCareerBeginYear(Integer careerBeginYear) {
        this.careerBeginYear = careerBeginYear;
    }

    public Integer getCareerEndYear() {
        return careerEndYear;
    }

    public void setCareerEndYear(Integer careerEndYear) {
        this.careerEndYear = careerEndYear;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSex() {
        return this.sex.toString();
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Announcer{" +
                "careerBeginYear=" + careerBeginYear +
                ", careerEndYear=" + careerEndYear +
                ", birthDate=" + birthDate +
                ", education='" + education + '\'' +
                ", description='" + description + '\'' +
                ", sex=" + sex +
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
    public Announcer parseResultSet(ResultSet resultSet) throws SQLException {
        //TODO: parse Announcer
        return null;
    }

    @Override
    public Announcer parseJSON(JSONObject jsonObject) throws JSONException {
        return null;
    }
}
