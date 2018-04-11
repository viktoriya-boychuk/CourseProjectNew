package wrappedDAO;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dao.Announcer;
import dao.BaseDAO;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AnnouncerWrapped  extends RecursiveTreeObject<AnnouncerWrapped> {
    private IntegerProperty id;
    private StringProperty name;
    private StringProperty careerBeginYear;
    private StringProperty careerEndYear;
    private StringProperty birthDate;
    private StringProperty sex;
    private StringProperty education;
    private StringProperty description;

    private Announcer mAnnouncer;

    public AnnouncerWrapped(BaseDAO baseDAO) {
        Announcer announcer = (Announcer) baseDAO;
        mAnnouncer = announcer;
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.YYYY");

        this.id = new SimpleIntegerProperty(announcer.getId());
        this.name = new SimpleStringProperty(announcer.getName());
        this.careerBeginYear = new SimpleStringProperty(Integer.toString(announcer.getCareerBeginYear()));
        this.careerEndYear = new SimpleStringProperty(Integer.toString(announcer.getCareerEndYear()));
        this.birthDate = new SimpleStringProperty(formatter.format(announcer.getBirthDate()));
        this.sex = new SimpleStringProperty(announcer.getSex());
        this.education = new SimpleStringProperty(announcer.getEducation());
        this.description = new SimpleStringProperty(announcer.getDescription());
    }

    public Announcer getAnnouncer() {
        return mAnnouncer;
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getCareerBeginYear() {
        return careerBeginYear.get();
    }

    public StringProperty careerBeginYearProperty() {
        return careerBeginYear;
    }

    public String getCareerEndYear() {
        return careerEndYear.get();
    }

    public StringProperty careerEndYearProperty() {
        return careerEndYear;
    }

    public String getBirthDate() {
        return birthDate.get();
    }

    public StringProperty birthDateProperty() {
        return birthDate;
    }

    public String getSex() {
        return sex.get();
    }

    public StringProperty sexProperty() {
        return sex;
    }

    public String getEducation() {
        return education.get();
    }

    public StringProperty educationProperty() {
        return education;
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public static ObservableList<AnnouncerWrapped> wrap(ArrayList<BaseDAO> announcers) {
        List<AnnouncerWrapped> announcerWrappedList = new ArrayList<>();
        for (BaseDAO announcer : announcers) {
            AnnouncerWrapped wrappedAnnouncer = new AnnouncerWrapped(announcer);
            announcerWrappedList.add(wrappedAnnouncer);
        }
        return FXCollections.observableArrayList(announcerWrappedList);
    }
}
