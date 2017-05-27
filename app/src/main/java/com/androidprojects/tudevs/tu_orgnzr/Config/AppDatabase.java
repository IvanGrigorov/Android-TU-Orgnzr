package com.androidprojects.tudevs.tu_orgnzr.Config;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.androidprojects.tudevs.tu_orgnzr.RoomLibraryDAO.DAOContractors.EvntDAOContractor;
import com.androidprojects.tudevs.tu_orgnzr.RoomLibraryDAO.DAOContractors.ProgrammDAOContractor;
import com.androidprojects.tudevs.tu_orgnzr.RoomLibraryDAO.EventsDAO;
import com.androidprojects.tudevs.tu_orgnzr.RoomLibraryDAO.ProgrammDAO;

@Database(entities = {ProgrammDAO.class, EventsDAO.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProgrammDAOContractor programmDAOContractor();

    public abstract EvntDAOContractor eventsDAOContractor();

}