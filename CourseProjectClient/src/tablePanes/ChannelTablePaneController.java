package tablePanes;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dao.Announcer;
import dao.Channel;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import utils.Protocol;
import utils.Receiver;
import utils.ServerConnection;
import wrappedDAO.AnnouncerWrapped;
import wrappedDAO.ChannelWrapped;

import java.net.InetAddress;
import java.net.URL;
import java.util.ResourceBundle;

public class ChannelTablePaneController implements Initializable, Receiver {
    @FXML
    JFXTreeTableView<ChannelWrapped> channelTable;

    private JFXTreeTableColumn<ChannelWrapped, Integer> idColumn;
    private JFXTreeTableColumn<ChannelWrapped, String> nameColumn;
    private JFXTreeTableColumn<ChannelWrapped, String> foundationDateColumn;
    private JFXTreeTableColumn<ChannelWrapped, String> destructionDateColumn;
    private JFXTreeTableColumn<ChannelWrapped, String> ownerColumn;
    private JFXTreeTableColumn<ChannelWrapped, String> logoColumn;
    private JFXTreeTableColumn<ChannelWrapped, String> airtimeColumn;
    private JFXTreeTableColumn<ChannelWrapped, String> cityColumn;
    private JFXTreeTableColumn<ChannelWrapped, String> descriptionColumn;
    private JFXTreeTableColumn<ChannelWrapped, String> frequencyColumn;
    private JFXTreeTableColumn<ChannelWrapped, String> satelliteColumn;

    private ObservableList<ChannelWrapped> mWrappedChannels;

    private ServerConnection mServerConnection;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idColumn = new JFXTreeTableColumn<>("ID");
        idColumn.setPrefWidth(150);
        idColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<ChannelWrapped, Integer> param) -> {
            if (idColumn.validateValue(param))
                return param.getValue().getValue().idProperty().asObject();
            else return idColumn.getComputedValue(param);
        });
        nameColumn = new JFXTreeTableColumn<>("Name");
        nameColumn.setPrefWidth(150);
        nameColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<ChannelWrapped, String> param) -> {
            if (nameColumn.validateValue(param))
                return param.getValue().getValue().nameProperty();
            else return nameColumn.getComputedValue(param);
        });
        foundationDateColumn = new JFXTreeTableColumn<>("Found. Date");
        foundationDateColumn.setPrefWidth(150);
        foundationDateColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<ChannelWrapped, String> param) -> {
            if (foundationDateColumn.validateValue(param))
                return param.getValue().getValue().foundationDateProperty();
            else return foundationDateColumn.getComputedValue(param);
        });
        destructionDateColumn = new JFXTreeTableColumn<>("Disc. Date");
        destructionDateColumn.setPrefWidth(150);
        destructionDateColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<ChannelWrapped, String> param) -> {
            if (destructionDateColumn.validateValue(param))
                return param.getValue().getValue().destructionDateProperty();
            else return destructionDateColumn.getComputedValue(param);
        });
        ownerColumn = new JFXTreeTableColumn<>("Owner");
        ownerColumn.setPrefWidth(150);
        ownerColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<ChannelWrapped, String> param) -> {
            if (ownerColumn.validateValue(param))
                return param.getValue().getValue().ownerProperty();
            else return ownerColumn.getComputedValue(param);
        });
        logoColumn = new JFXTreeTableColumn<>("Logo");
        logoColumn.setPrefWidth(150);
        logoColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<ChannelWrapped, String> param) -> {
            if (logoColumn.validateValue(param))
                return param.getValue().getValue().logoProperty();
            else return logoColumn.getComputedValue(param);
        });
        airtimeColumn = new JFXTreeTableColumn<>("Airtime");
        airtimeColumn.setPrefWidth(150);
        airtimeColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<ChannelWrapped, String> param) -> {
            if (airtimeColumn.validateValue(param))
                return param.getValue().getValue().airtimeProperty();
            else return airtimeColumn.getComputedValue(param);
        });
        cityColumn = new JFXTreeTableColumn<>("City");
        cityColumn.setPrefWidth(150);
        cityColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<ChannelWrapped, String> param) -> {
            if (cityColumn.validateValue(param))
                return param.getValue().getValue().cityProperty();
            else return cityColumn.getComputedValue(param);
        });
        descriptionColumn = new JFXTreeTableColumn<>("Description");
        descriptionColumn.setPrefWidth(150);
        descriptionColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<ChannelWrapped, String> param) -> {
            if (descriptionColumn.validateValue(param))
                return param.getValue().getValue().descriptionProperty();
            else return descriptionColumn.getComputedValue(param);
        });
        frequencyColumn = new JFXTreeTableColumn<>("Frequency");
        frequencyColumn.setPrefWidth(150);
        frequencyColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<ChannelWrapped, String> param) -> {
            if (frequencyColumn.validateValue(param))
                return param.getValue().getValue().frequencyProperty();
            else return frequencyColumn.getComputedValue(param);
        });
        satelliteColumn = new JFXTreeTableColumn<>("Satellite");
        satelliteColumn.setPrefWidth(150);
        satelliteColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<ChannelWrapped, String> param) -> {
            if (satelliteColumn.validateValue(param))
                return param.getValue().getValue().satelliteProperty();
            else return satelliteColumn.getComputedValue(param);
        });
        try {
            mServerConnection = new ServerConnection(
                    InetAddress.getByName(
                            ServerConnection.DEFAULT_IP),
                    ServerConnection.DEFAULT_PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mServerConnection.requestData(Channel.class, this);
    }

    public Channel getSelectedItem() {
        return channelTable.getSelectionModel().getSelectedItem().getValue().getChannel();
    }

    @Override
    public void onReceive(Protocol request) {
        Platform.runLater(() -> {
            ObservableList<ChannelWrapped> channels = null;
            try {
                channels = FXCollections.observableArrayList(ChannelWrapped.wrap(request.getData()));
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
            // build tree
            final TreeItem<ChannelWrapped> root = new RecursiveTreeItem<>(channels, RecursiveTreeObject::getChildren);

            channelTable.setRoot(root);
            channelTable.setShowRoot(false);
            channelTable.setEditable(false);
            channelTable.getColumns().setAll(idColumn,
                    nameColumn,
                    foundationDateColumn,
                    destructionDateColumn,
                    ownerColumn,
                    logoColumn,
                    airtimeColumn,
                    cityColumn,
                    descriptionColumn,
                    frequencyColumn,
                    satelliteColumn);
        });
    }
}