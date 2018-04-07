package rightSidebarPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.util.Callback;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class ChannelPaneController implements Initializable {

    @FXML
    private AnchorPane channelPane;

    @FXML
    private Label channelLabel;

    @FXML
    private JFXTextField name;

    @FXML
    private JFXDatePicker foundationDate;

    @FXML
    private JFXDatePicker destructionDate;

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
    public void initialize(URL location, ResourceBundle resources) {
        Pattern namePattern = Pattern.compile("[а-яА-яіІїЇєЄ\\-\\s'!+:]{0,45}");
        TextFormatter nameFormatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return namePattern.matcher(change.getControlNewText()).matches() ? change : null;
        });
        name.setTextFormatter(nameFormatter);

        Callback<DatePicker, DateCell> dayCellFactoryFoundation = new Callback<DatePicker, DateCell>() {
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(LocalDate.of(1936, 11, 2))) {
                            this.setDisable(true);
                        }
                    }
                };
            }
        };
        foundationDate.setDayCellFactory(dayCellFactoryFoundation);

        foundationDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != newValue) {
                Callback<DatePicker, DateCell> dayCellFactoryEnd = new Callback<DatePicker, DateCell>() {
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);
                                if (item.isBefore(foundationDate.getValue())) {
                                    this.setDisable(true);
                                }
                            }
                        };
                    }
                };
                destructionDate.setDayCellFactory(dayCellFactoryEnd);
            }
        });

        Pattern ownerPattern = Pattern.compile("[а-яА-яіІїЇєЄ\\-\\s']{0,45}");
        TextFormatter ownerFormatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return ownerPattern.matcher(change.getControlNewText()).matches() ? change : null;
        });
        owner.setTextFormatter(ownerFormatter);

        Pattern airtimePattern = Pattern.compile("\\d{0,2}|\\d+/\\d?|\\d");
        TextFormatter airtimeFormatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return airtimePattern.matcher(change.getControlNewText()).matches() ? change : null;
        });
        airtime.setTextFormatter(airtimeFormatter);

        Pattern cityPattern = Pattern.compile("[а-яА-яіІїЇєЄ\\-\\s']{0,45}");
        TextFormatter cityFormatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return cityPattern.matcher(change.getControlNewText()).matches() ? change : null;
        });
        city.setTextFormatter(cityFormatter);

        Pattern frequencyPattern = Pattern.compile("[1-9]?|[1-9]\\d{0,4}|\\d+\\.\\d{0,2}");
        TextFormatter frequencyFormatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return frequencyPattern.matcher(change.getControlNewText()).matches() ? change : null;
        });
        frequency.setTextFormatter(frequencyFormatter);

        Pattern satellitePattern = Pattern.compile("[a-zA-Z0-9\\-\\s]{0,10}");
        TextFormatter satelliteFormatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return satellitePattern.matcher(change.getControlNewText()).matches() ? change : null;
        });
        satellite.setTextFormatter(satelliteFormatter);
    }

}
