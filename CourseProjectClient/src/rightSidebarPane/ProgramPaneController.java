package rightSidebarPane;

import com.jfoenix.controls.*;
import dao.Announcer;
import dao.BaseDAO;
import dao.Program;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import utils.ChangeChecker;
import utils.CustomPane;
import utils.FieldsValidation;
import utils.Logger;

import java.net.URL;
import java.time.ZoneId;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import static utils.CustomPane.Type.EDIT;

public class ProgramPaneController implements Initializable {

    @FXML
    private CustomPane programPane;

    @FXML
    private Label programLabel;

    @FXML
    private JFXTextField name;

    @FXML
    private JFXTextField category;

    @FXML
    private JFXTextField genre;

    @FXML
    private JFXTextField duration;

    @FXML
    private JFXComboBox audience;

    @FXML
    private JFXTextField country;

    @FXML
    private JFXTextField author;

    @FXML
    private JFXTextArea description;

    @FXML
    private JFXCheckBox ownIdea;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnCancel;

    @FXML
    void checkAndSave(MouseEvent event) {
        if (check())
            ((BorderPane) programPane.getParent()).setRight(null);
    }

    @FXML
    void checkAndCancel(MouseEvent event) {
        if (cancel())
            ((BorderPane) programPane.getParent()).setRight(null);
    }

    private static JFXSnackbar snackbar;

    private void setFieldsValues(BaseDAO baseDAO){
        Program program = (Program) baseDAO;

        name.setText(program.getName());
        category.setText(program.getCategory());
        genre.setText(program.getGenre());
        duration.setText(program.getDuration().toString());
        //audience.setItems(program.getAudienceID());
        country.setText(program.getCountry());
        author.setText(program.getAuthorOrProducer());
        description.setText(program.getDescription());
        ownIdea.setSelected(program.getOriginality());

        ChangeChecker.hasChanged(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        snackbar = new JFXSnackbar();

        name.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                FieldsValidation.textFieldIsNotEmpty(name);
            }
        });

        Pattern categoryPattern = Pattern.compile("[а-яА-яіІїЇєЄ\\-\\s'/]{0,30}");
        TextFormatter categoryFormatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return categoryPattern.matcher(change.getControlNewText()).matches() ? change : null;
        });
        category.setTextFormatter(categoryFormatter);

        category.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                FieldsValidation.textFieldIsNotEmpty(category);
            }
        });

        Pattern genrePattern = Pattern.compile("[а-яА-яіІїЇєЄ\\-\\s']{0,20}");
        TextFormatter genreFormatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return genrePattern.matcher(change.getControlNewText()).matches() ? change : null;
        });
        genre.setTextFormatter(genreFormatter);

        Pattern durationPattern = Pattern.compile("\\d{0,3}");
        TextFormatter durationFormatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return durationPattern.matcher(change.getControlNewText()).matches() ? change : null;
        });
        duration.setTextFormatter(durationFormatter);

        duration.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                FieldsValidation.textFieldIsNotEmpty(duration);
            }
        });

        Pattern countryPattern = Pattern.compile("[а-яА-яіІїЇєЄ\\-\\s']{0,30}");
        TextFormatter countryFormatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return countryPattern.matcher(change.getControlNewText()).matches() ? change : null;
        });
        country.setTextFormatter(countryFormatter);

        country.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                FieldsValidation.textFieldIsNotEmpty(country);
            }
        });

        Pattern authorPattern = Pattern.compile("[а-яА-яіІїЇєЄ\\-\\s']{0,45}");
        TextFormatter authorFormatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return authorPattern.matcher(change.getControlNewText()).matches() ? change : null;
        });
        author.setTextFormatter(authorFormatter);

        author.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                FieldsValidation.textFieldIsNotEmpty(author);
            }
        });

        audience.getSelectionModel().select(0);

        name.textProperty().addListener(ChangeChecker.textListener);
        category.textProperty().addListener(ChangeChecker.textListener);
        genre.textProperty().addListener(ChangeChecker.textListener);
        duration.textProperty().addListener(ChangeChecker.textListener);
        audience.valueProperty().addListener(ChangeChecker.valueListener);
        country.textProperty().addListener(ChangeChecker.textListener);
        author.textProperty().addListener(ChangeChecker.textListener);
        description.textProperty().addListener(ChangeChecker.textListener);
        ownIdea.textProperty().addListener(ChangeChecker.textListener);

        ChangeChecker.hasChanged(false);

        Platform.runLater(() -> {
            Logger.logInfo("String", programPane.getData().toString());
            switch (programPane.getType()) {
                case EDIT: setFieldsValues(programPane.getData()); programLabel.setText("Редагувати телепередачу");break;
                default: programLabel.setText("Додати телепередачу"); break;
            }
        });
    }

    private boolean check() {
        if (FieldsValidation.textFieldIsNotEmpty(name) && FieldsValidation.textFieldIsNotEmpty(category) && FieldsValidation.textFieldIsNotEmpty(duration) &&
                FieldsValidation.textFieldIsNotEmpty(country) && FieldsValidation.textFieldIsNotEmpty(author)) {
            if (ChangeChecker.hasChanged()) {
                save();
                return true;
            }
//            else
//                snackbar.show("Запис не містить змін!", 2000);
        }
        return false;
    }

    private void save() {
//        if (mode.equals("Редагувати"))
//            snackbar.show("Запис успішно відредаговано!", 2000);
//        else snackbar.show("Запис успішно додано до бази даних!", 2000);
    }

    private boolean cancel() {
        if ((!programPane.getType().equals(EDIT) && ChangeChecker.hasChanged()) ||
                (programPane.getType().equals(EDIT) && ChangeChecker.hasChanged() && (FieldsValidation.textFieldIsNotEmpty(name) || FieldsValidation.textFieldIsNotEmpty(category) ||
                        FieldsValidation.textFieldIsNotEmpty(duration) || FieldsValidation.textFieldIsNotEmpty(country) || FieldsValidation.textFieldIsNotEmpty(author)))) {
//            snackbar.show("Запис містить зміни! Збережіть їх!", 2000);
            return false;
        }
        return true;
    }
}
