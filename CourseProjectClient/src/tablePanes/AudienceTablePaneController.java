package tablePanes;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dao.Audience;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import utils.Protocol;
import utils.Receiver;
import utils.ServerConnection;
import wrappedDAO.AudienceWrapped;

import java.net.InetAddress;
import java.net.URL;
import java.util.ResourceBundle;

public class AudienceTablePaneController implements Initializable, Receiver {
    @FXML
    JFXTreeTableView<AudienceWrapped> audienceTable;

    private JFXTreeTableColumn<AudienceWrapped, Integer> idColumn;
    private JFXTreeTableColumn<AudienceWrapped, String> nameColumn;
    private JFXTreeTableColumn<AudienceWrapped, String> ageCategoryColumn;
    private JFXTreeTableColumn<AudienceWrapped, String> descriptionColumn;
    private JFXTreeTableColumn<AudienceWrapped, String> emblemColumn;

    private ObservableList<AudienceWrapped> mWrappedAudiences;

    private ServerConnection mServerConnection;

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

        idColumn = new JFXTreeTableColumn<>("ID");
        idColumn.setPrefWidth(100);
        idColumn.setCellValueFactory((
                TreeTableColumn.CellDataFeatures<AudienceWrapped, Integer> param) -> {
            if (idColumn.validateValue(param)) return param.getValue().getValue().idProperty().asObject();
            else return idColumn.getComputedValue(param);
        });

        nameColumn = new JFXTreeTableColumn<>("Name");
        nameColumn.setPrefWidth(200);
        nameColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<AudienceWrapped, String> param) -> {
            if (nameColumn.validateValue(param)) return param.getValue().getValue().nameProperty();
            else return nameColumn.getComputedValue(param);
        });

        ageCategoryColumn = new JFXTreeTableColumn<>("Age Category");
        ageCategoryColumn.setPrefWidth(200);
        ageCategoryColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<AudienceWrapped, String> param) -> {
            if (ageCategoryColumn.validateValue(param)) return param.getValue().getValue().ageCategoryProperty();
            else return ageCategoryColumn.getComputedValue(param);
        });

        descriptionColumn = new JFXTreeTableColumn<>("Description");
        descriptionColumn.setPrefWidth(200);
        descriptionColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<AudienceWrapped, String> param) -> {
            if (descriptionColumn.validateValue(param)) return param.getValue().getValue().descriptionProperty();
            else return descriptionColumn.getComputedValue(param);
        });

        emblemColumn = new JFXTreeTableColumn<>("Emblem");
        emblemColumn.setPrefWidth(200);
        emblemColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<AudienceWrapped, String> param) -> {
            if (emblemColumn.validateValue(param)) return param.getValue().getValue().emblemProperty();
            else return emblemColumn.getComputedValue(param);
        });
    }

    public Audience getSelectedItem() {
        return audienceTable.getSelectionModel().getSelectedItem().getValue().getAudience();
    }

    @Override
    public void onReceive(Protocol request) {
        Platform.runLater(() -> {
            ObservableList<AudienceWrapped> announcers = null;
            try {
                announcers = FXCollections.observableArrayList(AudienceWrapped.wrap(request.getData()));
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
            // build tree
            final TreeItem<AudienceWrapped> root = new RecursiveTreeItem<>(announcers, RecursiveTreeObject::getChildren);

            audienceTable.setRoot(root);
            audienceTable.setShowRoot(false);
            audienceTable.setEditable(false);
            audienceTable.getColumns().setAll(idColumn, nameColumn, ageCategoryColumn, descriptionColumn, emblemColumn);

            JFXTextField filterField = new JFXTextField();
            Label size = new Label();
        });
    }
}

