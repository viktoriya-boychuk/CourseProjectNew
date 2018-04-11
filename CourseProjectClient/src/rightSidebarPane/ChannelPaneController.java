package rightSidebarPane;

import com.jfoenix.controls.*;
import com.sun.xml.internal.messaging.saaj.util.Base64;
import dao.BaseDAO;
import dao.Channel;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import utils.ChangeChecker;
import utils.CustomPane;
import utils.FieldsValidation;
import utils.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import static utils.CustomPane.Type.EDIT;

public class ChannelPaneController implements Initializable {

    @FXML
    private CustomPane channelPane;

    @FXML
    private AnchorPane pane;

    @FXML
    private Label channelLabel;

    @FXML
    private JFXTextField name;

    @FXML
    private JFXDatePicker foundationDate;

    @FXML
    private JFXDatePicker destructionDate;

    @FXML
    private JFXTextField owner;

    @FXML
    private ImageView logo;

    @FXML
    private JFXButton btnChooseLogo;

    @FXML
    private JFXTextField airtime;

    @FXML
    private JFXTextField city;

    @FXML
    private JFXTextArea description;

    @FXML
    private JFXTextField frequency;

    @FXML
    private JFXTextField satellite;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnCancel;

    @FXML
    void checkAndSave(MouseEvent event) {
        if (check())
            ((BorderPane) channelPane.getParent()).setRight(null);
    }

    @FXML
    void checkAndCancel(MouseEvent event) {
        cancelCounter++;
        if (cancel() || cancelCounter == 2)
            ((BorderPane) channelPane.getParent()).setRight(null);
    }

    private static JFXSnackbar snackbar;

    private int cancelCounter;

