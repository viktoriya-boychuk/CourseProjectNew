package mainPane;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import utils.Protocol;
import utils.Receiver;
import utils.ServerConnection;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private BorderPane mainPane;

    @FXML
    private AnchorPane centralPane;

    @FXML
    private JFXTreeTableView<User> mTreeTable;

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
        } catch (IOException e) {
            e.printStackTrace();
        }

//
        JFXTreeTableColumn<User, String> deptColumn = new JFXTreeTableColumn<>("Department");
        deptColumn.setPrefWidth(150);
        deptColumn.setCellValueFactory((
                TreeTableColumn.CellDataFeatures<User, String> param) -> {
            if (deptColumn.validateValue(param)) return param.getValue().getValue().department;
            else return deptColumn.getComputedValue(param);
        });
        JFXTreeTableColumn<User, String> empColumn = new JFXTreeTableColumn<>("Employee");
        empColumn.setPrefWidth(150);
        empColumn.setCellValueFactory((
                TreeTableColumn.CellDataFeatures<User, String> param) ->

        {
            if (empColumn.validateValue(param)) return param.getValue().getValue().userName;
            else return empColumn.getComputedValue(param);
        });

        JFXTreeTableColumn<User, String> ageColumn = new JFXTreeTableColumn<>("Age");
        ageColumn.setPrefWidth(150);
        ageColumn.setCellValueFactory((
                TreeTableColumn.CellDataFeatures<User, String> param) ->

        {
            if (ageColumn.validateValue(param)) return param.getValue().getValue().age;
            else return ageColumn.getComputedValue(param);
        });
        ageColumn.setCellFactory((
                TreeTableColumn<User, String> param) -> new GenericEditableTreeTableCell<>(new

                TextFieldEditorBuilder()));
        ageColumn.setOnEditCommit((
                TreeTableColumn.CellEditEvent<User, String> t) ->

        {
            t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().age
                    .set(t.getNewValue());
        });

        empColumn.setCellFactory((
                TreeTableColumn<User, String> param) -> new GenericEditableTreeTableCell<>(new

                TextFieldEditorBuilder()));
        empColumn.setOnEditCommit((
                TreeTableColumn.CellEditEvent<User, String> t) ->

        {
            t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue()
                    .userName.set(t.getNewValue());
        });

        deptColumn.setCellFactory((TreeTableColumn<User, String> param) ->
                new GenericEditableTreeTableCell<>(new
                        TextFieldEditorBuilder()));
        deptColumn.setOnEditCommit((
                TreeTableColumn.CellEditEvent<User, String> t) ->

        {
            t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().department.
                    set(t.getNewValue());
        });
        // data
        ObservableList<User> users = FXCollections.observableArrayList();
        users.add(new

                User("Computer Department", "23", "CD 1"));
        users.add(new

                User("Sales Department", "22", "Employee 1"));
        users.add(new

                User("Sales Department", "22", "Employee 2"));
        users.add(new

                User("Sales Department", "25", "Employee 4"));
        users.add(new

                User("Sales Department", "25", "Employee 5"));
        users.add(new

                User("IT Department", "42", "ID 2"));
        users.add(new

                User("HR Department", "22", "HR 1"));
        users.add(new

                User("HR Department", "22", "HR 2"));

        for (
                int i = 0;
                i < 40; i++)

        {
            users.add(new User("HR Department", i % 10 + "", "HR 2" + i));
        }
        for (
                int i = 0;
                i < 40; i++)

        {
            users.add(new User("Computer Department", i % 20 + "", "CD 2" + i));
        }

        for (
                int i = 0;
                i < 40; i++)

        {
            users.add(new User("IT Department", i % 5 + "", "HR 2" + i));
        }

        // build tree
        final TreeItem<User> root = new RecursiveTreeItem<>(users, RecursiveTreeObject::getChildren);

        mTreeTable.setRoot(root);
        mTreeTable.setShowRoot(false);
        mTreeTable.setEditable(false);
        mTreeTable.getColumns().setAll(deptColumn, ageColumn, empColumn);

        JFXTextField filterField = new JFXTextField();
        filterField.textProperty().
                addListener((o, oldVal, newVal) ->

                        mTreeTable.setPredicate(user -> user.getValue().age.get().contains(newVal)
                                || user.getValue().department.get().contains(newVal)
                                || user.getValue().userName.get().contains(newVal)));

        Label size = new Label();
        size.textProperty().

                bind(Bindings.createStringBinding(() -> mTreeTable.getCurrentItemsCount() + "",
                        mTreeTable.currentItemsCountProperty()));
    }
}


class User extends RecursiveTreeObject<User> {
    StringProperty userName;
    StringProperty age;
    StringProperty department;

    public User(String department, String age, String userName) {
        this.department = new SimpleStringProperty(department);
        this.userName = new SimpleStringProperty(userName);
        this.age = new SimpleStringProperty(age);
    }
}
