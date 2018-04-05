package mainPane;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
            right = FXMLLoader.load(getClass().getResource("../rightSidebarPane/RightSidebarPane.fxml"));
            mainPane.setRight(right);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
