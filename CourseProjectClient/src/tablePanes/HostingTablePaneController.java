package tablePanes;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dao.Announcer;
import dao.BaseDAO;
import dao.Hosting;
import dao.Program;
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
import wrappedDAO.HostingWrapped;

import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class
HostingTablePaneController implements Initializable, Receiver, BaseTable {
    @FXML
    JFXTreeTableView<HostingWrapped> hostingTable;

    private JFXTreeTableColumn<HostingWrapped, Integer> idColumn;
    private JFXTreeTableColumn<HostingWrapped, String> contractBeginDateColumn;
    private JFXTreeTableColumn<HostingWrapped, String> contractEndDateColumn;
    private JFXTreeTableColumn<HostingWrapped, String> announcerGratuityColumn;
    private JFXTreeTableColumn<HostingWrapped, Integer> announcerIDColumn;
    private JFXTreeTableColumn<HostingWrapped, Integer> programIDColumn;

    private static ObservableList<AnnouncerWrapped> mWrappedHostings;

    private ArrayList<? extends BaseDAO> mHostings;

    private ArrayList<Announcer> mAnnouncers;

    private ArrayList<Program> mPrograms;

    private ServerConnection mServerConnection;

    private static Hosting mSelectedHosting;

    public static Hosting getSelectedHosting() {
        return mSelectedHosting;
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

//        mServerConnection.requestData(Program.class, this);
//        mServerConnection.requestData(Hosting.class, this);

        idColumn = new JFXTreeTableColumn<>("№");
        idColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<HostingWrapped, Integer> param) -> {
            if (idColumn.validateValue(param)) return param.getValue().getValue().idProperty().asObject();
            else return idColumn.getComputedValue(param);
        });

        announcerIDColumn = new JFXTreeTableColumn<>("№ ведучого");
        announcerIDColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<HostingWrapped, Integer> param) -> {
            if (announcerIDColumn.validateValue(param))
                return param.getValue().getValue().announcerIDProperty().asObject();
            else return announcerIDColumn.getComputedValue(param);
        });

        programIDColumn = new JFXTreeTableColumn<>("№ програми");
        programIDColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<HostingWrapped, Integer> param) -> {
            if (programIDColumn.validateValue(param)) return param.getValue().getValue().programIDProperty().asObject();
            else return programIDColumn.getComputedValue(param);
        });

        contractBeginDateColumn = new JFXTreeTableColumn<>("Початок контракту");
        contractBeginDateColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<HostingWrapped, String> param) -> {
            if (contractBeginDateColumn.validateValue(param))
                return param.getValue().getValue().contractBeginDateProperty();
            else return contractBeginDateColumn.getComputedValue(param);
        });

        contractEndDateColumn = new JFXTreeTableColumn<>("Кінець контракту");
        contractEndDateColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<HostingWrapped, String> param) -> {
            if (contractEndDateColumn.validateValue(param)) {
                StringProperty value = param.getValue().getValue().contractEndDateProperty();
                return value;
            } else return contractEndDateColumn.getComputedValue(param);
        });

        announcerGratuityColumn = new JFXTreeTableColumn<>("Винагорода (грн.)");
        announcerGratuityColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<HostingWrapped, String> param) -> {
            if (announcerGratuityColumn.validateValue(param))
                return param.getValue().getValue().announcerGratuityProperty();
            else return announcerGratuityColumn.getComputedValue(param);
        });

        reloadList();

        hostingTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null)
                mSelectedHosting = newSelection.getValue().getHosting();
        });
    }

    @Override
    public Hosting getSelectedItem() {
        return hostingTable.getSelectionModel().getSelectedItem().getValue().getHosting();
    }

    @Override
    public ArrayList<? extends BaseDAO> getCurrentList() {
        return mHostings;
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
        mServerConnection.requestData(Hosting.class, this);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onReceive(Protocol request) {
        String dataType = request.getDataType();
        if (dataType.equals("dao.Hosting")) {
            Platform.runLater(() -> {
                ObservableList<HostingWrapped> hostings = null;
                try {
                    mHostings = request.getData();
                    hostings = FXCollections.observableArrayList(HostingWrapped.wrap(request.getData()));
                } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                    e.printStackTrace();
                }
                // build tree
                final TreeItem<HostingWrapped> root = new RecursiveTreeItem<>(hostings, RecursiveTreeObject::getChildren);

                hostingTable.setRoot(root);
                hostingTable.setShowRoot(false);
                hostingTable.setEditable(false);
                hostingTable.getColumns().setAll(idColumn,
                        announcerIDColumn,
                        programIDColumn,
                        contractBeginDateColumn,
                        contractEndDateColumn,
                        announcerGratuityColumn);

                onPostInitialize(() -> {
                    hostingTable.getSelectionModel().select(0);
                });
            });
        }
    }
}