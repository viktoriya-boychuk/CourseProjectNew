package dao;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Announcer extends BaseDAO {
    private static final String KEY_ID = "an_id";
    private static final String KEY_NAME = "an_name";
    private static final String KEY_CAREER_BEGIN = "an_career_begin_year";
    private static final String KEY_CAREER_END = "an_career_end_year";
    private static final String KEY_BIRTH_DATE = "an_birth_date";
    private static final String KEY_EDUCATION = "an_education";
    private static final String KEY_DESCRIPTION = "an_description";
    private static final String KEY_SEX = "an_sex";
    private static final String SELECT_ALL = "SELECT * FROM announcers";

    private Integer careerBeginYear;
    private Integer careerEndYear;
    private Date birthDate;
    private String education;
    private String description;

    private enum Sex {male, female;}

    private Sex sex;

    public Announcer(){
    }

    public Announcer(Integer id, String name, Integer careerBegin, Integer careerEnd, Date birthDate, String education, String description, Sex sex){
        this.setId(id);
        this.setName(name);
        this.careerBeginYear = careerBegin;
        this.careerEndYear = careerEnd;
        this.birthDate = birthDate;
        this.education = education;
        this.description = description;
        this.sex = sex;
    }

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
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(KEY_ID, this.getId());
        jsonObject.put(KEY_NAME, this.getName());
        jsonObject.put(KEY_CAREER_BEGIN, this.getCareerBeginYear());
        jsonObject.put(KEY_CAREER_END, this.getCareerEndYear());
        jsonObject.put(KEY_BIRTH_DATE, this.getBirthDate());
        jsonObject.put(KEY_EDUCATION, this.getEducation());
        jsonObject.put(KEY_DESCRIPTION, this.getDescription());
        jsonObject.put(KEY_SEX, this.getSex());
        return jsonObject;
    }

    @Override
    public String getObjectName() {
        return "Announcer";
    }

    @Override
    public Announcer parseResultSet(ResultSet resultSet) throws SQLException {
        parseData(resultSet.getInt(KEY_ID),
                resultSet.getString(KEY_NAME),
                resultSet.getInt(KEY_CAREER_BEGIN),
                resultSet.getInt(KEY_CAREER_END),
                resultSet.getDate(KEY_BIRTH_DATE),
                resultSet.getString(KEY_EDUCATION),
                resultSet.getString(KEY_DESCRIPTION),
                resultSet.getString(KEY_SEX));
        return this;
    }

    @Override
    public Announcer parseJSON(JSONObject jsonObject) throws JSONException {
        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd");
        try {
            parseData(jsonObject.getInt(KEY_ID),
                    jsonObject.getString(KEY_NAME),
                    jsonObject.getInt(KEY_CAREER_BEGIN),
                    jsonObject.getInt(KEY_CAREER_END),
                    formatter.parse(jsonObject.getString(KEY_BIRTH_DATE)),
                    jsonObject.getString(KEY_EDUCATION),
                    jsonObject.getString(KEY_DESCRIPTION),
                    jsonObject.getString(KEY_SEX));
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return this;
    }

    private void parseData(int anInt, String string, int anInt2, int anInt3, Date date, String string2, String string3, String string4) {
        this.setId(anInt);
        this.setName(string);
        this.setCareerBeginYear(anInt2);
        this.setCareerEndYear(anInt3);
        this.setBirthDate(date);
        this.setEducation(string2);
        this.setDescription(string3);
        switch (string4) {
            case "male": this.setSex(Sex.male);
            case "female": this.setSex(Sex.female);
        }
    }
}
