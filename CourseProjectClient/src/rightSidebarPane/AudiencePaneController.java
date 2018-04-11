package rightSidebarPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import dao.Audience;
import dao.BaseDAO;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import utils.ChangeChecker;
import utils.CustomPane;
import utils.FieldsValidation;
import utils.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import static utils.CustomPane.Type.EDIT;

public class AudiencePaneController implements Initializable {

    @FXML
    private CustomPane audiencePane;

    @FXML
    private AnchorPane pane;

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
    private JFXButton btnSave;

    @FXML
    private JFXButton btnCancel;

    @FXML
    void checkAndSave(MouseEvent event) {
        if (check())
            ((BorderPane) audiencePane.getParent()).setRight(null);
    }

    @FXML
    void checkAndCancel(MouseEvent event) {
        cancelCounter++;
        if (cancel() || cancelCounter == 2)
            ((BorderPane) audiencePane.getParent()).setRight(null);
    }

    private static JFXSnackbar snackbar;

    private int cancelCounter;

    private void setFieldsValues(BaseDAO baseDAO) {
        Audience audience = (Audience) baseDAO;

        name.setText(audience.getName());
        ageCategory.setText(audience.getAgeCategory());
        description.setText(audience.getDescription());
        if (!audience.getEmblem().equals("NO-IMAGE"))
            try {
                byte[] raw = java.util.Base64.getDecoder().decode(((Audience) audiencePane.getData()).getEmblem());
                emblem.setImage(SwingFXUtils.toFXImage(ImageIO.read(new ByteArrayInputStream(raw)), null));
            } catch (IOException e) {
                e.printStackTrace();
            }

        ChangeChecker.hasChanged(false);
    }

    private Audience getFieldsData(){
        Audience audience = new Audience();

        audience.setName(name.getText());
        audience.setAgeCategory(ageCategory.getText());
        audience.setDescription(description.getText());
        if (emblem.getImage() == null)
            audience.setEmblem(null);
        else {
            try {
                BufferedImage bImage = SwingFXUtils.fromFXImage(emblem.getImage(), null);
                ByteArrayOutputStream s = new ByteArrayOutputStream();
                ImageIO.write(bImage, "png", s);
                byte[] res = s.toByteArray();
                s.close();
                audience.setEmblem(Base64.getEncoder().encodeToString(res));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return audience;
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
        snackbar = new JFXSnackbar(pane);
        cancelCounter = 0;

        btnSave.setTooltip(new Tooltip("Зберегти"));
        btnCancel.setTooltip(new Tooltip("Скасувати"));

        Pattern namePattern = Pattern.compile("[0-9а-яА-яіІїЇєЄ\\-\\s']{0,45}");
        TextFormatter nameFormatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change
                -> namePattern.matcher(change.getControlNewText()).matches() ? change : null);
        name.setTextFormatter(nameFormatter);

        name.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                FieldsValidation.textFieldIsNotEmpty(name);
            }
        });

//        Pattern ageCategoryPattern = Pattern.compile("\\+?\\d{0,2}|\\d+\\+");
        Pattern ageCategoryPattern = Pattern.compile("(\\+?\\d{0,2}|\\d+\\+)|[а-яА-яіІїЇєЄ+\\s']{0,15}");
        TextFormatter ageCategoryFormatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change
                -> ageCategoryPattern.matcher(change.getControlNewText()).matches() ? change : null);
        ageCategory.setTextFormatter(ageCategoryFormatter);

        ageCategory.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                FieldsValidation.textFieldIsNotEmpty(ageCategory);
            }
        });

        description.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= 100 ? change : null));

        ChangeChecker.hasChanged(false);

        name.textProperty().addListener(ChangeChecker.textListener);
        ageCategory.textProperty().addListener(ChangeChecker.textListener);
        emblem.imageProperty().addListener(ChangeChecker.valueListener);
        description.textProperty().addListener(ChangeChecker.textListener);

        Platform.runLater(() -> {
            Logger.logInfo("String", audiencePane.getData().toString());
            switch (audiencePane.getType()) {
                case EDIT:
                    setFieldsValues(audiencePane.getData());
                    audienceLabel.setText("Редагувати аудиторію");
                    break;
                default:
                    audienceLabel.setText("Додати аудиторію");
                    break;
            }
        });
    }

    private boolean check() {
        if (FieldsValidation.textFieldIsNotEmpty(name) && FieldsValidation.textFieldIsNotEmpty(ageCategory)) {
            if (ChangeChecker.hasChanged()) {
                save();
                return true;
            } else
                snackbar.show("Запис не містить змін!", 2000);
        }
        return false;
    }

    private void save() {
//        if (mode.equals("Редагувати"))
//            snackbar.show("Запис успішно відредаговано!", 2000);
//        else snackbar.show("Запис успішно додано до бази даних!", 2000);
    }

    private boolean cancel() {
        if ((!audiencePane.getType().equals(EDIT) && ChangeChecker.hasChanged()) ||
                (audiencePane.getType().equals(EDIT) && ChangeChecker.hasChanged() && (FieldsValidation.textFieldIsNotEmpty(name) || FieldsValidation.textFieldIsNotEmpty(ageCategory)))) {
            snackbar.show("Запис містить зміни! Збережіть їх або\nскасуйте, натиснувши ще раз \"Скасувати\"!", 2000);
            return false;
        }
        return true;
    }
}
