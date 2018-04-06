package application;

import dao.Program;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import utils.Logger;
import utils.Protocol;
import utils.Receiver;
import utils.ServerConnection;

import java.net.InetAddress;


public class Main extends Application implements Receiver {
    @Override
    public void start(Stage primaryStage) {
        try {
            BorderPane root = new BorderPane();
            Scene scene = new Scene(root, 400, 400);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

            Logger.initialize();
            ServerConnection client = null;
            try {
                client = new ServerConnection(InetAddress.getByName("127.0.0.1"), 28365);
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("\r\nConnected to Server: " + client.getSocket().getInetAddress());

            client.requestData(Program.class, this);

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void onReceive(Protocol request) {
        Logger.logInfo("received!", request.getDataString());
    }
}
