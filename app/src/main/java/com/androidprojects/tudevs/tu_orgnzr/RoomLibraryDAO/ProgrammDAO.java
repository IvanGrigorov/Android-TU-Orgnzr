package com.androidprojects.tudevs.tu_orgnzr.RoomLibraryDAO;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "program_table")
public class ProgrammDAO {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "lecture_column")
    private String lecture;

    @ColumnInfo(name = "dayOfWeek_Column")
    private String dayOfWeek;

    @ColumnInfo(name = "startsAt_column")
    private String start;

    @ColumnInfo(name = "endsAt_column")
    private String end;

    @ColumnInfo(name = "building_column")
    private String building;

    public ProgrammDAO(String lecture, String dayOfWeek, String start, String end, String building) {
        this.lecture = lecture;
        this.dayOfWeek = dayOfWeek;
        this.start = start;
        this.end = end;
        this.building = building;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLecture() {
        return lecture;
    }

    public void setLecture(String lecture) {
        this.lecture = lecture;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }
}