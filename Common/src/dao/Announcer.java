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
    private static final String INSERT = "INSERT INTO `tv_programs`.`announcers` VALUES (null,'%s',%d,%d,'%s','%s','%s','%d')";
    private static final String DELETE = "DELETE FROM `tv_programs`.`announcers` WHERE `announcers`.`an_id` = %d";
    private static final String UPDATE = "UPDATE `tv_programs`.`announcers` SET `an_id` = %d, `an_name` = '%s', " +
            "`an_career_begin_year` = %d, `an_career_end_year` = %d, `an_birth_date` ='%s', `an_education` ='%s',`an_description` ='%s',  `an_sex` = %d WHERE `an_id` = %d; ";

    private Integer careerBeginYear;
    private Integer careerEndYear;
    private Date birthDate;
    private String education;
    private String description;

    public enum Sex {MALE, FEMALE;}

    private Sex sex;

    public Announcer() {
    }

    public Announcer(Integer id, String name, Integer careerBegin, Integer careerEnd, Date birthDate, String education, String description, Sex sex) {
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

    public Sex getSexEnum() {
        return this.sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return this.toJSON().toString();
    }

    @Override
    public String getSelectAllQuery() {
        return SELECT_ALL;
    }

    @Override
    public String getInsertQuery() {
        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd");

        Integer sex = 0;
        sex = getIntegerSex(sex);
        return String.format(INSERT,
                this.getName(), this.getCareerBeginYear(), this.getCareerEndYear(),
                formatter.format(this.getBirthDate()), this.getEducation(), this.getDescription(), sex);
    }

    @Override
    public String getUpdateQuery() {
        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd");

        Integer sex = 0;
        sex = getIntegerSex(sex);
        String string = String.format(UPDATE,
                this.getId(), this.getName(), this.getCareerBeginYear(), this.getCareerEndYear(),
                formatter.format(this.getBirthDate()), this.getEducation(), this.getDescription(), sex, this.getId());;
        return string;
    }

    private Integer getIntegerSex(Integer sex) {
        switch (this.getSexEnum()) {
            case MALE:
                sex = 1;
                break;
            case FEMALE:
                sex = 2;
                break;
        }
        return sex;
    }

    @Override
    public String getDeleteQuery() {
        return String.format(DELETE, this.getId());
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(KEY_ID, this.getId());
        jsonObject.put(KEY_NAME, this.getName());
        jsonObject.put(KEY_CAREER_BEGIN, this.getCareerBeginYear());
        jsonObject.put(KEY_CAREER_END, this.getCareerEndYear());
        jsonObject.put(KEY_BIRTH_DATE, formatter.format(this.getBirthDate()));
        jsonObject.put(KEY_EDUCATION, (this.getEducation() != null) ? this.getEducation() : " ");
        jsonObject.put(KEY_DESCRIPTION, (this.getDescription() != null) ? this.getDescription() : " ");
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
        } catch (ParseException e) {
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
        this.setDescription((string3 != null) ? string3 : "");
        switch (string4) {
            case "MALE":
                this.setSex(Sex.MALE);
                break;
            case "FEMALE":
                this.setSex(Sex.FEMALE);
                break;
            case "чоловіча":
                this.setSex(Sex.MALE);
                break;
            case "жіноча":
                this.setSex(Sex.FEMALE);
                break;
        }
    }
}
