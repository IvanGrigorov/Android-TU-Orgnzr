package com.androidprojects.tudevs.tu_orgnzr.Models.ProgrammModels;

/**
 * Created by Ivan Grigorov on 23/04/2016.
 * Represents a programm row
 */
public class ProgrammRow {

    private String lectureName;
    private String lectureStarts;
    private String lectureEnds;
    private String dayOfWeek;

    public ProgrammRow(String lectureName, String lectureStarts, String lectureEnds, String dayOfWeek) {
        this.lectureName = lectureName;
        this.lectureStarts = lectureStarts;
        this.lectureEnds = lectureEnds;
        this.dayOfWeek = dayOfWeek;
    }

    public String getLectureName() {

        return lectureName;
    }

    public void setLectureName(String lectureName) {
        this.lectureName = lectureName;
    }

    public String getLectureStarts() {
        return lectureStarts;
    }

    public void setLectureStarts(String lectureStarts) {
        this.lectureStarts = lectureStarts;
    }

    public String getLectureEnds() {
        return lectureEnds;
    }

    public void setLectureEnds(String lectureEnds) {
        this.lectureEnds = lectureEnds;
    }

    public String getdayOfWeek() {
        return dayOfWeek;
    }

    public void setdayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}
