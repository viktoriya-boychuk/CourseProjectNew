package leftSidebarPane;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class LeftSidebarPaneController implements Initializable {

    @FXML
    private VBox leftSidebar;

    @FXML
    private JFXButton btnExport;

    @FXML
    private JFXButton btnPrint;

    @FXML
    private JFXButton btnPreview;

    @FXML
    private JFXButton btnSelections;

    @FXML
    private JFXButton btnHelp;

    @FXML
    private JFXButton btnAbout;

    @FXML
    private JFXButton btnClose;

    private static VBox leftSidebarPane;

    public static VBox getSidebar() {
        return leftSidebarPane;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        leftSidebarPane = leftSidebar;
    }
}
