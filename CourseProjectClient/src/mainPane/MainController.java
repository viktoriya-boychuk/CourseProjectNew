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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        VBox top;
        VBox sidebar;
        BorderPane right;
        try {
            top = FXMLLoader.load(getClass().getResource("../topPane/TopPane.fxml"));
            mainPane.setTop(top);
            sidebar = FXMLLoader.load(getClass().getResource("../leftSidebarPane/LeftSidebarPane.fxml"));
            mainPane.setLeft(sidebar);
//            right = FXMLLoader.load(getClass().getResource("../rightSidebarPane/RightSidebarPane.fxml"));
//            mainPane.setRight(right);

            mTreeTable = FXMLLoader.load(getClass().getResource("../tablePanes/AnnouncerTablePane.fxml"));
            centralPane.setCenter(mTreeTable);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void audiencesButtonOnClick(MouseEvent event) {
        try {
            mTreeTable = FXMLLoader.load(getClass().getResource("../tablePanes/AudienceTablePane.fxml"));
            centralPane.setCenter(mTreeTable);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void announcersButtonOnClick(MouseEvent event) {
        try {
            mTreeTable = FXMLLoader.load(getClass().getResource("../tablePanes/AnnouncerTablePane.fxml"));
            centralPane.setCenter(mTreeTable);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void channelsButtonOnClick(MouseEvent event) {
        try {
            mTreeTable = FXMLLoader.load(getClass().getResource("../tablePanes/ChannelTablePane.fxml"));
            centralPane.setCenter(mTreeTable);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class AnnouncerWrapped extends BaseWrapped<AnnouncerWrapped> {
    StringProperty education;

    public AnnouncerWrapped(Announcer announcer) {
        super(announcer);
        this.education = new SimpleStringProperty(announcer.getEducation());
    }

    public static ObservableList<AnnouncerWrapped> wrap(ArrayList<? extends BaseDAO> announcers) {
        List<AnnouncerWrapped> wrappedAnnouncers = new ArrayList<>();
        for (BaseDAO announcer : announcers) {
            AnnouncerWrapped wrappedAnnouncer = new AnnouncerWrapped((Announcer) announcer);
            wrappedAnnouncers.add(wrappedAnnouncer);
        }
        return FXCollections.observableArrayList(wrappedAnnouncers);
    }
}

class BaseWrapped<T> extends RecursiveTreeObject<T> {
    IntegerProperty id;
    StringProperty name;

    public BaseWrapped(BaseDAO baseDAO) {
        this.id = new SimpleIntegerProperty(baseDAO.getId());
        this.name = new SimpleStringProperty(baseDAO.getName());
    }
}
