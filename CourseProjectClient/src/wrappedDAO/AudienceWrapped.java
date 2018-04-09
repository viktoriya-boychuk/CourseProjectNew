package wrappedDAO;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dao.Audience;
import dao.BaseDAO;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class AudienceWrapped extends RecursiveTreeObject<AudienceWrapped> {
    private IntegerProperty id;
    private StringProperty name;
    private StringProperty ageCategory;
    private StringProperty description;
    private StringProperty emblem;

    private Audience mAudience;

    public Audience getAudience() {
        return mAudience;
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getAgeCategory() {
        return ageCategory.get();
    }

    public StringProperty ageCategoryProperty() {
        return ageCategory;
    }

    public void setAgeCategory(String ageCategory) {
        this.ageCategory.set(ageCategory);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getEmblem() {
        return emblem.get();
    }

    public StringProperty emblemProperty() {
        return emblem;
    }

    public void setEmblem(String emblem) {
        this.emblem.set(emblem);
    }

    public AudienceWrapped(BaseDAO baseDAO) {
        Audience audience = (Audience) baseDAO;
        mAudience = audience;

        this.id = new SimpleIntegerProperty(audience.getId());
        this.name = new SimpleStringProperty(audience.getName());
        this.ageCategory = new SimpleStringProperty(audience.getAgeCategory());
        this.description = new SimpleStringProperty(audience.getDescription());
        this.emblem = new SimpleStringProperty(audience.getEmblem());
    }

    public static ObservableList<AudienceWrapped> wrap(ArrayList<BaseDAO> audiences) {
        List<AudienceWrapped> audienceWrappedList = new ArrayList<>();
        for (BaseDAO audience : audiences) {
            AudienceWrapped wrappedAudience = new AudienceWrapped(audience);
            audienceWrappedList.add(wrappedAudience);
        }
        return FXCollections.observableArrayList(audienceWrappedList);
    }
}
