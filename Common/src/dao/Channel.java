package dao;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Channel extends BaseDAO {
    private static final String SELECT_ALL = "SELECT * FROM channels";

    private Date foundationDate;
    private Date destructionDate;
    private String owner;
    private String logo;
    private String airtime;
    private String description;
    private String frequency;
    private String satellite;

    public Date getFoundationDate() {
        return foundationDate;
    }

    public void setFoundationDate(Date foundationDate) {
        this.foundationDate = foundationDate;
    }

    public Date getDestructionDate() {
        return destructionDate;
    }

    public void setDestructionDate(Date destructionDate) {
        this.destructionDate = destructionDate;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getAirtime() {
        return airtime;
    }

    public void setAirtime(String airtime) {
        this.airtime = airtime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getSatellite() {
        return satellite;
    }

    public void setSatellite(String satellite) {
        this.satellite = satellite;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "foundationDate=" + foundationDate +
                ", destructionDate=" + destructionDate +
                ", owner='" + owner + '\'' +
                ", logo='" + logo + '\'' +
                ", airtime='" + airtime + '\'' +
                ", description='" + description + '\'' +
                ", frequency='" + frequency + '\'' +
                ", satellite='" + satellite + '\'' +
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
    public Channel parseResultSet(ResultSet resultSet) throws SQLException {
        return null;
    }

    @Override
    public Channel parseJSON(JSONObject jsonObject) throws JSONException {
        return null;
    }
}
