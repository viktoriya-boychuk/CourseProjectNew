package rightSidebarPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChannelPaneController implements Initializable {

    @FXML
    private AnchorPane channelPane;

    @FXML
    private Label channelLabel;

    @FXML
    private JFXTextField name;

    @FXML
    private JFXDatePicker destructionDate;

    @FXML
    private JFXDatePicker foundationDate;

    @FXML
    private JFXTextField owner;

    @FXML
    private ImageView logo;

    @FXML
    private JFXButton btnChooseLogo;

    @FXML
    private JFXTextField airtime;

    @FXML
    private JFXTextField city;

    @FXML
    private JFXTextArea description;

    @FXML
    private JFXTextField frequency;

    @FXML
    private JFXTextField satellite;

    @FXML
    void chooseImage(MouseEvent event) {
        String imageFile;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Вибір зображення");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.bmp", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(btnChooseLogo.getScene().getWindow());
        if (selectedFile != null) {
            try {
                imageFile = selectedFile.toURI().toURL().toString();
                logo.setImage(new Image(imageFile));
                Tooltip.install(logo, new Tooltip(selectedFile.getName()));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) throws ClassCastException {
    }

}
