package tablePanes;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dao.Audience;
import dao.BaseDAO;
import javafx.application.Platform;
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
import wrappedDAO.AudienceWrapped;

import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AudienceTablePaneController implements Initializable, Receiver, BaseTable {
    @FXML
    JFXTreeTableView<AudienceWrapped> audienceTable;

    private JFXTreeTableColumn<AudienceWrapped, Integer> idColumn;
    private JFXTreeTableColumn<AudienceWrapped, String> nameColumn;
    private JFXTreeTableColumn<AudienceWrapped, String> ageCategoryColumn;
    private JFXTreeTableColumn<AudienceWrapped, String> descriptionColumn;
    private JFXTreeTableColumn<AudienceWrapped, String> emblemColumn;

    private ObservableList<AudienceWrapped> mWrappedAudiences;

    private ArrayList<? extends BaseDAO> mAudience;

    private ServerConnection mServerConnection;

    private static Audience mSelectedAudience;

    public static Audience getSelectedAnnouncer() {
        return mSelectedAudience;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            mServerConnection = new ServerConnection(
                    InetAddress.getByName(
                            ServerConnection.DEFAULT_IP),
                    ServerConnection.DEFAULT_PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mServerConnection.requestData(Audience.class, this);

        idColumn = new JFXTreeTableColumn<>("№");
        idColumn.setCellValueFactory((
                TreeTableColumn.CellDataFeatures<AudienceWrapped, Integer> param) -> {
            if (idColumn.validateValue(param)) return param.getValue().getValue().idProperty().asObject();
            else return idColumn.getComputedValue(param);
        });

        nameColumn = new JFXTreeTableColumn<>("Назва");
        nameColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<AudienceWrapped, String> param) -> {
            if (nameColumn.validateValue(param)) return param.getValue().getValue().nameProperty();
            else return nameColumn.getComputedValue(param);
        });

        ageCategoryColumn = new JFXTreeTableColumn<>("Вікова категорія");
        ageCategoryColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<AudienceWrapped, String> param) -> {
            if (ageCategoryColumn.validateValue(param)) return param.getValue().getValue().ageCategoryProperty();
            else return ageCategoryColumn.getComputedValue(param);
        });

        descriptionColumn = new JFXTreeTableColumn<>("Опис");
        descriptionColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<AudienceWrapped, String> param) -> {
            if (descriptionColumn.validateValue(param)) return param.getValue().getValue().descriptionProperty();
            else return descriptionColumn.getComputedValue(param);
        });

        reloadList();

        audienceTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null)
                mSelectedAudience = newSelection.getValue().getAudience();
        });
    }

    @Override
    public Audience getSelectedItem() {
        return audienceTable.getSelectionModel().getSelectedItem().getValue().getAudience();
    }

    @Override
    public ArrayList<? extends BaseDAO> getCurrentList() {
        return mAudience;
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
        mServerConnection.requestData(Audience.class, this);
    }

    @Override
    public void onReceive(Protocol request) {
        Platform.runLater(() -> {
            ObservableList<AudienceWrapped> announcers = null;
            try {
                mAudience = request.getData();
                announcers = FXCollections.observableArrayList(AudienceWrapped.wrap(request.getData()));
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
            // build tree
            final TreeItem<AudienceWrapped> root = new RecursiveTreeItem<>(announcers, RecursiveTreeObject::getChildren);

            audienceTable.setRoot(root);
            audienceTable.setShowRoot(false);
            audienceTable.setEditable(false);
            audienceTable.getColumns().setAll(idColumn, nameColumn, ageCategoryColumn, descriptionColumn);

            onPostInitialize(() -> {
                audienceTable.getSelectionModel().select(0);
            });
        });
    }
}

