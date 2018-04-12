package bottomPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import dao.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import mainPane.MainController;
import rightSidebarPane.BaseTable;
import topPane.TopPaneController;
import utils.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.ResourceBundle;

public class BottomPaneController implements Initializable, Receiver {
    @FXML
    HBox bottomPane;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnEdit;

    @FXML
    private JFXButton btnDelete;

    @FXML
    void addEntry(MouseEvent event) {
        openPane(CustomPane.Type.ADD);
    }

    @FXML
    void editEntry(MouseEvent event) {
        openPane(CustomPane.Type.EDIT);
    }

    @FXML
    void deleteEntry(MouseEvent event) {
        deleteCounter++;
        if (deleteCounter == 2) {
            try {
                ServerConnection serverConnection = new ServerConnection(InetAddress.getByName(ServerConnection.DEFAULT_IP), 28365);

                serverConnection.deleteData(((BaseTable) MainController.tableLoader.getController()).getSelectedItem(), this);
                ((BaseTable) MainController.tableLoader.getController()).reloadList();

                snackbar.show("Запис успішно видалено!", 2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else snackbar.show("Ви впевнені, що бажаєте видалити запис?\nЯкщо так, натисніть ще раз \"Видалити\"!", 3000);
    }

    private JFXSnackbar snackbar;
    private int deleteCounter;

    private void openPane(CustomPane.Type type) {
        BaseTable baseTable = MainController.tableLoader.getController();
        BorderPane parent = (BorderPane) bottomPane.getParent().getParent();

        BaseDAO baseDAO = baseTable.getSelectedItem();
        FXMLLoader loader;

        if (baseDAO instanceof Announcer)
            loader = new FXMLLoader(getClass().getResource("../rightSidebarPane/AnnouncerPane.fxml"));
        else if (baseDAO instanceof Audience)
            loader = new FXMLLoader(getClass().getResource("../rightSidebarPane/AudiencePane.fxml"));
        else if (baseDAO instanceof Channel)
            loader = new FXMLLoader(getClass().getResource("../rightSidebarPane/ChannelPane.fxml"));
        else if (baseDAO instanceof Hosting)
            loader = new FXMLLoader(getClass().getResource("../rightSidebarPane/HostingPane.fxml"));
        else if (baseDAO instanceof Program)
            loader = new FXMLLoader(getClass().getResource("../rightSidebarPane/ProgramPane.fxml"));
        else if (baseDAO instanceof Schedule)
            loader = new FXMLLoader(getClass().getResource("../rightSidebarPane/SchedulePane.fxml"));
        else
            loader = new FXMLLoader();

        try {
            CustomPane customPane = loader.load();
            customPane.setData(baseDAO);
            customPane.setType(type);
            parent.setRight(customPane);
        } catch (IOException e) {
            Logger.logError("Error", "We met troubles loading your FXML :(");
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        snackbar = new JFXSnackbar(TopPaneController.getBottomPanel());
        deleteCounter = 0;
        btnAdd.setTooltip(new Tooltip("Додати запис"));
        btnEdit.setTooltip(new Tooltip("Редагувати запис"));
        btnDelete.setTooltip(new Tooltip("Видалити запис"));
    }

    @Override
    public void onReceive(Protocol request) {

    }
}
