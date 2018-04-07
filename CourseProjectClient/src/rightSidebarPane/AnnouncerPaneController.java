package rightSidebarPane;

import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class AnnouncerPaneController implements Initializable {

    @FXML
    private AnchorPane announcerPane;

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
    private JFXRadioButton sexMan;

    @FXML
    private JFXRadioButton sexWoman;

    @FXML
    private JFXTextArea education;

    @FXML
    private JFXTextArea description;

    @Override
    public void initialize(URL location, ResourceBundle resources) throws ClassCastException {
        Pattern namePattern = Pattern.compile("[а-яА-яіІїЇєЄ\\-\\s']*");
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

        birthDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != newValue) {
                careerBeginYear.setItems(getYearsList(((Integer) birthDate.getValue().getYear())));
            }
        });

        careerBeginYear.setItems(getYearsList(1950));
        careerEndYear.setItems(getYearsList(1950));
        careerBeginYear.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue instanceof String) {
                careerEndYear.getSelectionModel().select(0);
                careerEndYear.setItems(getYearsList(Integer.parseInt((String) (newValue))));
            }
        });

    }

    private ObservableList<Integer> getYearsList(Integer start) {
        ArrayList<Integer> years = new ArrayList<>();
        for (int i = start; i <= Calendar.getInstance().get(Calendar.YEAR); i++) {
            years.add(i);
        }
        return FXCollections.observableArrayList(years);
    }
}
