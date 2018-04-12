package tablePanes;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dao.BaseDAO;
import dao.Program;
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
import wrappedDAO.ProgramWrapped;

import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProgramTablePaneController implements Initializable, Receiver, BaseTable {
    @FXML
    JFXTreeTableView<ProgramWrapped> programTable;

    private JFXTreeTableColumn<ProgramWrapped, Integer> idColumn;
    private JFXTreeTableColumn<ProgramWrapped, String> nameColumn;
    private JFXTreeTableColumn<ProgramWrapped, String> categoryColumn;
    private JFXTreeTableColumn<ProgramWrapped, String> genreColumn;
    private JFXTreeTableColumn<ProgramWrapped, Integer> durationColumn;
    private JFXTreeTableColumn<ProgramWrapped, String> countryColumn;
    private JFXTreeTableColumn<ProgramWrapped, String> authorOrProducerColumn;
    private JFXTreeTableColumn<ProgramWrapped, String> descriptionColumn;
    private JFXTreeTableColumn<ProgramWrapped, String> originalityColumn;
    private JFXTreeTableColumn<ProgramWrapped, Integer> audienceIDColumn;

    private static ObservableList<ProgramWrapped> mWrappedPrograms;

    private ArrayList<? extends BaseDAO> mPrograms;

    private ServerConnection mServerConnection;

    private static Program mSelectedProgram;

    public static Program getSelectedProgram() {
        return mSelectedProgram;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idColumn = new JFXTreeTableColumn<>("№");
        idColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProgramWrapped, Integer> param) -> {
            if (idColumn.validateValue(param)) return param.getValue().getValue().idProperty().asObject();
            else return idColumn.getComputedValue(param);
        });

        nameColumn = new JFXTreeTableColumn<>("Назва");
        nameColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProgramWrapped, String> param) -> {
            if (nameColumn.validateValue(param)) return param.getValue().getValue().nameProperty();
            else return nameColumn.getComputedValue(param);
        });

        categoryColumn = new JFXTreeTableColumn<>("Тип");
        categoryColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProgramWrapped, String> param) -> {
            if (categoryColumn.validateValue(param)) return param.getValue().getValue().categoryProperty();
            else return categoryColumn.getComputedValue(param);
        });

        genreColumn = new JFXTreeTableColumn<>("Жанр");
        genreColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProgramWrapped, String> param) -> {
            if (genreColumn.validateValue(param)) return param.getValue().getValue().genreProperty();
            else return genreColumn.getComputedValue(param);
        });

        durationColumn = new JFXTreeTableColumn<>("Тривалість");
        durationColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProgramWrapped, Integer> param) -> {
            if (durationColumn.validateValue(param)) return param.getValue().getValue().durationProperty().asObject();
            else return durationColumn.getComputedValue(param);
        });

        countryColumn = new JFXTreeTableColumn<>("Країна");
        countryColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProgramWrapped, String> param) -> {
            if (countryColumn.validateValue(param)) return param.getValue().getValue().countryProperty();
            else return countryColumn.getComputedValue(param);
        });

        authorOrProducerColumn = new JFXTreeTableColumn<>("Автор/Режисер");
        authorOrProducerColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProgramWrapped, String> param) -> {
            if (authorOrProducerColumn.validateValue(param))
                return param.getValue().getValue().authorOrProducerProperty();
            else return authorOrProducerColumn.getComputedValue(param);
        });

        descriptionColumn = new JFXTreeTableColumn<>("Опис");
        descriptionColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProgramWrapped, String> param) -> {
            if (descriptionColumn.validateValue(param)) return param.getValue().getValue().descriptionProperty();
            else return descriptionColumn.getComputedValue(param);
        });

        originalityColumn = new JFXTreeTableColumn<>("Оригінальність");
        originalityColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProgramWrapped, String> param) -> {
            if (originalityColumn.validateValue(param)) return param.getValue().getValue().originalityProperty();
            else return originalityColumn.getComputedValue(param);
        });

        audienceIDColumn = new JFXTreeTableColumn<>("Аудиторія");
        audienceIDColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProgramWrapped, Integer> param) -> {
            if (audienceIDColumn.validateValue(param))
                return param.getValue().getValue().audienceIDProperty().asObject();
            else return audienceIDColumn.getComputedValue(param);
        });

        reloadList();

        programTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null)
                mSelectedProgram = newSelection.getValue().getProgram();
        });
    }

    @Override
    public Program getSelectedItem() {
        return programTable.getSelectionModel().getSelectedItem().getValue().getProgram();
    }

    @Override
    public ArrayList<? extends BaseDAO> getCurrentList() {
        return mPrograms;
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
        mServerConnection.requestData(Program.class, this);
    }

    @Override
    public void onReceive(Protocol request) {
        Platform.runLater(() -> {
            try {
                mPrograms = request.getData();
                mWrappedPrograms = FXCollections.observableArrayList(ProgramWrapped.wrap(request.getData()));
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
            // build tree
            final TreeItem<ProgramWrapped> root = new RecursiveTreeItem<>(mWrappedPrograms, RecursiveTreeObject::getChildren);

            programTable.setRoot(root);
            programTable.setShowRoot(false);
            programTable.setEditable(false);
            programTable.getColumns().setAll(idColumn,
                    nameColumn,
                    categoryColumn,
                    genreColumn,
                    durationColumn,
                    countryColumn,
                    authorOrProducerColumn,
                    audienceIDColumn,
                    descriptionColumn,
                    originalityColumn);

            onPostInitialize(() -> {
                programTable.getSelectionModel().select(0);
            });
        });
    }
}
