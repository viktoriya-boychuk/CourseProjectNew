package mainPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dao.Announcer;
import dao.BaseDAO;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private BorderPane mainPane;

    @FXML
    private BorderPane centralPane;

    @FXML
    private JFXTreeTableView mTreeTable;

    @FXML
    JFXButton buttonAudiencesTable;

    @FXML
    JFXButton buttonAnnouncersTable;

    @FXML
    JFXButton buttonChannelsTable;

    public static JFXTreeTableView currentTable;

    public static FXMLLoader tableLoader;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        VBox top;
        VBox sidebar;
        BorderPane right;
        AnchorPane bottom;
        try {
            top = FXMLLoader.load(getClass().getResource("../topPane/TopPane.fxml"));
            mainPane.setTop(top);
            sidebar = FXMLLoader.load(getClass().getResource("../leftSidebarPane/LeftSidebarPane.fxml"));
            mainPane.setLeft(sidebar);
//            right = FXMLLoader.load(getClass().getResource("../rightSidebarPane/AnnouncerPane.fxml"));
//            mainPane.setRight(right);

            tableLoader = new FXMLLoader(getClass().getResource("../tablePanes/AnnouncerTablePane.fxml"));
            mTreeTable = tableLoader.load();

            //AnnouncerTablePaneController announcerTablePaneController = ((AnnouncerTablePaneController) tableLoader.getController());
            //announcerTablePaneController.getSelectedItem();

            centralPane.setCenter(mTreeTable);
            currentTable = mTreeTable;

            bottom = FXMLLoader.load(getClass().getResource("../bottomPane/BottomPane.fxml"));
            centralPane.setBottom(bottom);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void audiencesButtonOnClick(MouseEvent event) {
        try {
            mTreeTable = FXMLLoader.load(getClass().getResource("../tablePanes/AudienceTablePane.fxml"));
            centralPane.setCenter(mTreeTable);
            currentTable = mTreeTable;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void announcersButtonOnClick(MouseEvent event) {
        try {
            mTreeTable = FXMLLoader.load(getClass().getResource("../tablePanes/AnnouncerTablePane.fxml"));
            centralPane.setCenter(mTreeTable);
            currentTable = mTreeTable;

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void channelsButtonOnClick(MouseEvent event) {
        try {
            mTreeTable = FXMLLoader.load(getClass().getResource("../tablePanes/ChannelTablePane.fxml"));
            centralPane.setCenter(mTreeTable);
            currentTable = mTreeTable;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void hostingsButtonOnClick(MouseEvent event) {
        try {
            mTreeTable = FXMLLoader.load(getClass().getResource("../tablePanes/HostingTablePane.fxml"));
            centralPane.setCenter(mTreeTable);
            currentTable = mTreeTable;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void programButtonOnClick(MouseEvent event) {
        try {
            mTreeTable = FXMLLoader.load(getClass().getResource("../tablePanes/ProgramTablePane.fxml"));
            centralPane.setCenter(mTreeTable);
            currentTable = mTreeTable;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
