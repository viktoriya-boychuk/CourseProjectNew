package wrappedDAO;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dao.BaseDAO;
import dao.Hosting;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HostingWrapped extends RecursiveTreeObject<HostingWrapped> {
    private IntegerProperty id;
    private StringProperty name;
    private StringProperty contractBeginDate;
    private StringProperty contractEndDate;
    private StringProperty announcerGratuity;
    private IntegerProperty announcerID;
    private IntegerProperty programID;

    private Hosting mHosting;

    public HostingWrapped(BaseDAO baseDAO) {
        Hosting hosting = (Hosting) baseDAO;
        mHosting = hosting;
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.YYYY");

        this.id = new SimpleIntegerProperty(hosting.getId());
        this.name = new SimpleStringProperty(hosting.getName());
        this.contractBeginDate = new SimpleStringProperty(formatter.format(hosting.getContractBeginDate()));
        this.contractEndDate = new SimpleStringProperty((hosting.getContractEndDate().getYear() == 249) ? "" : formatter.format(hosting.getContractEndDate()));
        this.announcerGratuity = new SimpleStringProperty((hosting.getAnnouncerGratuity() == 0.0) ? "" : String.valueOf(hosting.getAnnouncerGratuity()));
        this.announcerID = new SimpleIntegerProperty(hosting.getAnnouncerID());
        this.programID = new SimpleIntegerProperty(hosting.getProgramID());
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

    public String getContractBeginDate() {
        return contractBeginDate.get();
    }

    public StringProperty contractBeginDateProperty() {
        return contractBeginDate;
    }

    public String getContractEndDate() {
        return contractEndDate.get();
    }

    public StringProperty contractEndDateProperty() {
        return contractEndDate;
    }

    public String getAnnouncerGratuity() {
        return announcerGratuity.get();
    }

    public StringProperty announcerGratuityProperty() {
        return announcerGratuity;
    }

    public int getAnnouncerID() {
        return announcerID.get();
    }

    public IntegerProperty announcerIDProperty() {
        return announcerID;
    }

    public int getProgramID() {
        return programID.get();
    }

    public IntegerProperty programIDProperty() {
        return programID;
    }

    public Hosting getHosting() {
        return mHosting;
    }

    public static ObservableList<HostingWrapped> wrap(ArrayList<BaseDAO> hostings) {
        List<HostingWrapped> hostingWrappedList = new ArrayList<>();
        for (BaseDAO hosting : hostings) {
            HostingWrapped wrappedAnnouncer = new HostingWrapped(hosting);
            hostingWrappedList.add(wrappedAnnouncer);
        }
        return FXCollections.observableArrayList(hostingWrappedList);
    }
}
