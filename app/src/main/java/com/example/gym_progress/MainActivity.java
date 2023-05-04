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

    Button addNewWorkout;

    DatabaseWorkout myDB;
    ArrayList<String> workout_names;



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

        initWidgets();
        selectedDate = LocalDate.now();
        setMonthView();

        //-------------SQL init-----------------------

        myDB = new DatabaseWorkout(MainActivity.this);
        workout_names = new ArrayList<>();
        storeDataInArrays();

        RecyclerView workoutsRecyclerView = findViewById(R.id.mainWorkoutRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        workoutsRecyclerView.setLayoutManager(layoutManager);

        MainWorkoutAdapter adapter = new MainWorkoutAdapter(MainActivity.this, workout_names);
        workoutsRecyclerView.setAdapter(adapter);


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

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth); // This one edited
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
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                workout_names.add(cursor.getString(0)); // Adds the distinct workouts
            }
        }
    }

    public void updateCalendar(){

    }






}