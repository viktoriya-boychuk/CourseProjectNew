package utils;

import dao.BaseDAO;
import javafx.scene.layout.BorderPane;

public class CustomPane extends BorderPane {
    private BaseDAO mObject;

    public BaseDAO getData() {
        return mObject;
    }

    public void setData(BaseDAO baseDAO) {
        mObject = baseDAO;
    }
}
