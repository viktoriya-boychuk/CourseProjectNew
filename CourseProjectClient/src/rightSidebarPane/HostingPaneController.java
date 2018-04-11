package rightSidebarPane;

import com.jfoenix.controls.*;
import dao.BaseDAO;
import dao.Hosting;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
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
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import static utils.CustomPane.Type.EDIT;

public class HostingPaneController implements Initializable {

    @FXML
    private CustomPane hostingPane;

    @FXML
    private AnchorPane pane;

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
        cancelCounter++;
        if (cancel() || cancelCounter == 2)
            ((BorderPane) hostingPane.getParent()).setRight(null);
    }

    private static JFXSnackbar snackbar;

    private int cancelCounter;

    private void setFieldsValues(BaseDAO baseDAO) {
        Hosting hosting = (Hosting) baseDAO;

        //announcer.setItems(hosting.getAnnouncerID());
        //program.setItems(hosting.getProgramID());
        if (hosting.getAnnouncerGratuity() != 0.0)
            announcerGratuity.setText(hosting.getAnnouncerGratuity().toString());
        contractBeginDate.setValue(hosting.getContractBeginDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        if (hosting.getContractEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear() != 2149)
            contractEndDate.setValue(hosting.getContractEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

        ChangeChecker.hasChanged(false);
    }

    private Hosting getFieldsData(){
        Hosting hosting = new Hosting();

        //hosting.setAnnouncerID();
        //hosting.setProgramID();
        hosting.setAnnouncerGratuity(announcerGratuity.getText() == null ? null : Double.valueOf(announcerGratuity.getText()));
        hosting.setContractBeginDate(Date.from(contractBeginDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        hosting.setContractEndDate(contractEndDate.getValue() != null ? Date.from(contractEndDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()) : null);

        return hosting;
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
        snackbar = new JFXSnackbar(pane);
        cancelCounter = 0;

        btnSave.setTooltip(new Tooltip("Зберегти"));
        btnCancel.setTooltip(new Tooltip("Скасувати"));

        Pattern pattern = Pattern.compile("[1-9]?|[1-9]\\d{0,5}|\\d+\\.\\d{0,2}");
        TextFormatter formatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return pattern.matcher(change.getControlNewText()).matches() ? change : null;
        });
        announcerGratuity.setTextFormatter(formatter);

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
        if (ChangeChecker.hasChanged()) {
            snackbar.show("Запис містить зміни! Збережіть їх або\nскасуйте, натиснувши ще раз \"Скасувати\"!", 2000);
            return false;
        }
        return true;
    }
}
