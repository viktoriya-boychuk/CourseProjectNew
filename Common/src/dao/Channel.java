package dao;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class Channel extends BaseDAO {
    private static final String KEY_ID = "ch_id";
    private static final String KEY_NAME = "ch_name";
    private static final String KEY_FOUNDATION = "ch_foundation_date";
    private static final String KEY_DESTRUCTION = "ch_destruction_date";
    private static final String KEY_OWNER = "ch_owner";
    private static final String KEY_LOGO = "ch_logo";
    private static final String KEY_AIRTIME = "ch_airtime";
    private static final String KEY_CITY = "ch_city";
    private static final String KEY_DESCRIPTION = "ch_description";
    private static final String KEY_FREQUENCY = "ch_frequency";
    private static final String KEY_SATELLITE = "ch_satellite";

    private static final String SELECT_ALL = "SELECT * FROM channels";
    private static final String DELETE = "DELETE FROM `tv_programs`.`channels` WHERE `channels`.`ch_id` = %d";

    private Date foundationDate;
    private Date destructionDate;
    private String owner;
    private String logo;
    private String airtime;
    private String city;
    private String description;
    private String frequency;
    private String satellite;

    public Channel() {
    }

    public Channel(Integer id, String name, Date foundationDate, Date destructionDate, String owner, String logo, String airtime, String city, String description, String frequency, String satellite) {
        this.setId(id);
        this.setName(name);
        this.foundationDate = foundationDate;
        this.destructionDate = destructionDate;
        this.owner = owner;
        this.logo = logo;
        this.airtime = airtime;
        this.city = city;
        this.description = description;
        this.frequency = frequency;
        this.satellite = satellite;
    }

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(KEY_ID, this.getId());
        jsonObject.put(KEY_NAME, this.getName());
        jsonObject.put(KEY_FOUNDATION, formatter.format(this.getFoundationDate()));
        jsonObject.put(KEY_DESTRUCTION, (this.getDestructionDate() != null) ? formatter.format(this.getDestructionDate()): " ");
        jsonObject.put(KEY_OWNER, this.getOwner());
        jsonObject.put(KEY_LOGO, this.getLogo());
        jsonObject.put(KEY_AIRTIME, this.getAirtime());
        jsonObject.put(KEY_CITY, this.getCity());
        jsonObject.put(KEY_DESCRIPTION, (this.getDescription() != null) ? this.getDescription() : " ");
        jsonObject.put(KEY_FREQUENCY, this.getFrequency());
        jsonObject.put(KEY_SATELLITE, this.getSatellite());
        return jsonObject;
    }

    @Override
    public String getObjectName() {
        return "Channel";
    }

    @Override
    public Channel parseResultSet(ResultSet resultSet) throws SQLException {
        Blob image = resultSet.getBlob(KEY_LOGO);
        parseData(resultSet.getInt(KEY_ID),
                resultSet.getString(KEY_NAME),
                resultSet.getDate(KEY_FOUNDATION),
                resultSet.getDate(KEY_DESTRUCTION),
                resultSet.getString(KEY_OWNER),
                Base64.getEncoder().encodeToString(image.getBytes(1, (int) image.length())),
                resultSet.getString(KEY_AIRTIME),
                resultSet.getString(KEY_CITY),
                resultSet.getString(KEY_DESCRIPTION),
                resultSet.getString(KEY_FREQUENCY),
                resultSet.getString(KEY_SATELLITE));
        return this;
    }

    @Override
    public Channel parseJSON(JSONObject jsonObject) throws JSONException {
        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd");
        try {
            parseData(jsonObject.getInt(KEY_ID),
                    jsonObject.getString(KEY_NAME),
                    formatter.parse(jsonObject.getString(KEY_FOUNDATION)),
                    (!jsonObject.getString(KEY_DESTRUCTION).equals(" ")) ? formatter.parse(jsonObject.getString(KEY_DESTRUCTION)) : formatter.parse("2150-01-01"),
                    jsonObject.getString(KEY_OWNER),
                    jsonObject.getString(KEY_LOGO),
                    jsonObject.getString(KEY_AIRTIME),
                    jsonObject.getString(KEY_CITY),
                    jsonObject.getString(KEY_DESCRIPTION),
                    jsonObject.getString(KEY_FREQUENCY),
                    jsonObject.getString(KEY_SATELLITE));
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return this;
    }

    private void parseData(int anInt, String string, Date date, Date date2, String string2, String image, String string4, String string5, String string6, String string7, String string8) {
        this.setId(anInt);
        this.setName(string);
        this.setFoundationDate(date);
        this.setDestructionDate(date2);
        this.setOwner(string2);
        this.setLogo(image);
        this.setAirtime(string4);
        this.setCity(string5);
        this.setDescription(string6);
        this.setFrequency(string7);
        this.setSatellite(string8);
    }
}
