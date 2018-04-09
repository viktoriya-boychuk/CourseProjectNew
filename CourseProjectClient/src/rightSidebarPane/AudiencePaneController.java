package rightSidebarPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
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
import utils.ChangeChecker;
import utils.FieldsValidation;

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

    private static AnchorPane audiencePanel;
    private static Label audienceTitle;
    private static JFXTextField nameField;
    private static JFXTextField ageCategoryField;
    private static JFXTextArea descriptionField;
    private static ImageView emblemField;

    private static JFXSnackbar snackbar;
    private static String mode;

    public static void setMode(String passedMode) {
        mode = passedMode;
    }

    public static void setAudienceTitle(String title) {
        audienceTitle.setText(title);
    }

    private void setFieldsValues(String name, String ageCategory, String description, Image emblem){
        nameField.setText(name);
        ageCategoryField.setText(ageCategory);
        descriptionField.setText(description);
        emblemField.setImage(emblem);

        ChangeChecker.hasChanged(false);
    }

    private void setStaticValues(){
        audiencePanel = audiencePane;
        audienceTitle = audienceLabel;
        nameField = name;
        ageCategoryField = ageCategory;
        descriptionField = description;
        emblemField = emblem;

        snackbar = new JFXSnackbar();
    }

    @FXML
    void chooseImage(MouseEvent event) {
        String imageFile;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Вибір зображення");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.bmp", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(btnChooseEmblem.getScene().getWindow());
        if (selectedFile != null) {
            try {
                imageFile = selectedFile.toURI().toURL().toString();
                emblem.setImage(new Image(imageFile));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setStaticValues();

        ChangeChecker.hasChanged(false);

        Pattern namePattern = Pattern.compile("[0-9а-яА-яіІїЇєЄ\\-\\s']{0,45}");
        TextFormatter nameFormatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return namePattern.matcher(change.getControlNewText()).matches() ? change : null;
        });
        name.setTextFormatter(nameFormatter);

        ageCategoryField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                FieldsValidation.textFieldIsNotEmpty(ageCategoryField);
            }
        });
        Pattern ageCategoryPattern = Pattern.compile("\\+?\\d{0,2}|\\d+\\+");
        TextFormatter ageCategoryFormatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return ageCategoryPattern.matcher(change.getControlNewText()).matches() ? change : null;
        });
        ageCategory.setTextFormatter(ageCategoryFormatter);

        description.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= 100 ? change : null));

        name.textProperty().addListener(ChangeChecker.textListener);
        ageCategory.textProperty().addListener(ChangeChecker.textListener);
        emblem.imageProperty().addListener(ChangeChecker.valueListener);
        description.textProperty().addListener(ChangeChecker.textListener);
    }

    public static boolean check() {
        if (FieldsValidation.textFieldIsNotEmpty(nameField) && FieldsValidation.textFieldIsNotEmpty(ageCategoryField)) {
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
                (mode.equals("Редагувати") && ChangeChecker.hasChanged() && FieldsValidation.textFieldIsNotEmpty(nameField) && FieldsValidation.textFieldIsNotEmpty(ageCategoryField))) {
            snackbar.show("Запис містить зміни! Збережіть їх!", 2000);
            return false;
        }
        return true;
    }
}
