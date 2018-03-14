package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import utils.Logger;
import utils.ServerConnection;

import java.io.IOException;
import java.net.InetAddress;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            BorderPane root = new BorderPane();
            Scene scene = new Scene(root, 400, 400);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Logger.initialize();
        ServerConnection client = null;
        try {
            client = new ServerConnection(
                    InetAddress.getByName(args[0]),
                    Integer.parseInt(args[1]));
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("\r\nConnected to Server: " + client.getSocket().getInetAddress());
        try {
            client.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        launch(args);
    }
}
