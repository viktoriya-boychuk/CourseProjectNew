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

import java.util.ArrayList;
import java.util.List;

public class AnnouncerWrapped  extends RecursiveTreeObject<AnnouncerWrapped> {
    private IntegerProperty id;
    private StringProperty name;
    private IntegerProperty careerBeginYear;
    private IntegerProperty careerEndYear;
    private StringProperty birthDate;
    private StringProperty education;
    private StringProperty description;

    private Announcer mAnnouncer;

    public AnnouncerWrapped(BaseDAO baseDAO) {
        Announcer announcer = (Announcer) baseDAO;
        mAnnouncer = announcer;

        this.id = new SimpleIntegerProperty(announcer.getId());
        this.name = new SimpleStringProperty(announcer.getName());
        this.careerBeginYear = new SimpleIntegerProperty(announcer.getCareerBeginYear());
        this.careerEndYear = new SimpleIntegerProperty(announcer.getCareerEndYear());
        this.birthDate = new SimpleStringProperty(announcer.getBirthDate().toString());
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

    public int getCareerBeginYear() {
        return careerBeginYear.get();
    }

    public IntegerProperty careerBeginYearProperty() {
        return careerBeginYear;
    }

    public int getCareerEndYear() {
        return careerEndYear.get();
    }

    public IntegerProperty careerEndYearProperty() {
        return careerEndYear;
    }

    public String getBirthDate() {
        return birthDate.get();
    }

    public StringProperty birthDateProperty() {
        return birthDate;
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
