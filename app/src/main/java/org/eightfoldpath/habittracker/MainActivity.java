package org.eightfoldpath.habittracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.eightfoldpath.habittracker.data.HabitDbHelper;
import org.eightfoldpath.habittracker.data.HabitContract.HabitEntry;

public class MainActivity extends AppCompatActivity {

    /** Database helper that will provide us access to the database */
    private HabitDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new HabitDbHelper(this);
        createHabit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayHabits();
    }

    private Cursor displayHabits() {

        // Create and/or open a database to read from it
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                HabitEntry._ID,
                HabitEntry.COLUMN_NAME,
                HabitEntry.COLUMN_GOAL,
                HabitEntry.COLUMN_WEEKLY_COMMITMENT };

        Cursor cursor = db.query(
                HabitEntry.TABLE_NAME,   // The table to query
                columns,                 // The columns to return
                null,                    // The columns for the WHERE clause
                null,                    // The values for the WHERE clause
                null,                    // Don't group the rows
                null,                    // Don't filter by row groups
                null);                   // The sort order

        TextView displayView = (TextView) findViewById(R.id.habits);

        try {
            displayView.append(HabitEntry._ID + " - " +
                    HabitEntry.COLUMN_NAME + " - " +
                    HabitEntry.COLUMN_GOAL + " - " +
                    HabitEntry.COLUMN_WEEKLY_COMMITMENT + "\n");

            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int ID = cursor.getInt(cursor.getColumnIndex(HabitEntry._ID));
                String name = cursor.getString(cursor.getColumnIndex(HabitEntry.COLUMN_NAME));
                String goal = cursor.getString(cursor.getColumnIndex(HabitEntry.COLUMN_GOAL));
                int weeklyCommitment = cursor.getInt(cursor.getColumnIndex(HabitEntry.COLUMN_WEEKLY_COMMITMENT));
                displayView.append(("\n" + ID + " - " +
                        name + " - " +
                        goal + " - " +
                        weeklyCommitment));
            }
        } finally {
            cursor.close();
        }

        return cursor;
    }

    /**
     * Helper method to insert hardcoded pet data into the database. For debugging purposes only.
     */
    private void createHabit() {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_NAME, "Run");
        values.put(HabitEntry.COLUMN_GOAL, "3 Miles");
        values.put(HabitEntry.COLUMN_WEEKLY_COMMITMENT, 3);

        long newRowId = db.insert(HabitEntry.TABLE_NAME, null, values);
    }
}
