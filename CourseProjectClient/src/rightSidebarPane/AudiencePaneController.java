package rightSidebarPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;
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
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class AudiencePaneController implements Initializable {

    @FXML
    private AnchorPane audiencePane;

    @FXML
    private Label audienceLabel;

    @FXML
    private JFXTextField name;

    @FXML
    private JFXTextField ageCategory;

    @FXML
    private JFXTextArea description;

    @FXML
    private ImageView emblem;

    @FXML
    private JFXButton btnChooseEmblem;

    @FXML
    void chooseImage(MouseEvent event) {
        String imageFile;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Вибір зображення");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.bmp", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(btnChooseEmblem.getScene().getWindow());
        if (selectedFile != null) {
            try {
                imageFile = selectedFile.toURI().toURL().toString();
                emblem.setImage(new Image(imageFile));
                Tooltip.install(emblem, new Tooltip(selectedFile.getName()));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Pattern namePattern = Pattern.compile("[0-9а-яА-яіІїЇєЄ\\-\\s']*");
        TextFormatter nameFormatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return namePattern.matcher(change.getControlNewText()).matches() ? change : null;
        });
        name.setTextFormatter(nameFormatter);

        Pattern ageCategoryPattern = Pattern.compile("\\+?\\d{0,2}|\\d+\\+");
        TextFormatter ageCategoryFormatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return ageCategoryPattern.matcher(change.getControlNewText()).matches() ? change : null;
        });
        ageCategory.setTextFormatter(ageCategoryFormatter);

    }
}
