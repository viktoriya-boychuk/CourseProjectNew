package rightSidebarPane;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class ProgramPaneController implements Initializable {

    @FXML
    private AnchorPane programPane;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Pattern categoryPattern = Pattern.compile("[а-яА-яіІїЇєЄ\\-\\s'/]{0,30}");
        TextFormatter categoryFormatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return categoryPattern.matcher(change.getControlNewText()).matches() ? change : null;
        });
        category.setTextFormatter(categoryFormatter);

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

        Pattern countryPattern = Pattern.compile("[а-яА-яіІїЇєЄ\\-\\s']{0,30}");
        TextFormatter countryFormatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return countryPattern.matcher(change.getControlNewText()).matches() ? change : null;
        });
        country.setTextFormatter(countryFormatter);

        Pattern authorPattern = Pattern.compile("[а-яА-яіІїЇєЄ\\-\\s']{0,45}");
        TextFormatter authorFormatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return authorPattern.matcher(change.getControlNewText()).matches() ? change : null;
        });
        author.setTextFormatter(authorFormatter);
    }
}
