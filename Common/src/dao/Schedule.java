package dao;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

public class Schedule extends BaseDAO {
    private static final String SELECT_ALL = "SELECT * FROM schedule";

    private Date date;
    private Time time;

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
    public JSONObject toJSON() throws JSONException {
        return null;
    }

    @Override
    public Schedule parseResultSet(ResultSet resultSet) throws SQLException {
        return null;
    }

    @Override
    public Schedule parseJSON(JSONObject jsonObject) throws JSONException {
        return null;
    }
}
