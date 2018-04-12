package rightSidebarPane;

import com.jfoenix.controls.*;
import dao.Announcer;
import dao.BaseDAO;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import utils.*;

import java.net.InetAddress;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import static utils.CustomPane.Type.EDIT;

public class AnnouncerPaneController implements Initializable, Receiver {

    @FXML
    private CustomPane announcerPane;

    @FXML
    private AnchorPane pane;

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
    private JFXRadioButton sexMan;

    @FXML
    private JFXRadioButton sexWoman;

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
        if (check()) {
            ((BorderPane) announcerPane.getParent()).setRight(null);
            try {
                Protocol.RequestType type;
                if (announcerPane.getType() == CustomPane.Type.ADD)
                    type = Protocol.RequestType.POST;
                else type = Protocol.RequestType.UPDATE;
                ServerConnection serverConnection = new ServerConnection(InetAddress.getByName(ServerConnection.DEFAULT_IP), 28365);

                Announcer announcer = getFieldsData();

                serverConnection.pushData(type, announcer, this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void checkAndCancel(MouseEvent event) {
        cancelCounter++;
        if (cancel() || cancelCounter == 2)
            ((BorderPane) announcerPane.getParent()).setRight(null);
    }

    private static JFXSnackbar snackbar;

    private int cancelCounter;

    private void setFieldsValues(BaseDAO baseDAO) {
        Announcer announcer = (Announcer) baseDAO;

        name.setText(announcer.getName());
        birthDate.setValue(announcer.getBirthDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        careerBeginYear.getSelectionModel().select(announcer.getCareerBeginYear() - birthDate.getValue().getYear());
        if (!Integer.toString(announcer.getCareerEndYear()).equals(""))
            careerEndYear.getSelectionModel().select(announcer.getCareerEndYear() - birthDate.getValue().getYear());
        if (((Announcer) baseDAO).getSexEnum() == Announcer.Sex.MALE) {
            sex.selectToggle(sexMan);
        } else if (((Announcer) baseDAO).getSexEnum() == Announcer.Sex.FEMALE) {
            sex.selectToggle(sexWoman);
        }
        education.setText(announcer.getEducation());
        description.setText(announcer.getDescription());

        ChangeChecker.hasChanged(false);
    }

    private Announcer getFieldsData(){
        Announcer announcer = new Announcer();

        announcer.setId(announcerPane.getData().getId());
        announcer.setName(name.getText());
        announcer.setBirthDate(Date.from(birthDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        announcer.setCareerBeginYear(Integer.parseInt(careerBeginYear.getSelectionModel().getSelectedItem().toString()));
        announcer.setCareerEndYear(careerEndYear.getSelectionModel().getSelectedItem() != null ? Integer.parseInt(careerEndYear.getSelectionModel().getSelectedItem().toString()) : 0);
        announcer.setSex(sexMan.isSelected() ? Announcer.Sex.MALE : Announcer.Sex.FEMALE);
        announcer.setEducation(education.getText());
        announcer.setDescription(description.getText());

        return announcer;
    }

    private ChangeListener birthDateListener = (observable, oldValue, newValue) -> {
        if (oldValue != newValue) {
            careerBeginYear.setItems(getYearsList(birthDate.getValue().getYear()));
            careerBeginYear.getSelectionModel().select(0);
            //careerEndYear.setItems(getYearsList(careerBeginYear.getSelectionModel().getSelectedIndex()));
        }
    };

    private ChangeListener careerBeginYearListener = (observable, oldValue, newValue) -> {
        if (newValue instanceof String) {
            //careerEndYear.getSelectionModel().select(0);
            careerEndYear.setItems(getYearsList(Integer.parseInt((String) (newValue))));
        }
    };

    @Override
    public void initialize(URL location, ResourceBundle resources) throws ClassCastException {
        snackbar = new JFXSnackbar(pane);
        cancelCounter = 0;

        btnSave.setTooltip(new Tooltip("Зберегти"));
        btnCancel.setTooltip(new Tooltip("Скасувати"));

        Pattern namePattern = Pattern.compile("[а-яА-яіІїЇєЄ\\-\\s']{0,45}");
        TextFormatter nameFormatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return namePattern.matcher(change.getControlNewText()).matches() ? change : null;
        });
        name.setTextFormatter(nameFormatter);

        name.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                FieldsValidation.textFieldIsNotEmpty(name);
            }
        });

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
        birthDate.valueProperty().addListener(ChangeChecker.valueListener);
        careerBeginYear.valueProperty().addListener(ChangeChecker.valueListener);
        careerEndYear.valueProperty().addListener(ChangeChecker.valueListener);
        sex.selectedToggleProperty().addListener(ChangeChecker.toggleListener);
        education.textProperty().addListener(ChangeChecker.textListener);
        description.textProperty().addListener(ChangeChecker.textListener);

        ChangeChecker.hasChanged(false);

        Platform.runLater(() -> {
            Logger.logInfo("String", announcerPane.getData().toString());
            switch (announcerPane.getType()) {
                case EDIT:
                    setFieldsValues(announcerPane.getData());
                    announcerLabel.setText("Редагувати ведучого");
                    break;
                default:
                    announcerLabel.setText("Додати ведучого");
                    break;
            }
        });
    }

    private ObservableList<Integer> getYearsList(Integer start) {
        ArrayList<Integer> years = new ArrayList<>();
        for (int i = (start < 1936 ? 1936 : start); i <= Calendar.getInstance().get(Calendar.YEAR); i++) {
            years.add(i);
        }
        return FXCollections.observableArrayList(years);
    }

    private boolean check() {
        if (FieldsValidation.textFieldIsNotEmpty(name)) {
            if (ChangeChecker.hasChanged()) {
                save();
                return true;
            } else
                snackbar.show("Запис не містить змін!", 2000);
        }
        return false;
    }

    @FXML
    void saveButtonOnClick(MouseEvent event) {


    }

    private void save() {
//        if (mode.equals("Редагувати"))
//            snackbar.show("Запис успішно відредаговано!", 2000);
//        else snackbar.show("Запис успішно додано до бази даних!", 2000);

    }

    private boolean cancel() {
        if ((!announcerPane.getType().equals(EDIT) && ChangeChecker.hasChanged()) ||
                (announcerPane.getType().equals(EDIT) && ChangeChecker.hasChanged() && FieldsValidation.textFieldIsNotEmpty(name))) {
            snackbar.show("Запис містить зміни! Збережіть їх або\nскасуйте, натиснувши ще раз \"Скасувати\"!", 2000);
            return false;
        }
        return true;
    }

    @Override
    public void onReceive(Protocol request) {

    }
}
