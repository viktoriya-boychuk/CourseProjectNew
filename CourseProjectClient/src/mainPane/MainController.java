package mainPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import utils.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private AnchorPane mainContainer;

    @FXML
    private BorderPane mainPane;

    @FXML
    private BorderPane centralPane;

    @FXML
    private JFXTreeTableView mTreeTable;

    @FXML
    JFXButton buttonAudiencesTable;

    @FXML
    JFXButton buttonAnnouncersTable;

    @FXML
    JFXButton buttonChannelsTable;

    public static JFXTreeTableView currentTable;

    public static FXMLLoader tableLoader;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (!Main.getIsSplashLoaded()) {
            loadSplashScreen();
        }

        VBox top;
        VBox sidebar;
        BorderPane right;
        HBox bottom;
        try {
            top = FXMLLoader.load(getClass().getResource("../topPane/TopPane.fxml"));
            mainPane.setTop(top);
            sidebar = FXMLLoader.load(getClass().getResource("../leftSidebarPane/LeftSidebarPane.fxml"));
            mainPane.setLeft(sidebar);

            tableLoader = new FXMLLoader(getClass().getResource("../tablePanes/AnnouncerTablePane.fxml"));
            mTreeTable = tableLoader.load();

            centralPane.setCenter(mTreeTable);
            currentTable = mTreeTable;

            bottom = FXMLLoader.load(getClass().getResource("../bottomPane/BottomPane.fxml"));
            centralPane.setBottom(bottom);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void audiencesButtonOnClick(MouseEvent event) {
        try {
            tableLoader = new FXMLLoader(getClass().getResource("../tablePanes/AudienceTablePane.fxml"));
            mTreeTable = tableLoader.load();
            centralPane.setCenter(mTreeTable);
            currentTable = mTreeTable;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void announcersButtonOnClick(MouseEvent event) {
        try {
            tableLoader = new FXMLLoader(getClass().getResource("../tablePanes/AnnouncerTablePane.fxml"));
            mTreeTable = tableLoader.load();
            centralPane.setCenter(mTreeTable);
            currentTable = mTreeTable;

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void channelsButtonOnClick(MouseEvent event) {
        try {
            tableLoader = new FXMLLoader(getClass().getResource("../tablePanes/ChannelTablePane.fxml"));
            mTreeTable = tableLoader.load();
            centralPane.setCenter(mTreeTable);
            currentTable = mTreeTable;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void hostingsButtonOnClick(MouseEvent event) {
        try {
            tableLoader = new FXMLLoader(getClass().getResource("../tablePanes/HostingTablePane.fxml"));
            mTreeTable = tableLoader.load();
            centralPane.setCenter(mTreeTable);
            currentTable = mTreeTable;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void programButtonOnClick(MouseEvent event) {
        try {
            tableLoader = new FXMLLoader(getClass().getResource("../tablePanes/ProgramTablePane.fxml"));
            mTreeTable = tableLoader.load();
            centralPane.setCenter(mTreeTable);
            currentTable = mTreeTable;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void scheduleButtonOnClick(MouseEvent event) {
        try {
            tableLoader = new FXMLLoader(getClass().getResource("../tablePanes/ScheduleTablePane.fxml"));
            mTreeTable = tableLoader.load();
            centralPane.setCenter(mTreeTable);
            currentTable = mTreeTable;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadSplashScreen() {
        try {
            Main.setIsSplashLoaded(true);

            AnchorPane pane = FXMLLoader.load(getClass().getResource(("SplashScreenPane.fxml")));
            mainContainer.getChildren().setAll(pane);

            FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), pane);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);

            FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), pane);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setCycleCount(1);

            fadeIn.play();

            fadeIn.setOnFinished((e) -> {
                fadeOut.play();
            });

            fadeOut.setOnFinished((e) -> {
                try {
                    AnchorPane parentContent = FXMLLoader.load(getClass().getResource(("Main.fxml")));
                    mainContainer.getChildren().setAll(parentContent);

                } catch (IOException exception){
                    Logger.logError("Error", "Error loading pane");
                }
            });

        } catch (IOException ex) {
            Logger.logError("Error", "Error loading pane");
        }
    }
}
