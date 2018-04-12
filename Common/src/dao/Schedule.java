package dao;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Schedule extends BaseDAO {
    private static final String KEY_ID = "sc_id";
    private static final String KEY_DATE = "sc_date";
    private static final String KEY_TIME = "sc_time";
    private static final String KEY_CHANNEL_ID = "sc_ch_id";
    private static final String KEY_PROGRAM_ID = "sc_pr_id";

    private static final String SELECT_ALL = "SELECT * FROM schedule";
    private static final String DELETE = "DELETE FROM `tv_programs`.`schedule` WHERE `schedule`.`sc_id` = %d";

    private Date date;
    private Time time;
    private Integer channelID;
    private Integer programID;

    public Schedule() {
    }

    public Schedule(Integer id, Date date, Time time, Integer channelID, Integer programID) {
        this.setId(id);
        this.date = date;
        this.time = time;
        this.channelID = channelID;
        this.programID = programID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Integer getChannelID() {
        return channelID;
    }

    public void setChannelID(Integer channelID) {
        this.channelID = channelID;
    }

    public Integer getProgramID() {
        return programID;
    }

    public void setProgramID(Integer programID) {
        this.programID = programID;
    }


    @Override
    public String toString() {
        return "Schedule{" +
                "date=" + date +
                ", time=" + time +
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
        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd");
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(KEY_ID, this.getId());
        jsonObject.put(KEY_DATE, formatter.format(this.getDate()));
        jsonObject.put(KEY_TIME, format.format(this.getTime()));
        jsonObject.put(KEY_CHANNEL_ID, this.getChannelID());
        jsonObject.put(KEY_PROGRAM_ID, this.getProgramID());
        return jsonObject;
    }

    @Override
    public String getObjectName() {
        return "Schedule";
    }

    @Override
    public Schedule parseResultSet(ResultSet resultSet) throws SQLException {
        parseData(resultSet.getInt(KEY_ID),
                resultSet.getDate(KEY_DATE),
                resultSet.getTime(KEY_TIME),
                resultSet.getInt(KEY_CHANNEL_ID),
                resultSet.getInt(KEY_PROGRAM_ID));
        return this;
    }

    @Override
    public Schedule parseJSON(JSONObject jsonObject) throws JSONException {
        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd");
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        try {
            parseData(jsonObject.getInt(KEY_ID),
                    formatter.parse(jsonObject.getString(KEY_DATE)),
                    new Time(format.parse(jsonObject.getString(KEY_TIME)).getTime()),
                    jsonObject.getInt(KEY_CHANNEL_ID),
                    jsonObject.getInt(KEY_PROGRAM_ID));
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return this;
    }

    private void parseData(int anInt, Date date, Time time, int anInt2, int anInt3) {
        this.setId(anInt);
        this.setDate(date);
        this.setTime(time);
        this.setChannelID(anInt2);
        this.setProgramID(anInt3);
    }
}
