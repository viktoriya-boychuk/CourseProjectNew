package topPane;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import leftSidebarPane.LeftSidebarPaneController;

import java.net.URL;
import java.util.ResourceBundle;

public class TopPaneController implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;
    private boolean isExpand = false;

    @FXML
    private VBox topPane;

    @FXML
    private AnchorPane top;

    @FXML
    private HBox bottom;

    @FXML
    private Label mainLabel;

    @FXML
    private JFXButton btnClose;

    @FXML
    private JFXButton btnMaximize;

    @FXML
    private JFXButton btnMinimize;

    @FXML
    private JFXButton btnMenu;

    private static HBox bottomPanel;

    public static HBox getBottomPanel() {
        return bottomPanel;
    }

    @FXML
    void onPanePressed(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    @FXML
    void dragWindow(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }

    @FXML
    void onPaneDoubleClick(MouseEvent event) {
        if (event.getClickCount() == 2)
            maximizeWindow(event);
    }

    @FXML
    void maximizeWindow(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        if (!stage.isMaximized())
            stage.setMaximized(true);
        else
            stage.setMaximized(false);

        Double height = ((Node) event.getSource()).getScene().getWindow().getHeight();
        Double width = ((Node) event.getSource()).getScene().getWindow().getWidth();
        ((AnchorPane) topPane.getParent().getParent()).setPrefWidth(width);
        ((AnchorPane) topPane.getParent().getParent()).setPrefHeight(height);
    }

    @FXML
    void minimizeWindow(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void closeWindow(MouseEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    void openCloseMenuPane(MouseEvent event) {
        if (isExpand) {
            LeftSidebarPaneController.getSidebar().setPrefWidth(52);
            btnMenu.getTooltip().setText("Відкрити меню");
            isExpand = false;
        } else {
            LeftSidebarPaneController.getSidebar().setPrefWidth(180);
            btnMenu.getTooltip().setText("Закрити меню");
            isExpand = true;
        }
    }

    public void initialize(URL url, ResourceBundle rb) {
        bottomPanel = bottom;
        btnClose.setTooltip(new Tooltip("Закрити"));
        btnMaximize.setTooltip(new Tooltip("Збільшити"));
        btnMinimize.setTooltip(new Tooltip("Згорнути"));
        btnMenu.setTooltip(new Tooltip("Відкрити меню"));
    }
}