    private void setFieldsValues(BaseDAO baseDAO) {
        Channel channel = (Channel) baseDAO;

        byte[] raw = java.util.Base64.getDecoder().decode(((Channel) channelPane.getData()).getLogo());

        name.setText(channel.getName());
        foundationDate.setValue(channel.getFoundationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        if (channel.getDestructionDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear() != 2149)
            destructionDate.setValue(channel.getDestructionDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        owner.setText(channel.getOwner());
        try {
            logo.setImage(SwingFXUtils.toFXImage(ImageIO.read(new ByteArrayInputStream(raw)), null));
        } catch (IOException e) {
            e.printStackTrace();
        }
        airtime.setText(channel.getAirtime());
        city.setText(channel.getCity());
        description.setText(channel.getDescription());
        frequency.setText(channel.getFrequency());
        satellite.setText((channel.getSatellite() != null) ? channel.getSatellite() : "");

        ChangeChecker.hasChanged(false);
    }

    private Channel getFieldsData(){
        Channel channel = new Channel();

        channel.setName(name.getText());
        channel.setFoundationDate(Date.from(foundationDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        channel.setDestructionDate(destructionDate.getValue() != null ? Date.from(destructionDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()) : null);
        channel.setOwner(owner.getText());
        if (logo.getImage() == null)
            channel.setLogo(null);
        else {
            try {
                BufferedImage bImage = SwingFXUtils.fromFXImage(logo.getImage(), null);
                ByteArrayOutputStream s = new ByteArrayOutputStream();
                ImageIO.write(bImage, "png", s);
                byte[] res = s.toByteArray();
                s.close();
                channel.setLogo(java.util.Base64.getEncoder().encodeToString(res));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        channel.setAirtime(airtime.getText());
        channel.setCity(city.getText());
        channel.setDescription(description.getText());
        channel.setFrequency(frequency.getText());
        channel.setSatellite(satellite.getText());

        return channel;
    }

    @FXML
    void chooseImage(MouseEvent event) {
        String imageFile;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Вибір зображення");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.bmp", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(btnChooseLogo.getScene().getWindow());
        if (selectedFile != null) {
            try {
                imageFile = selectedFile.toURI().toURL().toString();
                logo.setImage(new Image(imageFile));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

    }

    private ChangeListener foundationDateListener = (observable, oldValue, newValue) -> {
        if (oldValue != newValue) {
            Callback<DatePicker, DateCell> dayCellFactoryEnd = new Callback<DatePicker, DateCell>() {
                public DateCell call(final DatePicker datePicker) {
                    return new DateCell() {
                        @Override
                        public void updateItem(LocalDate item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item.isBefore(foundationDate.getValue())) {
                                this.setDisable(true);
                            }
                        }
                    };
                }
            };
            destructionDate.setDayCellFactory(dayCellFactoryEnd);
        }
    };

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        snackbar = new JFXSnackbar(pane);
        cancelCounter = 0;

        btnSave.setTooltip(new Tooltip("Зберегти"));
        btnCancel.setTooltip(new Tooltip("Скасувати"));

        Pattern namePattern = Pattern.compile("[0-9a-zA-Zа-яА-яіІїЇєЄ\\-\\s'!+:]{0,45}");
        TextFormatter nameFormatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return namePattern.matcher(change.getControlNewText()).matches() ? change : null;
        });
        name.setTextFormatter(nameFormatter);

        name.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                FieldsValidation.textFieldIsNotEmpty(name);
            }
        });

        Callback<DatePicker, DateCell> dayCellFactoryFoundation = new Callback<DatePicker, DateCell>() {
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(LocalDate.of(1936, 11, 2))) {
                            this.setDisable(true);
                        }
                    }
                };
            }
        };
        foundationDate.setDayCellFactory(dayCellFactoryFoundation);

        foundationDate.valueProperty().addListener(foundationDateListener);

        Pattern ownerPattern = Pattern.compile("[а-яА-яіІїЇєЄ\\-\\s'.,]{0,45}");
        TextFormatter ownerFormatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return ownerPattern.matcher(change.getControlNewText()).matches() ? change : null;
        });
        owner.setTextFormatter(ownerFormatter);

        owner.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                FieldsValidation.textFieldIsNotEmpty(owner);
            }
        });

        Pattern airtimePattern = Pattern.compile("\\d{0,2}|\\d+/\\d?|\\d");
        TextFormatter airtimeFormatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return airtimePattern.matcher(change.getControlNewText()).matches() ? change : null;
        });
        airtime.setTextFormatter(airtimeFormatter);

        airtime.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                FieldsValidation.textFieldIsNotEmpty(airtime);
            }
        });

        Pattern cityPattern = Pattern.compile("[а-яА-яіІїЇєЄ\\-\\s']{0,45}");
        TextFormatter cityFormatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return cityPattern.matcher(change.getControlNewText()).matches() ? change : null;
        });
        city.setTextFormatter(cityFormatter);

        city.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                FieldsValidation.textFieldIsNotEmpty(city);
            }
        });

        Pattern frequencyPattern = Pattern.compile("[1-9]?|[1-9]\\d{0,4}|\\d+\\.\\d{0,2}");
        TextFormatter frequencyFormatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return frequencyPattern.matcher(change.getControlNewText()).matches() ? change : null;
        });
        frequency.setTextFormatter(frequencyFormatter);

        frequency.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                FieldsValidation.textFieldIsNotEmpty(frequency);
            }
        });

        Pattern satellitePattern = Pattern.compile("[a-zA-Z0-9\\-\\s]{0,10}");
        TextFormatter satelliteFormatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return satellitePattern.matcher(change.getControlNewText()).matches() ? change : null;
        });
        satellite.setTextFormatter(satelliteFormatter);

        satellite.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                FieldsValidation.textFieldIsNotEmpty(satellite);
            }
        });

        name.textProperty().addListener(ChangeChecker.textListener);
        owner.textProperty().addListener(ChangeChecker.textListener);
        logo.imageProperty().addListener(ChangeChecker.valueListener);
        airtime.textProperty().addListener(ChangeChecker.textListener);
        city.textProperty().addListener(ChangeChecker.textListener);
        description.textProperty().addListener(ChangeChecker.textListener);
        frequency.textProperty().addListener(ChangeChecker.textListener);
        satellite.textProperty().addListener(ChangeChecker.textListener);

        ChangeChecker.hasChanged(false);

        Platform.runLater(() -> {
            Logger.logInfo("String", channelPane.getData().toString());
            switch (channelPane.getType()) {
                case EDIT:
                    setFieldsValues(channelPane.getData());
                    channelLabel.setText("Редагувати телеканал");
                    break;
                default:
                    channelLabel.setText("Додати телеканал");
                    break;
            }
        });
    }

    private boolean check() {
        if (FieldsValidation.textFieldIsNotEmpty(name) && FieldsValidation.textFieldIsNotEmpty(owner) &&
                FieldsValidation.textFieldIsNotEmpty(airtime) && FieldsValidation.textFieldIsNotEmpty(city) &&
                FieldsValidation.textFieldIsNotEmpty(frequency)) {
            if (ChangeChecker.hasChanged()) {
                save();
                return true;
            }
            else
                snackbar.show("Запис не містить змін!", 2000);
        }
        return false;
    }

    private void save() {
//        if (mode.equals("Редагувати"))
//            snackbar.show("Запис успішно відредаговано!", 2000);
//        else snackbar.show("Запис успішно додано до бази даних!", 2000);
    }

    private boolean cancel() {
        if ((!channelPane.getType().equals(EDIT) && ChangeChecker.hasChanged()) ||
                (channelPane.getType().equals(EDIT) && ChangeChecker.hasChanged() && (FieldsValidation.textFieldIsNotEmpty(name) || FieldsValidation.textFieldIsNotEmpty(owner) ||
                        FieldsValidation.textFieldIsNotEmpty(airtime) || FieldsValidation.textFieldIsNotEmpty(city) || FieldsValidation.textFieldIsNotEmpty(frequency) ||
                        FieldsValidation.textFieldIsNotEmpty(satellite)))) {
            snackbar.show("Запис містить зміни! Збережіть їх або\nскасуйте, натиснувши ще раз \"Скасувати\"!", 2000);
            return false;
        }
        return true;
    }

}
