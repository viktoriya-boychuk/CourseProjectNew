package wrappedDAO;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dao.BaseDAO;
import dao.Program;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class ProgramWrapped extends RecursiveTreeObject<ProgramWrapped> {
    private IntegerProperty id;
    private StringProperty name;
    private StringProperty category;
    private StringProperty genre;
    private IntegerProperty duration;
    private StringProperty country;
    private StringProperty authorOrProducer;
    private StringProperty description;
    private StringProperty originality;
    private IntegerProperty audienceID;

    private Program mProgram;

    public ProgramWrapped(BaseDAO baseDAO) {
        Program program = (Program) baseDAO;
        mProgram = program;

        this.id = new SimpleIntegerProperty(program.getId());
        this.name = new SimpleStringProperty(program.getName());
        this.category = new SimpleStringProperty(program.getCategory());
        this.genre = new SimpleStringProperty(program.getGenre().equals("null")? "" : program.getGenre());
        this.duration = new SimpleIntegerProperty(program.getDuration());
        this.country = new SimpleStringProperty(program.getCountry());
        this.authorOrProducer = new SimpleStringProperty(program.getAuthorOrProducer());
        this.description = new SimpleStringProperty(program.getDescription());
        this.originality = new SimpleStringProperty(program.getOriginality() ? "Власна ідея" : "Чужа ідея");
        this.audienceID = new SimpleIntegerProperty(program.getAudienceID());
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

    public String getCategory() {
        return category.get();
    }

    public StringProperty categoryProperty() {
        return category;
    }

    public String getGenre() {
        return genre.get();
    }

    public StringProperty genreProperty() {
        return genre;
    }

    public int getDuration() {
        return duration.get();
    }

    public IntegerProperty durationProperty() {
        return duration;
    }

    public String getCountry() {
        return country.get();
    }

    public StringProperty countryProperty() {
        return country;
    }

    public String getAuthorOrProducer() {
        return authorOrProducer.get();
    }

    public StringProperty authorOrProducerProperty() {
        return authorOrProducer;
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public String getOriginality() {
        return originality.get();
    }

    public StringProperty originalityProperty() {
        return originality;
    }

    public int getAudienceID() {
        return audienceID.get();
    }

    public IntegerProperty audienceIDProperty() {
        return audienceID;
    }

    public Program getProgram() {
        return mProgram;
    }

    public static ObservableList<ProgramWrapped> wrap(ArrayList<BaseDAO> hostings) {
        List<ProgramWrapped> programWrappedList = new ArrayList<>();
        for (BaseDAO hosting : hostings) {
            ProgramWrapped wrappedProgram = new ProgramWrapped(hosting);
            programWrappedList.add(wrappedProgram);
        }
        return FXCollections.observableArrayList(programWrappedList);
    }
}
