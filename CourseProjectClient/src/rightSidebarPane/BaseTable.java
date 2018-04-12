package rightSidebarPane;

import dao.BaseDAO;

import java.util.ArrayList;
import java.util.List;

public interface BaseTable {
    BaseDAO getSelectedItem();
    ArrayList<? extends BaseDAO> getCurrentList();

    void onPostInitialize(Runnable runnable);
    void reloadList();
}
