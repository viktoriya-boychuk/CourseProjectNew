package dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

public class Schedule extends BaseDAO{
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
    public void parseResultSet(ResultSet resultSet) throws SQLException {

    }
}
