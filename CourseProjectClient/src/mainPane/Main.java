package mainPane;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.Logger;

public class Main extends Application {

    private static Boolean isSplashLoaded = false;

    public static Boolean getIsSplashLoaded() {
        return isSplashLoaded;
    }

    public static void setIsSplashLoaded(Boolean isSplashLoaded) {
        Main.isSplashLoaded = isSplashLoaded;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Logger.initialize();
        Parent mainPane = FXMLLoader.load(getClass().getResource("Main.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);

        Scene scene = new Scene(mainPane);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
