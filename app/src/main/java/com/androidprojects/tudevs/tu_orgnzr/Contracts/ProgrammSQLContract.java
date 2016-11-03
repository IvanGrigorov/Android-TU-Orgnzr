package com.androidprojects.tudevs.tu_orgnzr.Contracts;

import android.provider.BaseColumns;

/**
 * Created by Ivan Grigorov on 20/04/2016.
 * This Contract is created to work easier with Programm and Event Database
 */
public class ProgrammSQLContract {

    public static final class SubjectTable implements BaseColumns {

        public static final String TABLE_NAME = "Subjects";

        public static final String SUBJECT_ID_COLUMN = "subject_id";
        public static final String LECTURE_NAME_COLUMN = "lecture";
        public static final String DAY_OF_WEEK_COLUMN = "day";
        public static final String STARTS_AT_COLUMN = "starts";
        public static final String ENDS_AT_COLUMN = "ends";
        public static final String BUILDING_COLUMN = "building";
    }

    public static final class EventsTable implements BaseColumns {

        public static final String TABLE_NAME = "Events";

        public static final String EVENTS_NAME_COLUMN = "EventName";
        public static final String EVENT_DESCRIPTION = "EventDescription";
        public static final String EVENT_DATE = "EventDate";
    }
}
