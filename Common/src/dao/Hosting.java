package dao;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Hosting extends BaseDAO {
    private static final String KEY_ID = "pha_id";
    private static final String KEY_CONTRACT_BEGIN = "pha_contract_begin_date";
    private static final String KEY_CONTRACT_END = "pha_contract_end_date";
    private static final String KEY_GRATUITY = "pha_announcer_gratuity";
    private static final String KEY_ANNOUNCER_ID = "pha_an_id";
    private static final String KEY_PROGRAM_ID = "pha_pr_id";

    private static final String SELECT_ALL = "SELECT * FROM programs_have_announcers";
    private static final String DELETE = "DELETE FROM `tv_programs`.`programs_have_announcers` WHERE `programs_have_announcers`.`pha_id` = %d";

    private Date contractBeginDate;
    private Date contractEndDate;
    private Double announcerGratuity;
    private Integer announcerID;
    private Integer programID;

    public Hosting() {
    }

    public Hosting(Integer id, Date contractBegin, Date contractEnd, Double announcerGratuity, Integer announcerID, Integer programID) {
        this.setId(id);
        this.setContractBeginDate(contractBegin);
        this.setContractEndDate(contractEnd);
        this.setAnnouncerGratuity(announcerGratuity);
        this.setAnnouncerID(announcerID);
        this.setProgramID(programID);
    }

    public Date getContractBeginDate() {
        return contractBeginDate;
    }

    public void setContractBeginDate(Date contractBeginDate) {
        this.contractBeginDate = contractBeginDate;
    }

    public Date getContractEndDate() {
        return contractEndDate;
    }

    public void setContractEndDate(Date contractEndDate) {
        this.contractEndDate = contractEndDate;
    }

    public Double getAnnouncerGratuity() {
        return announcerGratuity;
    }

    public void setAnnouncerGratuity(Double announcerGratuity) {
        this.announcerGratuity = announcerGratuity;
    }

    public Integer getAnnouncerID() {
        return announcerID;
    }

    public void setAnnouncerID(Integer announcerID) {
        this.announcerID = announcerID;
    }

    public Integer getProgramID() {
        return programID;
    }

    public void setProgramID(Integer programID) {
        this.programID = programID;
    }

    @Override
    public String toString() {
        return "Hosting{" +
                "contractBeginDate=" + contractBeginDate +
                ", contractEndDate=" + contractEndDate +
                ", announcerGratuity=" + announcerGratuity +
                ", announcerID=" + announcerID +
                ", programID=" + programID +
                '}';
    }

    @Override
    public BaseDAO parseResultSet(ResultSet resultSet) throws SQLException {
        parseData(resultSet.getInt(KEY_ID),
                resultSet.getDate(KEY_CONTRACT_BEGIN),
                resultSet.getDate(KEY_CONTRACT_END),
                resultSet.getDouble(KEY_GRATUITY),
                resultSet.getInt(KEY_ANNOUNCER_ID),
                resultSet.getInt(KEY_PROGRAM_ID));
        return this;
    }

    @Override
    public BaseDAO parseJSON(JSONObject jsonObject) throws JSONException {
        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd");
        try {
            parseData(jsonObject.getInt(KEY_ID),
                    formatter.parse(jsonObject.getString(KEY_CONTRACT_BEGIN)),
                    (!jsonObject.getString(KEY_CONTRACT_END).equals(" ")) ? formatter.parse(jsonObject.getString(KEY_CONTRACT_END)) : formatter.parse("2150-01-01"),
                    jsonObject.getDouble(KEY_GRATUITY),
                    jsonObject.getInt(KEY_ANNOUNCER_ID),
                    jsonObject.getInt(KEY_PROGRAM_ID));
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(KEY_ID, this.getId());
        jsonObject.put(KEY_CONTRACT_BEGIN, formatter.format(this.getContractBeginDate()));
        jsonObject.put(KEY_CONTRACT_END, (this.getContractEndDate() != null) ? formatter.format(this.getContractEndDate()) : " ");
        jsonObject.put(KEY_GRATUITY, this.getAnnouncerGratuity());
        jsonObject.put(KEY_ANNOUNCER_ID, this.getAnnouncerID());
        jsonObject.put(KEY_PROGRAM_ID, this.getProgramID());
        return jsonObject;
    }

    @Override
    public String getObjectName() {
        return "Hosting";
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

    private void parseData(Integer anInt, Date date, Date date2, Double aDouble, Integer anInt2, Integer anInt3) {
        this.setId(anInt);
        this.setContractBeginDate(date);
        this.setContractEndDate(date2);
        this.setAnnouncerGratuity(aDouble);
        this.setAnnouncerID(anInt2);
        this.setProgramID(anInt3);
    }

}
