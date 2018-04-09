package tablePanes;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dao.Announcer;
import dao.Audience;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import utils.Logger;
import utils.Protocol;
import utils.Receiver;
import utils.ServerConnection;
import wrappedDAO.AnnouncerWrapped;
import wrappedDAO.AudienceWrapped;

import java.net.InetAddress;
import java.net.URL;
import java.util.ResourceBundle;

public class AnnouncerTablePaneController implements Initializable, Receiver {
    @FXML
    JFXTreeTableView<AnnouncerWrapped> announcerTable;

    private JFXTreeTableColumn<AnnouncerWrapped, Integer> idColumn;
    private JFXTreeTableColumn<AnnouncerWrapped, String> nameColumn;
    private JFXTreeTableColumn<AnnouncerWrapped, Integer> careerBeginYearColumn;
    private JFXTreeTableColumn<AnnouncerWrapped, Integer> careerEndYearColumn;
    private JFXTreeTableColumn<AnnouncerWrapped, String> birthDateColumn;
    private JFXTreeTableColumn<AnnouncerWrapped, String> educationColumn;
    private JFXTreeTableColumn<AnnouncerWrapped, String> descriptionColumn;

    private static ObservableList<AudienceWrapped> mWrappedAnnouncers;

    public static ObservableList<AudienceWrapped> getWrappedData() {
        return mWrappedAnnouncers;
    }

    private ServerConnection mServerConnection;

    private static Announcer mSelectedAnnouncer;

    public static Announcer getSelectedAnnouncer() {
        return mSelectedAnnouncer;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idColumn = new JFXTreeTableColumn<>("ID");
        idColumn.setPrefWidth(150);
        idColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<AnnouncerWrapped, Integer> param) -> {
            if (idColumn.validateValue(param)) return param.getValue().getValue().idProperty().asObject();
            else return idColumn.getComputedValue(param);
        });

        nameColumn = new JFXTreeTableColumn<>("Name");
        nameColumn.setPrefWidth(150);
        nameColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<AnnouncerWrapped, String> param) -> {
            if (nameColumn.validateValue(param)) return param.getValue().getValue().nameProperty();
            else return nameColumn.getComputedValue(param);
        });

        careerBeginYearColumn = new JFXTreeTableColumn<>("Career Started");
        careerBeginYearColumn.setPrefWidth(150);
        careerBeginYearColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<AnnouncerWrapped, Integer> param) -> {
            if (careerBeginYearColumn.validateValue(param))
                return param.getValue().getValue().careerBeginYearProperty().asObject();
            else return careerBeginYearColumn.getComputedValue(param);
        });

        careerEndYearColumn = new JFXTreeTableColumn<>("Career Ended");
        careerEndYearColumn.setPrefWidth(150);
        careerEndYearColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<AnnouncerWrapped, Integer> param) -> {
            if (careerEndYearColumn.validateValue(param))
                return param.getValue().getValue().careerEndYearProperty().asObject();
            else return careerEndYearColumn.getComputedValue(param);
        });

        birthDateColumn = new JFXTreeTableColumn<>("Birth Date");
        birthDateColumn.setPrefWidth(150);
        birthDateColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<AnnouncerWrapped, String> param) -> {
            if (birthDateColumn.validateValue(param)) return param.getValue().getValue().birthDateProperty();
            else return birthDateColumn.getComputedValue(param);
        });

        educationColumn = new JFXTreeTableColumn<>("Education");
        educationColumn.setPrefWidth(150);
        educationColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<AnnouncerWrapped, String> param) -> {
            if (educationColumn.validateValue(param)) return param.getValue().getValue().educationProperty();
            else return educationColumn.getComputedValue(param);
        });

        descriptionColumn = new JFXTreeTableColumn<>("Description");
        descriptionColumn.setPrefWidth(150);
        descriptionColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<AnnouncerWrapped, String> param) -> {
            if (descriptionColumn.validateValue(param)) return param.getValue().getValue().descriptionProperty();
            else return descriptionColumn.getComputedValue(param);
        });

        try {
            mServerConnection = new ServerConnection(
                    InetAddress.getByName(
                            ServerConnection.DEFAULT_IP),
                    ServerConnection.DEFAULT_PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mServerConnection.requestData(Announcer.class, this);

        announcerTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null)
                mSelectedAnnouncer = newSelection.getValue().getAnnouncer();
        });
    }

    public Announcer getSelectedItem() {
        return announcerTable.getSelectionModel().getSelectedItem().getValue().getAnnouncer();
    }

    @Override
    public void onReceive(Protocol request) {
        Platform.runLater(() -> {
            ObservableList<AnnouncerWrapped> announcers = null;
            try {
                announcers = FXCollections.observableArrayList(AnnouncerWrapped.wrap(request.getData()));
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
            // build tree
            final TreeItem<AnnouncerWrapped> root = new RecursiveTreeItem<>(announcers, RecursiveTreeObject::getChildren);

            announcerTable.setRoot(root);
            announcerTable.setShowRoot(false);
            announcerTable.setEditable(false);
            announcerTable.getColumns().setAll(idColumn, nameColumn, careerBeginYearColumn, careerEndYearColumn, birthDateColumn, educationColumn, descriptionColumn);
        });
    }
}
