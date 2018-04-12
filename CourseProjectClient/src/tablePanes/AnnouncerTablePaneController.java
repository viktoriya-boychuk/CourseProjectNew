package tablePanes;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dao.Announcer;
import dao.BaseDAO;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import rightSidebarPane.BaseTable;
import utils.Protocol;
import utils.Receiver;
import utils.ServerConnection;
import wrappedDAO.AnnouncerWrapped;
import wrappedDAO.AudienceWrapped;

import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AnnouncerTablePaneController implements Initializable, Receiver, BaseTable {
    @FXML
    JFXTreeTableView<AnnouncerWrapped> announcerTable;

    private JFXTreeTableColumn<AnnouncerWrapped, Integer> idColumn;
    private JFXTreeTableColumn<AnnouncerWrapped, String> nameColumn;
    private JFXTreeTableColumn<AnnouncerWrapped, String> careerBeginYearColumn;
    private JFXTreeTableColumn<AnnouncerWrapped, String> careerEndYearColumn;
    private JFXTreeTableColumn<AnnouncerWrapped, String> birthDateColumn;
    private JFXTreeTableColumn<AnnouncerWrapped, String> sexColumn;
    private JFXTreeTableColumn<AnnouncerWrapped, String> educationColumn;
    private JFXTreeTableColumn<AnnouncerWrapped, String> descriptionColumn;

    private static ObservableList<AudienceWrapped> mWrappedAnnouncers;

    public static ObservableList<AudienceWrapped> getWrappedData() {
        return mWrappedAnnouncers;
    }

    private ArrayList<? extends BaseDAO> mAnnouncers;

    private ServerConnection mServerConnection;

    private static Announcer mSelectedAnnouncer;

    public static Announcer getSelectedAnnouncer() {
        return mSelectedAnnouncer;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idColumn = new JFXTreeTableColumn<>("№");
        idColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<AnnouncerWrapped, Integer> param) -> {
            if (idColumn.validateValue(param)) return param.getValue().getValue().idProperty().asObject();
            else return idColumn.getComputedValue(param);
        });

        nameColumn = new JFXTreeTableColumn<>("ПІБ");
        nameColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<AnnouncerWrapped, String> param) -> {
            if (nameColumn.validateValue(param)) return param.getValue().getValue().nameProperty();
            else return nameColumn.getComputedValue(param);
        });

        careerBeginYearColumn = new JFXTreeTableColumn<>("Початок кар'єри");
        careerBeginYearColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<AnnouncerWrapped, String> param) -> {
            if (careerBeginYearColumn.validateValue(param))
                return param.getValue().getValue().careerBeginYearProperty();
            else return careerBeginYearColumn.getComputedValue(param);
        });

        careerEndYearColumn = new JFXTreeTableColumn<>("Кінець кар'єри");
        careerEndYearColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<AnnouncerWrapped, String> param) -> {
            if (careerEndYearColumn.validateValue(param)) {
                StringProperty value = param.getValue().getValue().careerEndYearProperty();
                if(value.get().equals("0")) {
                    return new SimpleStringProperty("");
                } else return value;
            }
            else return careerEndYearColumn.getComputedValue(param);
        });

        birthDateColumn = new JFXTreeTableColumn<>("Дата народження");
        birthDateColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<AnnouncerWrapped, String> param) -> {
            if (birthDateColumn.validateValue(param)) return param.getValue().getValue().birthDateProperty();
            else return birthDateColumn.getComputedValue(param);
        });

        sexColumn = new JFXTreeTableColumn<>("Стать");
        sexColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<AnnouncerWrapped, String> param) -> {
            if (sexColumn.validateValue(param)) {
                StringProperty value = param.getValue().getValue().sexProperty();
                if (value.get().equals("MALE")) return new SimpleStringProperty("чоловіча");
                else
                return new SimpleStringProperty("жіноча");
            }
            else return sexColumn.getComputedValue(param);
        });

        educationColumn = new JFXTreeTableColumn<>("Освіта");
        educationColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<AnnouncerWrapped, String> param) -> {
            if (educationColumn.validateValue(param)) return param.getValue().getValue().educationProperty();
            else return educationColumn.getComputedValue(param);
        });

        descriptionColumn = new JFXTreeTableColumn<>("Характеристика");
        descriptionColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<AnnouncerWrapped, String> param) -> {
            if (descriptionColumn.validateValue(param)) return param.getValue().getValue().descriptionProperty();
            else return descriptionColumn.getComputedValue(param);
        });

        reloadList();

        announcerTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null)
                mSelectedAnnouncer = newSelection.getValue().getAnnouncer();
        });
    }

    @Override
    public Announcer getSelectedItem() {
        return announcerTable.getSelectionModel().getSelectedItem().getValue().getAnnouncer();
    }

    @Override
    public ArrayList<? extends BaseDAO> getCurrentList() {
        return mAnnouncers;
    }

    @Override
    public void onPostInitialize(Runnable runnable) {
        Platform.runLater(runnable);
    }

    @Override
    public void reloadList() {
        try {
            mServerConnection = new ServerConnection(
                    InetAddress.getByName(
                            ServerConnection.DEFAULT_IP),
                    ServerConnection.DEFAULT_PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mServerConnection.requestData(Announcer.class, this);
    }

    @Override
    public void onReceive(Protocol request) {
        Platform.runLater(() -> {
            ObservableList<AnnouncerWrapped> announcers = null;
            try {
                mAnnouncers = request.getData();
                announcers = FXCollections.observableArrayList(AnnouncerWrapped.wrap(request.getData()));
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
            // build tree
            final TreeItem<AnnouncerWrapped> root = new RecursiveTreeItem<>(announcers, RecursiveTreeObject::getChildren);

            announcerTable.setRoot(root);
            announcerTable.setShowRoot(false);
            announcerTable.setEditable(false);
            announcerTable.getColumns().setAll(idColumn, nameColumn, sexColumn, birthDateColumn, careerBeginYearColumn, careerEndYearColumn, educationColumn, descriptionColumn);

            onPostInitialize(() -> {
                announcerTable.getSelectionModel().select(0);
            });
        });
    }
}
