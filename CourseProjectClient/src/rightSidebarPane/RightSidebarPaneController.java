package rightSidebarPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
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

    private static BorderPane rightSidebarPane;
    private AnchorPane replaceablePane;
    private static String mode = "Редагувати";

    public static void setMode(String passedMode) {
        mode = passedMode;
    }

    public static BorderPane getSidebar() {
        return rightSidebarPane;
    }

    @FXML
    void checkAndSave(MouseEvent event) {
        JFXSnackbar snackbar = new JFXSnackbar();
        if (ChannelPaneController.check())
            ((BorderPane) rightSidebar.getParent()).setRight(null);
    }

    @FXML
    void checkAndCancel(MouseEvent event) {
        if (ChannelPaneController.cancel())
            ((BorderPane) rightSidebar.getParent()).setRight(null);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rightSidebarPane = rightSidebar;
        try {
            replaceablePane = FXMLLoader.load(getClass().getResource("../rightSidebarPane/ChannelPane.fxml"));
            rightSidebar.setCenter(replaceablePane);
            ChannelPaneController.setMode(mode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
