package com.example.gym_progress;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseWorkout extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Workout.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "WorkoutPackages";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_WORKOUT_NAME = "name";
    private static final String COLUMN_EXERCISE = "exercise";
    private static final String COLUMN_SETS = "sets";
    private static final String COLUMN_REPS = "repetitions";
    private static final String COLUMN_NO_OF_WORKOUT = "workout_no";

    DatabaseWorkout(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +" (" + COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NO_OF_WORKOUT + " INTEGER, "+ COLUMN_WORKOUT_NAME + " TEXT, "
                + COLUMN_EXERCISE + " TEXT, " + COLUMN_SETS + " INTEGER, " + COLUMN_REPS + " INTEGER)";
        db.execSQL(query);

        String query2 = "CREATE TABLE IF NOT EXISTS WorkoutCalendarData ( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "day INTEGER, " +
                "month INTEGER, " +
                "year INTEGER, " +
                "sets INTEGER, reps INTEGER)";

        db.execSQL(query);
        db.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addWorkout(int workout_number, String name, String exercise, int sets, int reps){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NO_OF_WORKOUT, workout_number);
        cv.put(COLUMN_WORKOUT_NAME, name);
        cv.put(COLUMN_EXERCISE, exercise); // Now selected as the planets
        cv.put(COLUMN_SETS, sets);
        cv.put(COLUMN_REPS, reps);
        long result = db.insert(TABLE_NAME, null, cv);

        if (result == -1){
            Toast.makeText(context, "Failed to upload data", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Added successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readAllDataWorkoutNames(String tableSelected){
        String query = "select DISTINCT name from " + tableSelected;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor readAllDataCalendarData(String tableSelected){
        String query = "select DISTINCT * from " + tableSelected;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }


}
