package rightSidebarPane;

import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

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
        careerBeginYear.setItems(getYearsList(1950));
        careerEndYear.setItems(getYearsList(1950));
        careerBeginYear.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue instanceof String) {
                careerEndYear.getSelectionModel().select(0);
                careerEndYear.setItems(getYearsList(Integer.parseInt((String) (newValue))));
            }
        });
    }

    private ObservableList<Integer> getYearsList(Integer start) {
        ArrayList<Integer> years = new ArrayList<>();
        for (int i = start; i < Calendar.getInstance().get(Calendar.YEAR); i++) {
            years.add(i);
        }
        return FXCollections.observableArrayList(years);
    }
}
