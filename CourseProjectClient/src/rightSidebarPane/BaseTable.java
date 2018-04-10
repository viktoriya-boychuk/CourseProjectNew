package rightSidebarPane;

import dao.BaseDAO;

public interface BaseTable {
    BaseDAO getSelectedItem();
    void onPostInitialize(Runnable runnable);
}
