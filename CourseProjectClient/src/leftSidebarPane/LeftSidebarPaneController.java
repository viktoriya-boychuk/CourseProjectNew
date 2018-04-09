package leftSidebarPane;

import com.jfoenix.controls.JFXButton;
import infoHelpPane.InfoHelpPaneController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mainPane.MainController;

import java.awt.*;
import java.io.IOException;
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

    @FXML
    void openInfoHelpWindow(MouseEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("../infoHelpPane/InfoHelpPane.fxml"));
            InfoHelpPaneController.setTitle(((JFXButton) event.getSource()).getText());
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void closeWindow(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void printTable(MouseEvent event){
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(MainController.currentTable.getScene().getWindow())){
            boolean success = job.printPage(MainController.currentTable);
            if (success) {
                job.endJob();
            }
        }
    }

    private static VBox leftSidebarPane;

    public static VBox getSidebar() {
        return leftSidebarPane;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        leftSidebarPane = leftSidebar;
    }
}
