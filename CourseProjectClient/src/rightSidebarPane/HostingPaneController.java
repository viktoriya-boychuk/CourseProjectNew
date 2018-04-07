package rightSidebarPane;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.net.URL;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class HostingPaneController implements Initializable {

    @FXML
    private AnchorPane hostingPane;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

        contractBeginDate.valueProperty().addListener((observable, oldValue, newValue) -> {
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
        });


        /*UnaryOperator<Change> filter = change -> {
            String text = change.getText();

            if (text.matches("[0-9]*")) {
                return change;
            }

            return null;
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        announcerGratuity.setTextFormatter(textFormatter);*/

    }
}
