package com.androidprojects.tudevs.tu_orgnzr.Config;

/**
 * Created by Ivan Grigorov on 15/04/2016.
 * Sets Configuration for the whole application
 */
public final class Config {

    // Sets name for the shared preferences file of the application
    public static final String USER_SHARED_PREFERENCES = "USER_PREFERENCES";


    // Shared preferences config:

    // Set key index to the shared preferences
    public final static String key = "username";

    // Set key default value to the shared preferences -> username
    public final static String defaultValue = "NotExist";

    // Set key index to the shared event preferences
    public final static String eventKey = "EventNumber";

    // Database config:

    // Holds the initial version of the Database
    public static int DATABASE_VERSION = 1;

    // Holds the name of the Database
    public static final String DATABASE_NAME = "Organizer.db";



}
