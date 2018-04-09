package rightSidebarPane;

import com.jfoenix.controls.*;
import dao.BaseDAO;
import dao.Channel;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import mainPane.MainController;
import utils.ChangeChecker;
import utils.FieldsValidation;
import utils.Logger;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
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

    private static AnchorPane channelPanel;
    private static Label channelTitle;
    private static JFXTextField nameField;
    private static JFXTextField ownerField;
    private static ImageView logoField;
    private static JFXTextField airtimeField;
    private static JFXTextField cityField;
    private static JFXTextArea descriptionField;
    private static JFXTextField frequencyField;
    private static JFXTextField satelliteField;

    private static JFXSnackbar snackbar;
    private static String mode;

    public static void setMode(String passedMode) {
        mode = passedMode;
    }

    public static void setChannelTitle(String title) {
        channelTitle.setText(title);
    }

    private void setFieldsValues(String name, String owner, Image logo, String airtime, String city, String description, String frequency, String satellite){
        nameField.setText(name);
        ownerField.setText(owner);
        logoField.setImage(logo);
        airtimeField.setText(airtime);
        cityField.setText(city);
        descriptionField.setText(description);
        frequencyField.setText(frequency);
        satelliteField.setText(satellite);

        ChangeChecker.hasChanged(false);
    }

    private void setFieldsValues(BaseDAO baseDAO){
        Channel channel = (Channel) baseDAO;
        nameField.setText(channel.getName());
        ownerField.setText(channel.getOwner());
        logoField.setImage(new Image(channel.getLogo()));
        airtimeField.setText(channel.getAirtime());
        cityField.setText(channel.getCity());
        descriptionField.setText(channel.getDescription());
        frequencyField.setText(channel.getFrequency());
        satelliteField.setText((channel.getSatellite() != null) ? channel.getSatellite() : "no satellite");

        ChangeChecker.hasChanged(false);
    }

    private void setStaticValues(){
        channelPanel = channelPane;
        channelTitle = channelLabel;
        nameField = name;
        ownerField = owner;
        logoField = logo;
        airtimeField = airtime;
        cityField = city;
        descriptionField = description;
        frequencyField = frequency;
        satelliteField = satellite;

        snackbar = new JFXSnackbar();
    }

    @FXML
    void chooseImage(MouseEvent event) {
        Logger.logInfo("A", ((Channel) MainController.currentTable.getSelectionModel().getSelectedItem()).getDescription());

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
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

    }

    private ChangeListener foundationDateListener = (observable, oldValue, newValue) -> {
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
    };

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setStaticValues();

        Pattern namePattern = Pattern.compile("[0-9а-яА-яіІїЇєЄ\\-\\s'!+:]{0,45}");
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

        foundationDate.valueProperty().addListener(foundationDateListener);

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

        name.textProperty().addListener(ChangeChecker.textListener);
        owner.textProperty().addListener(ChangeChecker.textListener);
        logo.imageProperty().addListener(ChangeChecker.valueListener);
        airtime.textProperty().addListener(ChangeChecker.textListener);
        city.textProperty().addListener(ChangeChecker.textListener);
        description.textProperty().addListener(ChangeChecker.textListener);
        frequency.textProperty().addListener(ChangeChecker.textListener);
        satellite.textProperty().addListener(ChangeChecker.textListener);
    }

    public static boolean check() {
        if (FieldsValidation.textFieldIsNotEmpty(nameField) && FieldsValidation.textFieldIsNotEmpty(ownerField) &&
                FieldsValidation.textFieldIsNotEmpty(airtimeField) && FieldsValidation.textFieldIsNotEmpty(cityField) &&
                FieldsValidation.textFieldIsNotEmpty(frequencyField)) {
            if (ChangeChecker.hasChanged()) {
                save();
                return true;
            } else
                snackbar.show("Запис не містить змін!", 2000);
        }
        return false;
    }

    private static void save() {
        if (mode.equals("Редагувати"))
            snackbar.show("Запис успішно відредаговано!", 2000);
        else snackbar.show("Запис успішно додано до бази даних!", 2000);
    }

    public static boolean cancel() {
        if ((!mode.equals("Редагувати") && ChangeChecker.hasChanged()) ||
                (mode.equals("Редагувати") && ChangeChecker.hasChanged() && FieldsValidation.textFieldIsNotEmpty(nameField) && FieldsValidation.textFieldIsNotEmpty(ownerField) &&
                        FieldsValidation.textFieldIsNotEmpty(airtimeField) && FieldsValidation.textFieldIsNotEmpty(cityField) && FieldsValidation.textFieldIsNotEmpty(frequencyField))) {
            snackbar.show("Запис містить зміни! Збережіть їх!", 2000);
            return false;
        }
        return true;
    }

}
