package wrappedDAO;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dao.BaseDAO;
import dao.Schedule;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class ScheduleWrapped extends RecursiveTreeObject<ScheduleWrapped> {
    private IntegerProperty id;
    private StringProperty name;
    private StringProperty date;
    private StringProperty time;
    private IntegerProperty channelID;
    private IntegerProperty programID;

    private Schedule mSchedule;

    public ScheduleWrapped(BaseDAO baseDAO) {
        Schedule schedule = (Schedule) baseDAO;
        mSchedule = schedule;

        this.id = new SimpleIntegerProperty(schedule.getId());
        this.name = new SimpleStringProperty(schedule.getName());
        this.date = new SimpleStringProperty(schedule.getDate().toString());
        this.time = new SimpleStringProperty(schedule.getTime().toString());
        this.channelID = new SimpleIntegerProperty(schedule.getChannelID());
        this.programID = new SimpleIntegerProperty(schedule.getProgramID());
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

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public String getTime() {
        return time.get();
    }

    public StringProperty timeProperty() {
        return time;
    }

    public int getChannelID() {
        return channelID.get();
    }

    public IntegerProperty channelIDProperty() {
        return channelID;
    }

    public int getProgramID() {
        return programID.get();
    }

    public IntegerProperty programIDProperty() {
        return programID;
    }

    public Schedule getSchedule() {
        return mSchedule;
    }

    public static ObservableList<ScheduleWrapped> wrap(ArrayList<BaseDAO> schedules) {
        List<ScheduleWrapped> scheduleWrappedList = new ArrayList<>();
        for (BaseDAO schedule : schedules) {
            ScheduleWrapped wrappedProgram = new ScheduleWrapped(schedule);
            scheduleWrappedList.add(wrappedProgram);
        }
        return FXCollections.observableArrayList(scheduleWrappedList);
    }
}
