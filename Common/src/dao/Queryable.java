package dao;

public interface Queryable {
    String getSelectAllQuery();
    String getInsertQuery();
    String getDeleteQuery();
    String getUpdateQuery();
}
