package bottomPane;

import com.jfoenix.controls.JFXButton;
import dao.Announcer;
import dao.Audience;
import dao.BaseDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import mainPane.MainController;
import rightSidebarPane.BaseTable;
import tablePanes.AnnouncerTablePaneController;
import utils.CustomPane;
import utils.Logger;

import javax.swing.border.Border;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BottomPaneController implements Initializable {
    @FXML
    AnchorPane bottomPane;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnEdit;

    @FXML
    private JFXButton btnDelete;

    @FXML
    void addEntry(MouseEvent event) {
        BaseTable baseTable = MainController.tableLoader.getController();
        BorderPane parent = (BorderPane) bottomPane.getParent().getParent();

        BaseDAO baseDAO = baseTable.getSelectedItem();
        FXMLLoader loader;


        if (baseDAO instanceof Announcer) {
            loader = new FXMLLoader(getClass().getResource("../rightSidebarPane/AnnouncerPane.fxml"));
        } else if (baseDAO instanceof Audience) {
            loader = new FXMLLoader(/*AUDIENCE FXML*/);
        } else
            loader = new FXMLLoader();

        try {
            CustomPane customPane = loader.load();
            customPane.setData(baseDAO);
            parent.setRight(customPane);
        } catch (IOException e) {
            Logger.logError("Error", "We met troubles loading your FXML :(");
            e.printStackTrace();
        }
    }

    @FXML
    void editEntry(MouseEvent event) {

    }

    @FXML
    void deleteEntry(MouseEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
