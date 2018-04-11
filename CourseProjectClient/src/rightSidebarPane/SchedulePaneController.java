package rightSidebarPane;

import com.jfoenix.controls.*;
import dao.BaseDAO;
import dao.Schedule;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import utils.ChangeChecker;
import utils.CustomPane;
import utils.Logger;

import java.net.URL;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import static utils.CustomPane.Type.EDIT;

public class SchedulePaneController implements Initializable {

    @FXML
    private CustomPane schedulePane;

    @FXML
    private AnchorPane pane;

    @FXML
    private Label scheduleLabel;

    @FXML
    private JFXComboBox<?> channel;

    @FXML
    private JFXComboBox<?> program;

    @FXML
    private JFXDatePicker date;

    @FXML
    private JFXTimePicker time;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnCancel;

    @FXML
    void checkAndSave(MouseEvent event) {
        if (check())
            ((BorderPane) schedulePane.getParent()).setRight(null);
    }

    @FXML
    void checkAndCancel(MouseEvent event) {
        cancelCounter++;
        if (cancel() || cancelCounter == 2)
            ((BorderPane) schedulePane.getParent()).setRight(null);
    }

    private JFXSnackbar snackbar;

    private int cancelCounter;

    private void setFieldsValues(BaseDAO baseDAO) {
        Schedule schedule = (Schedule) baseDAO;

        //channel.setItems(schedule.getChannelID());
        //program.setItems(schedule.getProgramID());
        date.setValue(schedule.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        time.setValue(schedule.getTime().toLocalTime());

        ChangeChecker.hasChanged(false);
    }

    private Schedule getFieldsData(){
        Schedule schedule = new Schedule();

        schedule.setDate(Date.from(date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        schedule.setTime(Time.valueOf(time.getValue()));
        //schedule.setProgramID();
        //schedule.setChannelID();

        return schedule;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        snackbar = new JFXSnackbar(pane);
        cancelCounter = 0;

        btnSave.setTooltip(new Tooltip("Зберегти"));
        btnCancel.setTooltip(new Tooltip("Скасувати"));

        channel.getSelectionModel().select(0);
        program.getSelectionModel().select(0);
        date.setValue(LocalDate.now());
        time.setValue(LocalTime.now());

        channel.valueProperty().addListener(ChangeChecker.valueListener);
        program.valueProperty().addListener(ChangeChecker.valueListener);
        date.valueProperty().addListener(ChangeChecker.valueListener);
        time.valueProperty().addListener(ChangeChecker.valueListener);

        ChangeChecker.hasChanged(false);

        Platform.runLater(() -> {
            Logger.logInfo("String", schedulePane.getData().toString());
            switch (schedulePane.getType()) {
                case EDIT:
                    setFieldsValues(schedulePane.getData());
                    scheduleLabel.setText("Редагувати запис розкладу");
                    break;
                default:
                    scheduleLabel.setText("Додати запис до розкладу");
                    break;
            }
        });
    }

    private boolean check() {
        JFXSnackbar snackbar = new JFXSnackbar();
        if (ChangeChecker.hasChanged()) {
            save();
            return true;
        } else
            snackbar.show("Запис не містить змін!", 2000);
        return false;
    }

    private void save() {
//        if (mode.equals("Редагувати"))
//            snackbar.show("Запис успішно відредаговано!", 2000);
//        else snackbar.show("Запис успішно додано до бази даних!", 2000);
    }

    private boolean cancel() {
        if ((!schedulePane.getType().equals(EDIT) && ChangeChecker.hasChanged()) ||
                (schedulePane.getType().equals(EDIT) && ChangeChecker.hasChanged())) {
            snackbar.show("Запис містить зміни! Збережіть їх або\nскасуйте, натиснувши ще раз \"Скасувати\"!", 2000);
            return false;
        }
        return true;
    }
}
