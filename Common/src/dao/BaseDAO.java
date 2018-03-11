package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseDAO {
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public interface Parcelable {
        void parseResultSet(ResultSet resultSet) throws SQLException;
    }
}
