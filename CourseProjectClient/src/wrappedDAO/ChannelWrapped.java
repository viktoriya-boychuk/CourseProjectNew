package wrappedDAO;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dao.BaseDAO;
import dao.Channel;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChannelWrapped extends RecursiveTreeObject<ChannelWrapped> {
    private IntegerProperty id;
    private StringProperty name;
    private StringProperty foundationDate;
    private StringProperty destructionDate;
    private StringProperty owner;
    private StringProperty logo;
    private StringProperty airtime;
    private StringProperty city;
    private StringProperty description;
    private StringProperty frequency;
    private StringProperty satellite;

    private Channel mChannel;

    public ChannelWrapped(BaseDAO baseDAO) {
        Channel channel = (Channel) baseDAO;
        mChannel = channel;
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.YYYY");

        this.id = new SimpleIntegerProperty(channel.getId());
        this.name = new SimpleStringProperty(channel.getName());
        this.foundationDate = new SimpleStringProperty(formatter.format(channel.getFoundationDate()));
        this.destructionDate = new SimpleStringProperty((channel.getDestructionDate().getYear() == 249) ? "" : formatter.format(channel.getDestructionDate()));
        this.owner = new SimpleStringProperty(channel.getOwner());
        this.logo = new SimpleStringProperty(channel.getLogo());
        this.airtime = new SimpleStringProperty(channel.getAirtime());
        this.city = new SimpleStringProperty(channel.getCity());
        this.description = new SimpleStringProperty(channel.getDescription());
        this.frequency = new SimpleStringProperty(channel.getFrequency());
        this.satellite = new SimpleStringProperty(channel.getSatellite());
    }

    public Channel getChannel() {
        return mChannel;
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

    public String getFoundationDate() {
        return foundationDate.get();
    }

    public StringProperty foundationDateProperty() {
        return foundationDate;
    }

    public String getDestructionDate() {
        return destructionDate.get();
    }

    public StringProperty destructionDateProperty() {
        return destructionDate;
    }

    public String getOwner() {
        return owner.get();
    }

    public StringProperty ownerProperty() {
        return owner;
    }

    public String getLogo() {
        return logo.get();
    }

    public StringProperty logoProperty() {
        return new SimpleStringProperty("LOGO HERE");
    }

    public String getAirtime() {
        return airtime.get();
    }

    public StringProperty airtimeProperty() {
        return airtime;
    }

    public String getCity() {
        return city.get();
    }

    public StringProperty cityProperty() {
        return city;
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public String getFrequency() {
        return frequency.get();
    }

    public StringProperty frequencyProperty() {
        return frequency;
    }

    public String getSatellite() {
        return satellite.get();
    }

    public StringProperty satelliteProperty() {
        return satellite;
    }

    public static ObservableList<ChannelWrapped> wrap(ArrayList<BaseDAO> channels) {
        List<ChannelWrapped> channelWrappedList = new ArrayList<>();
        for (BaseDAO channel : channels) {
            ChannelWrapped wrappedAnnouncer = new ChannelWrapped(channel);
            channelWrappedList.add(wrappedAnnouncer);
        }
        return FXCollections.observableArrayList(channelWrappedList);
    }
}
