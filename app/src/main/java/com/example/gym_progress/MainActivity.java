package com.example.gym_progress;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private LocalDate selectedDate;

    Button addNewWorkout, J;

    DatabaseWorkout myDB;
    ArrayList<String> workout_names;

    //--------Calendar Data View--------------------
    ArrayList<Integer> day, month, year, amount_of_sets, amount_of_reps;


    //----------------Edit option from textView------------------
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addNewWorkout = (Button) findViewById(R.id.add_new_workout);
        addNewWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToEditWorkoutActivity();
            }
        });

        //----------SQL init Calendar View---------------
        day = new ArrayList<>();
        month = new ArrayList<>();
        year = new ArrayList<>();
        amount_of_reps = new ArrayList<>();
        amount_of_sets = new ArrayList<>();

        //-------------SQL init-----------------------

        myDB = new DatabaseWorkout(MainActivity.this);
        workout_names = new ArrayList<>();
        storeDataInArrays();

        storeCalendarDataInArrays();

        RecyclerView workoutsRecyclerView = findViewById(R.id.mainWorkoutRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        workoutsRecyclerView.setLayoutManager(layoutManager);

        MainWorkoutAdapter adapter = new MainWorkoutAdapter(MainActivity.this, workout_names);
        adapter.setOnItemClickListener(new MainWorkoutAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String currentQueryToAdd = workout_names.get(position);
                Log.d("HHHHHHHHHHHHHHHHHHHHH", currentQueryToAdd);
            }
        });
        workoutsRecyclerView.setAdapter(adapter);

        initWidgets();
        selectedDate = LocalDate.now();
        setMonthView();

    }


    private void goToEditWorkoutActivity() {
        Intent  intent = new Intent(this, EditWorkout.class); // Second arguments says which file it'll head to
        String textToSend = EditTitles.Routine1Text;
        EditTitles.RoutineSelected = textToSend;
        startActivity(intent);
    }

    //------------- SET MONTH VIEW---------------------
    private void setMonthView() {
        monthYearText.setText(monthYearFromDate(selectedDate)); // Set current text of the current month
        ArrayList<String>daysInMonth = daysInMonthArray(selectedDate);

        // This one edited for the inputs
        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    //--------------DAYS IN MONTH ARRAY-------------------
    private ArrayList<String> daysInMonthArray(LocalDate date) {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for(int i = 1; i <= 42; i++)
        {
            if(i <= dayOfWeek || i > daysInMonth + dayOfWeek)
            {
                daysInMonthArray.add("");
            }
            else
            {
                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
            }
        }
        return  daysInMonthArray;
    }

    // --------------FORMATTER---------------------
    // This class returns a string about how got extract the Month String
    private String monthYearFromDate(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    private void initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);

    }

    public void previousMonthAction(View view){
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
    }
    public void nextMonthAction(View view){
        selectedDate = selectedDate.plusMonths(1);
        setMonthView();
    }


    //----------------SQL------------------------
    public void storeDataInArrays(){
        Cursor cursor = myDB.readAllDataWorkoutNames("workoutPackages");
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                workout_names.add(cursor.getString(0)); // Adds the distinct workouts
                addToWorkCalendarDataTable();
            }
        }
    }

    private void addToWorkCalendarDataTable() {

    }

    //--------------UPDATING CALENDAR ON CALENDAR VIEWz---------------------
    public void storeCalendarDataInArrays(){
        // Stores
        Cursor cursor = myDB.readAllDataCalendarData("workoutCalendarData");
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                day.add(cursor.getInt(1));
                month.add(cursor.getInt(2));
                year.add(cursor.getInt(3));
                amount_of_reps.add(cursor.getInt(4));
                amount_of_sets.add(cursor.getInt(5));
            }
        }
    }


}