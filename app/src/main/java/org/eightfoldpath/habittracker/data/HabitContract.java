package org.eightfoldpath.habittracker.data;

import android.provider.BaseColumns;

/**
 * Created by rick on 2/18/17.
 */

public final class HabitContract implements BaseColumns {

    // Privatize the constructor to prevent instantiation.
    private HabitContract() {}

    public static final class HabitEntry implements BaseColumns {

        public final static String TABLE_NAME = "habits";

        public final static String _ID = BaseColumns._ID;

        public final static String COLUMN_NAME ="name";

        public final static String COLUMN_GOAL = "goal";

        public final static String COLUMN_WEEKLY_COMMITMENT = "weekly_commitment";

    }
}
