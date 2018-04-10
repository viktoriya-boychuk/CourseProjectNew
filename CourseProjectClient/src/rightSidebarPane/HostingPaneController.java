package rightSidebarPane;

import com.jfoenix.controls.*;
import dao.BaseDAO;
import dao.Hosting;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import utils.ChangeChecker;
import utils.CustomPane;
import utils.FieldsValidation;
import utils.Logger;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import static utils.CustomPane.Type.EDIT;

public class HostingPaneController implements Initializable {

    @FXML
    private CustomPane hostingPane;

    @FXML
    private Label hostingLabel;

    @FXML
    private JFXComboBox<?> announcer;

    @FXML
    private JFXComboBox<?> program;

    @FXML
    private JFXTextField announcerGratuity;

    @FXML
    private JFXDatePicker contractBeginDate;

    @FXML
    private JFXDatePicker contractEndDate;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnCancel;

    @FXML
    void checkAndSave(MouseEvent event) {
        if (check())
            ((BorderPane) hostingPane.getParent()).setRight(null);
    }

    @FXML
    void checkAndCancel(MouseEvent event) {
        if (cancel())
            ((BorderPane) hostingPane.getParent()).setRight(null);
    }

    private static JFXSnackbar snackbar;

    private void setFieldsValues(BaseDAO baseDAO) {
        Hosting hosting = (Hosting) baseDAO;

        //announcer.setItems(hosting.getAnnouncerID());
        //program.setItems(hosting.getProgramID());
        announcerGratuity.setText(hosting.getAnnouncerGratuity().toString());
        contractBeginDate.setValue(hosting.getContractBeginDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        contractEndDate.setValue(hosting.getContractEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

        ChangeChecker.hasChanged(false);
    }

    private ChangeListener contractBeginDateListener = (observable, oldValue, newValue) -> {
        if (oldValue != newValue) {
            Callback<DatePicker, DateCell> dayCellFactoryEnd = new Callback<DatePicker, DateCell>() {
                public DateCell call(final DatePicker datePicker) {
                    return new DateCell() {
                        @Override
                        public void updateItem(LocalDate item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item.isBefore(contractBeginDate.getValue())) {
                                this.setDisable(true);
                            }
                        }
                    };
                }
            };
            contractEndDate.setDayCellFactory(dayCellFactoryEnd);
        }
    };

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        snackbar = new JFXSnackbar();

        Pattern pattern = Pattern.compile("[1-9]?|[1-9]\\d{0,5}|\\d+\\.\\d{0,2}");
        TextFormatter formatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return pattern.matcher(change.getControlNewText()).matches() ? change : null;
        });
        announcerGratuity.setTextFormatter(formatter);

        /*Callback<DatePicker, DateCell> dayCellFactoryBegin = new Callback<DatePicker, DateCell>() {
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isAfter(LocalDate.now())) {
                            this.setDisable(true);
                        }
                    }
                };
            }
        };
        contractBeginDate.setDayCellFactory(dayCellFactoryBegin);*/

        contractBeginDate.valueProperty().addListener(contractBeginDateListener);

        announcer.getSelectionModel().select(0);
        program.getSelectionModel().select(0);
        contractBeginDate.setValue(LocalDate.now());

        ChangeChecker.hasChanged(false);

        announcer.valueProperty().addListener(ChangeChecker.valueListener);
        program.valueProperty().addListener(ChangeChecker.valueListener);
        announcerGratuity.textProperty().addListener(ChangeChecker.textListener);
        contractBeginDate.valueProperty().addListener(ChangeChecker.valueListener);
        contractEndDate.valueProperty().addListener(ChangeChecker.valueListener);

        Platform.runLater(() -> {
            Logger.logInfo("String", hostingPane.getData().toString());
            switch (hostingPane.getType()) {
                case EDIT:
                    setFieldsValues(hostingPane.getData());
                    hostingLabel.setText("Редагувати ведучого передачі");
                    break;
                default:
                    hostingLabel.setText("Додати ведучого передачі");
                    break;
            }
        });
    }

    private boolean check() {
        if (ChangeChecker.hasChanged()) {
            save();
            return true;
        }
//        else
//            snackbar.show("Запис не містить змін!", 2000);
        return false;
    }

    private void save() {
//        if (mode.equals("Редагувати"))
//            snackbar.show("Запис успішно відредаговано!", 2000);
//        else snackbar.show("Запис успішно додано до бази даних!", 2000);
    }

    private boolean cancel() {
        if (ChangeChecker.hasChanged()) {
            snackbar.show("Запис містить зміни! Збережіть їх!", 2000);
            return false;
        }
        return true;
    }
}
