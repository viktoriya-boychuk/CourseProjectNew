package tablePanes;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dao.BaseDAO;
import dao.Channel;
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
import wrappedDAO.ChannelWrapped;

import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChannelTablePaneController implements Initializable, Receiver, BaseTable {
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

    static private ObservableList<ChannelWrapped> mWrappedChannels;

    public static ObservableList<ChannelWrapped> getWrappedData() {
        return mWrappedChannels;
    }

    private ArrayList<? extends BaseDAO> mChannels;

    private ServerConnection mServerConnection;

    private static Channel mSelectedChannel;

    public static Channel getSelectedChannel() {
        return mSelectedChannel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idColumn = new JFXTreeTableColumn<>("№");
        idColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<ChannelWrapped, Integer> param) -> {
            if (idColumn.validateValue(param))
                return param.getValue().getValue().idProperty().asObject();
            else return idColumn.getComputedValue(param);
        });
        nameColumn = new JFXTreeTableColumn<>("Назва");
        nameColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<ChannelWrapped, String> param) -> {
            if (nameColumn.validateValue(param))
                return param.getValue().getValue().nameProperty();
            else return nameColumn.getComputedValue(param);
        });
        foundationDateColumn = new JFXTreeTableColumn<>("Дата заснування");
        foundationDateColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<ChannelWrapped, String> param) -> {
            if (foundationDateColumn.validateValue(param))
                return param.getValue().getValue().foundationDateProperty();
            else return foundationDateColumn.getComputedValue(param);
        });
        destructionDateColumn = new JFXTreeTableColumn<>("Дата закриття");
        destructionDateColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<ChannelWrapped, String> param) -> {
            if (destructionDateColumn.validateValue(param))
                return param.getValue().getValue().destructionDateProperty();
            else return destructionDateColumn.getComputedValue(param);
        });
        ownerColumn = new JFXTreeTableColumn<>("Власник");
        ownerColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<ChannelWrapped, String> param) -> {
            if (ownerColumn.validateValue(param))
                return param.getValue().getValue().ownerProperty();
            else return ownerColumn.getComputedValue(param);
        });
        airtimeColumn = new JFXTreeTableColumn<>("Ефірний час");
        airtimeColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<ChannelWrapped, String> param) -> {
            if (airtimeColumn.validateValue(param))
                return param.getValue().getValue().airtimeProperty();
            else return airtimeColumn.getComputedValue(param);
        });
        cityColumn = new JFXTreeTableColumn<>("Місто");
        cityColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<ChannelWrapped, String> param) -> {
            if (cityColumn.validateValue(param))
                return param.getValue().getValue().cityProperty();
            else return cityColumn.getComputedValue(param);
        });
        descriptionColumn = new JFXTreeTableColumn<>("Опис");
        descriptionColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<ChannelWrapped, String> param) -> {
            if (descriptionColumn.validateValue(param))
                return param.getValue().getValue().descriptionProperty();
            else return descriptionColumn.getComputedValue(param);
        });
        frequencyColumn = new JFXTreeTableColumn<>("Частота");
        frequencyColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<ChannelWrapped, String> param) -> {
            if (frequencyColumn.validateValue(param))
                return param.getValue().getValue().frequencyProperty();
            else return frequencyColumn.getComputedValue(param);
        });
        satelliteColumn = new JFXTreeTableColumn<>("Супутник");
        satelliteColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<ChannelWrapped, String> param) -> {
            if (satelliteColumn.validateValue(param))
                return param.getValue().getValue().satelliteProperty();
            else return satelliteColumn.getComputedValue(param);
        });

        reloadList();

        channelTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null)
                mSelectedChannel = newSelection.getValue().getChannel();
        });
    }

    @Override
    public Channel getSelectedItem() {
        return channelTable.getSelectionModel().getSelectedItem().getValue().getChannel();
    }

    @Override
    public ArrayList<? extends BaseDAO> getCurrentList() {
        return mChannels;
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
        mServerConnection.requestData(Channel.class, this);
    }

    @Override
    public void onReceive(Protocol request) {
        Platform.runLater(() -> {
            try {
                mChannels = request.getData();
                mWrappedChannels = FXCollections.observableArrayList(ChannelWrapped.wrap(request.getData()));
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
            // build tree
            final TreeItem<ChannelWrapped> root = new RecursiveTreeItem<>(mWrappedChannels, RecursiveTreeObject::getChildren);

            channelTable.setRoot(root);
            channelTable.setShowRoot(false);
            channelTable.setEditable(false);
            channelTable.getColumns().setAll(idColumn,
                    nameColumn,
                    foundationDateColumn,
                    destructionDateColumn,
                    ownerColumn,
                    airtimeColumn,
                    cityColumn,
                    descriptionColumn,
                    frequencyColumn,
                    satelliteColumn);

            onPostInitialize(() -> {
                channelTable.getSelectionModel().select(0);
            });
        });
    }
}