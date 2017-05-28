package com.androidprojects.tudevs.tu_orgnzr.RoomLibraryDAO.DAOContractors;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.androidprojects.tudevs.tu_orgnzr.RoomLibraryDAO.EventsDAO;

/**
 * Created by Ivan Grigorov on 24/05/2017.
 * Contact at ivangrigorov9 at gmail.com
 */

@Dao
public interface EvntDAOContractor {

    @Insert
    void insertEvent(EventsDAO eventsDAO);

    @Query("SELECT * FROM events_table ORDER BY eventDate_column DESC")
    EventsDAO[] returnAllEvents();

    @Query("SELECT * FROM events_table ORDER BY eventDate_column ASC LIMIT 1")
    EventsDAO returnLatestEvent();


}
