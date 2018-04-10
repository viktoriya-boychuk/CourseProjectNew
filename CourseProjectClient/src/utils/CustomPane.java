package utils;

import dao.BaseDAO;
import javafx.scene.layout.BorderPane;

public class CustomPane extends BorderPane {
    public enum Type {EDIT, ADD}
    private BaseDAO mObject;
    private Type mType;

    public Type getType() {
        return mType;
    }

    public void setType(Type type) {
        mType = type;
    }

    public BaseDAO getData() {
        return mObject;
    }

    public void setData(BaseDAO baseDAO) {
        mObject = baseDAO;
    }
}
