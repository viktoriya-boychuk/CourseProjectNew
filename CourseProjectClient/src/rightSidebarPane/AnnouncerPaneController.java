package rightSidebarPane;

import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import utils.ChangeChecker;
import utils.CustomPane;
import utils.FieldsValidation;
import utils.Logger;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;


public class AnnouncerPaneController implements Initializable {

    @FXML
    private CustomPane announcerPane;

    @FXML
    private Label announcerLabel;

    @FXML
    private JFXTextField name;

    @FXML
    private JFXDatePicker birthDate;

    @FXML
    private JFXComboBox careerBeginYear;

    @FXML
    private JFXComboBox careerEndYear;

    @FXML
    private ToggleGroup sex;

    @FXML
    private JFXTextArea education;

    @FXML
    private JFXTextArea description;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnCancel;

    @FXML
    void checkAndSave(MouseEvent event) {
        //JFXSnackbar snackbar = new JFXSnackbar();
        CustomPane b = (CustomPane) announcerPane.getParent();
        String s = b.getId();
        if (check())
            //((BorderPane) announcerPane.getParent()).setRight(null);
            b.setRight(new BorderPane());
    }

    @FXML
    void checkAndCancel(MouseEvent event) {
        if (cancel())
            ((BorderPane) announcerPane.getParent()).setRight(null);
    }

    private static AnchorPane announcerPanel;
    private static Label announcerTitle;
    private static JFXTextField nameField;
    private static JFXDatePicker birthDateField;
    private static JFXComboBox careerBeginYearField;
    private static JFXComboBox careerEndYearField;
    private static ToggleGroup sexField;
    private static JFXTextArea educationField;
    private static JFXTextArea descriptionField;

    private static JFXSnackbar snackbar;
    private static String mode = "Редагувати";

    public static void setMode(String passedMode) {
        mode = passedMode;
    }

    public static void setAnnouncerTitle(String title) {
        announcerTitle.setText(title);
    }

    private void setFieldsValues(String name, LocalDate birthDate, Integer careerBeginYear, Integer careerEndYear, String education, String description){
        nameField.setText(name);
        birthDateField.setValue(birthDate);
        careerBeginYearField.setItems(getYearsList(careerBeginYear));
        careerEndYearField.setItems(getYearsList(careerEndYear));
        sexField = sex;
        educationField.setText(education);
        descriptionField.setText(description);

        ChangeChecker.hasChanged(false);
    }

    private void setStaticValues(){
        nameField = name;
        birthDateField = birthDate;
        careerBeginYearField = careerBeginYear;
        careerEndYearField = careerEndYear;
        sexField = sex;
        educationField = education;
        descriptionField = description;

        snackbar = new JFXSnackbar();
    }

    private ChangeListener birthDateListener = (observable, oldValue, newValue) -> {
        if (oldValue != newValue) {
            careerBeginYear.setItems(getYearsList(birthDate.getValue().getYear()));
            careerBeginYear.getSelectionModel().select(0);
            careerEndYear.setItems(getYearsList(birthDate.getValue().getYear()));
            careerEndYear.getSelectionModel().select(0);
        }
    };

    private ChangeListener careerBeginYearListener = (observable, oldValue, newValue) -> {
        if (newValue instanceof String) {
            careerEndYear.getSelectionModel().select(0);
            careerEndYear.setItems(getYearsList(Integer.parseInt((String) (newValue))));
        }
    };

    @Override
    public void initialize(URL location, ResourceBundle resources) throws ClassCastException {
        ChangeChecker.hasChanged(false);
        setStaticValues();

        Pattern namePattern = Pattern.compile("[а-яА-яіІїЇєЄ\\-\\s']{0,45}");
        TextFormatter nameFormatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return namePattern.matcher(change.getControlNewText()).matches() ? change : null;
        });
        name.setTextFormatter(nameFormatter);

        Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(LocalDate.now().minusYears(123))) {
                            this.setDisable(true);
                        }
                    }
                };
            }
        };
        birthDate.setDayCellFactory(dayCellFactory);

        birthDate.valueProperty().addListener(birthDateListener);

        careerBeginYear.valueProperty().addListener(careerBeginYearListener);

        birthDate.setValue(LocalDate.now());

        education.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= 100 ? change : null));
        description.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= 100 ? change : null));

        name.textProperty().addListener(ChangeChecker.textListener);
        birthDate.valueProperty().addListener(ChangeChecker.textListener);
        careerBeginYear.valueProperty().addListener(ChangeChecker.valueListener);
        careerEndYear.valueProperty().addListener(ChangeChecker.valueListener);
        sex.selectedToggleProperty().addListener(ChangeChecker.toggleListener);
        education.textProperty().addListener(ChangeChecker.textListener);
        description.textProperty().addListener(ChangeChecker.textListener);

        Platform.runLater(() -> {
            Logger.logInfo("String", announcerPane.getData().toString());
        });
    }

    private ObservableList<Integer> getYearsList(Integer start) {
        ArrayList<Integer> years = new ArrayList<>();
        for (int i = (start < 1936 ? 1936 : start); i <= Calendar.getInstance().get(Calendar.YEAR); i++) {
            years.add(i);
        }
        return FXCollections.observableArrayList(years);
    }

    public static boolean check() {
        if (FieldsValidation.textFieldIsNotEmpty(nameField)) {
            if (ChangeChecker.hasChanged()) {
                save();
                return true;
            }
//            else
//                snackbar.show("Запис не містить змін!", 2000);
        }
        return false;
    }

    private static void save() {
//        if (mode.equals("Редагувати"))
//            snackbar.show("Запис успішно відредаговано!", 2000);
//        else snackbar.show("Запис успішно додано до бази даних!", 2000);
    }

    public static boolean cancel() {
        if ((!mode.equals("Редагувати") && ChangeChecker.hasChanged()) ||
                (mode.equals("Редагувати") && ChangeChecker.hasChanged() && FieldsValidation.textFieldIsNotEmpty(nameField))) {
//            snackbar.show("Запис містить зміни! Збережіть їх!", 2000);
            return false;
        }
        return true;
    }
}
