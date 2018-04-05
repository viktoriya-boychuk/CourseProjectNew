package rightSidebarPane;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RightSidebarPaneController implements Initializable {

    @FXML
    private BorderPane rightSidebar;

    @FXML
    private AnchorPane bottomPane;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnCancel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AnchorPane replaceablePane;
        try {
            replaceablePane = FXMLLoader.load(getClass().getResource("../rightSidebarPane/HostingPane.fxml"));
            rightSidebar.setCenter(replaceablePane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
