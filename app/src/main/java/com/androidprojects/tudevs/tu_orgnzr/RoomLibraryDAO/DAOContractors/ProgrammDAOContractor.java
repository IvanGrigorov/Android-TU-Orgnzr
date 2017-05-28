package com.androidprojects.tudevs.tu_orgnzr.RoomLibraryDAO.DAOContractors;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.androidprojects.tudevs.tu_orgnzr.RoomLibraryDAO.ProgrammDAO;

/**
 * Created by Ivan Grigorov on 24/05/2017.
 * Contact at ivangrigorov9 at gmail.com
 */

@Dao
public interface ProgrammDAOContractor {

    @Insert
    void insertSubject(ProgrammDAO programmDAO);

    @Query("SELECT * FROM program_table WHERE dayOfWeek_Column = :currentDay")
    ProgrammDAO[] returnAllSubjects(String currentDay);

    @Query("SELECT * FROM program_table WHERE startsAt_column LIKE :startsAt")
    ProgrammDAO returnCurrenrLecture(String startsAt);


}
