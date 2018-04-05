package rightSidebarPane;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class SchedulePaneController implements Initializable {

    @FXML
    private AnchorPane schedulePane;

    @FXML
    private Label scheduleLabel;

    @FXML
    private JFXComboBox<?> channel;

    @FXML
    private JFXComboBox<?> program;

    @FXML
    private JFXDatePicker date;

    @FXML
    private JFXTimePicker time;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
