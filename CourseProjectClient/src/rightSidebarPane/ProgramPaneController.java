package rightSidebarPane;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ProgramPaneController implements Initializable {

    @FXML
    private AnchorPane programPane;

    @FXML
    private Label programLabel;

    @FXML
    private JFXTextField name;

    @FXML
    private JFXTextField category;

    @FXML
    private JFXTextField genre;

    @FXML
    private JFXTextField duration;

    @FXML
    private JFXComboBox audience;

    @FXML
    private JFXTextField country;

    @FXML
    private JFXTextField author;

    @FXML
    private JFXTextArea description;

    @FXML
    private JFXCheckBox ownIdea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
