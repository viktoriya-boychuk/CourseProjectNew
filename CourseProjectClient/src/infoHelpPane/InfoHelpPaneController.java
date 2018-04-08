package infoHelpPane;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class InfoHelpPaneController implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;

    public static Label text;
    public static Label label;

    @FXML
    private Label infoHelpLabel;

    @FXML
    private Label textLabel;

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
    void minimizeWindow(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void closeWindow(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public static void setTitle(String title) {
        label.setText(title);
        try {
            Scanner scanner;
            if (label.getText().equals("Довідка")) {
                scanner = new Scanner(new File("files/help.txt"));
                text.setPrefSize(850, 600);
            }
            else scanner = new Scanner(new File("files/about.txt"));
            String labelText = "";
            while (scanner.hasNextLine()) {
                labelText = labelText.concat(scanner.nextLine()).concat("\n");
            }
            scanner.close();
            text.setText(labelText);
        } catch (NumberFormatException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        label = infoHelpLabel;
        text = textLabel;


        /*FileReader fileReader;
        try {

            fileReader = new FileReader("files/about.txt");

        BufferedReader br = new BufferedReader(fileReader);
        String s, labelText = "";
        while ((s = br.readLine()) != null) {
            labelText = labelText.concat(s).concat("\n");
        }
        textLabel.setText(labelText);
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }


}
