package com.androidprojects.tudevs.tu_orgnzr.RoomLibraryDAO;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "events_table")
public class EventsDAO {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "eventName_column")
    private String eventName;

    @ColumnInfo(name = "eventDescription_column")
    private String eventDescription;

    @ColumnInfo(name = "eventDate_column")
    private String eventDate;

    public EventsDAO(String eventName, String eventDescription, String eventDate) {
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.eventDate = eventDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }
}
