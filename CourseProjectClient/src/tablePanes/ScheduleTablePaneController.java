package tablePanes;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dao.Program;
import dao.Schedule;
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
import wrappedDAO.ProgramWrapped;
import wrappedDAO.ScheduleWrapped;

import java.net.InetAddress;
import java.net.URL;
import java.util.ResourceBundle;

public class ScheduleTablePaneController implements Initializable, Receiver {
    @FXML
    JFXTreeTableView<ScheduleWrapped> scheduleTable;

    private JFXTreeTableColumn<ScheduleWrapped, Integer> idColumn;
    private JFXTreeTableColumn<ScheduleWrapped, String> nameColumn;
    private JFXTreeTableColumn<ScheduleWrapped, String> dateColumn;
    private JFXTreeTableColumn<ScheduleWrapped, String> timeColumn;
    private JFXTreeTableColumn<ScheduleWrapped, Integer> channelIDColumn;
    private JFXTreeTableColumn<ScheduleWrapped, Integer> programIDColumn;

    private static ObservableList<ScheduleWrapped> mWrappedSchedules;

    private ServerConnection mServerConnection;

    private static Schedule mSelectedSchedule;

    public static Schedule getSelectedSchedule() {
        return mSelectedSchedule;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idColumn = new JFXTreeTableColumn<>(" ");
        idColumn.setPrefWidth(100);
        idColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<ScheduleWrapped, Integer> param) -> {
            if (idColumn.validateValue(param)) return param.getValue().getValue().idProperty().asObject();
            else return idColumn.getComputedValue(param);
        });

        nameColumn = new JFXTreeTableColumn<>(" ");
        nameColumn.setPrefWidth(100);
        nameColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<ScheduleWrapped, String> param) -> {
            if (nameColumn.validateValue(param)) return param.getValue().getValue().nameProperty();
            else return nameColumn.getComputedValue(param);
        });

        dateColumn = new JFXTreeTableColumn<>(" ");
        dateColumn.setPrefWidth(100);
        dateColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<ScheduleWrapped, String> param) -> {
            if (dateColumn.validateValue(param)) return param.getValue().getValue().dateProperty();
            else return dateColumn.getComputedValue(param);
        });

        timeColumn = new JFXTreeTableColumn<>(" ");
        timeColumn.setPrefWidth(100);
        timeColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<ScheduleWrapped, String> param) -> {
            if (timeColumn.validateValue(param)) return param.getValue().getValue().timeProperty();
            else return timeColumn.getComputedValue(param);
        });

        channelIDColumn = new JFXTreeTableColumn<>(" ");
        channelIDColumn.setPrefWidth(100);
        channelIDColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<ScheduleWrapped, Integer> param) -> {
            if (channelIDColumn.validateValue(param)) return param.getValue().getValue().channelIDProperty().asObject();
            else return channelIDColumn.getComputedValue(param);
        });

        programIDColumn = new JFXTreeTableColumn<>(" ");
        programIDColumn.setPrefWidth(100);
        programIDColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<ScheduleWrapped, Integer> param) -> {
            if (programIDColumn.validateValue(param)) return param.getValue().getValue().programIDProperty().asObject();
            else return programIDColumn.getComputedValue(param);
        });
        try {
            mServerConnection = new ServerConnection(
                    InetAddress.getByName(
                            ServerConnection.DEFAULT_IP),
                    ServerConnection.DEFAULT_PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mServerConnection.requestData(Schedule.class, this);

    }

    public Schedule getSelectedItem() {
        return scheduleTable.getSelectionModel().getSelectedItem().getValue().getSchedule();
    }

    @Override
    public void onReceive(Protocol request) {
        Platform.runLater(() -> {
            try {
                mWrappedSchedules = FXCollections.observableArrayList(ScheduleWrapped.wrap(request.getData()));
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
            // build tree
            final TreeItem<ScheduleWrapped> root = new RecursiveTreeItem<>(mWrappedSchedules, RecursiveTreeObject::getChildren);

            scheduleTable.setRoot(root);
            scheduleTable.setShowRoot(false);
            scheduleTable.setEditable(false);
            scheduleTable.getColumns().setAll(idColumn,
                    nameColumn,
                    dateColumn,
                    timeColumn,
                    channelIDColumn,
                    programIDColumn);
        });
    }
}
