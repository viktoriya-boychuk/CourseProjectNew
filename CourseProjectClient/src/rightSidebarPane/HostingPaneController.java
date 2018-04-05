package rightSidebarPane;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class HostingPaneController implements Initializable {

    @FXML
    private AnchorPane hostingPane;

    @FXML
    private Label hostingLabel;

    @FXML
    private JFXComboBox<?> announcer;

    @FXML
    private JFXComboBox<?> program;

    @FXML
    private JFXTextField announcerGratuity;

    @FXML
    private JFXDatePicker contractBeginDate;

    @FXML
    private JFXDatePicker contractEndDate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
