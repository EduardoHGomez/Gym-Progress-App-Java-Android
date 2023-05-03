package com.example.gym_progress;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EditWorkout extends AppCompatActivity {

    Button return_button, save_button, add_spinner_item;
    TextView setTitle;
    Spinner spinner, spinner_sets, spinner_reps;
    EditText editWorkoutName;

    RecyclerView recyclerView;

    //----------------Adapter variables-----------
    WorkoutAdapter adapter;
    //--------
    List<String> recyclerName, recyclerSets, recyclerReps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_routine);
        recyclerView = findViewById(R.id.recyclerViewEditWorkout);

        //-----Spinner init-----------------
        initSpinner();
        recyclerName = new ArrayList<>();
        recyclerSets = new ArrayList<>();
        recyclerReps = new ArrayList<>();

        //----------ADDING EDIT TEXT----------
        editWorkoutName = (EditText) findViewById(R.id.editWorkoutName);

        //---------ADDING BUTTONS--------------
        return_button = findViewById(R.id.return_button);
        return_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMain();
            }
        });

        add_spinner_item = findViewById(R.id.add_spinner_item);
        add_spinner_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //-----------recycler view part-------------
                insertItems();
            }
        });
        //-------------------SAVE BUTTON LISTENER------------------
        save_button = findViewById(R.id.save_button);
        save_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                //-------------After the button is clicked---------
                AlertDialog.Builder builder = new AlertDialog.Builder(EditWorkout.this);
                builder.setCancelable(true);
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure about these changes?");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DatabaseWorkout myDB = new DatabaseWorkout(EditWorkout.this);
                                 for (int index = 0; index < recyclerName.size(); index++) {
                                    myDB.addWorkout(1, editWorkoutName.getText().toString(), (String) recyclerName.get(index),
                                            Integer.parseInt((String) recyclerSets.get(index)),
                                            Integer.parseInt((String) recyclerReps.get(index)));
                                 }
                                goToMain();
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();


                }
        });

        //--------------------Recycler view----------------------
        RecyclerView recyclerView = findViewById(R.id.recyclerViewEditWorkout);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        adapter = new WorkoutAdapter(this, recyclerName, recyclerSets, recyclerReps);
        recyclerView.setAdapter(adapter);

        //---------------READING ALL THE DATA----------------
/**
 *         myDB = new DatabaseWorkout(EditWorkout.this);
 *         workout_id = new ArrayList<>();
 *         workout_number = new ArrayList<>();
 *         workout_name = new ArrayList<>();
 *         workout_reps = new ArrayList<>();
 *         workout_sets = new ArrayList<>();
 *         workout_exercise = new ArrayList<>();
 *
 *         storeDataInArrays();
 *         customAdapter = new CustomAdapter(EditWorkout.this, workout_id, workout_number, workout_name, workout_exercise, workout_sets, workout_reps);
 *         recyclerView.setAdapter(customAdapter);
 *         recyclerView.setLayoutManager(new LinearLayoutManager(EditWorkout.this));*/

    }

    //------------INIT FUNCTION-----------------
    public void initSpinner(){
        setTitle = findViewById(R.id.title);
        setTitle.setText(EditTitles.RoutineSelected);
        spinner = findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.planets_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner_sets = findViewById(R.id.spinner_sets);
        ArrayAdapter<CharSequence> adapter_sets = ArrayAdapter.createFromResource(this, R.array.sets_array, android.R.layout.simple_dropdown_item_1line);
        spinner_sets.setAdapter(adapter_sets);

        spinner_reps = findViewById(R.id.spinner_reps);
        ArrayAdapter<CharSequence> adapter_reps = ArrayAdapter.createFromResource(this, R.array.reps_array, android.R.layout.simple_dropdown_item_1line);
        spinner_reps.setAdapter(adapter_reps);

    }



    //----------------ON CLICK EVENTS------------------------------
    public void goToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //-----------DATA BASE METHODS TO READ--------------------------
/**
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *     void storeDataInArrays(){
 *         Cursor cursor = myDB.readAllData();
 *         if (cursor.getCount() == 0) {
 *             Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
 *         }else{
 *             while (cursor.moveToNext()){
 *                 workout_id.add(cursor.getString(0));
 *                 workout_number.add(cursor.getString(0));
 *                 workout_name.add(cursor.getString(0));
 *                 workout_exercise.add(cursor.getString(0));
 *                 workout_sets.add(cursor.getString(0));
 *                 workout_reps.add(cursor.getString(0));
 *             }
 *         }
 *     }*/

    public void onButtonClick(View view) {
        insertItems();
    }

    private void insertItems() {
        String item = (String) spinner.getSelectedItem();
        int insertIndex = 0;
        recyclerName.add(insertIndex, item);


        item = (String) spinner_sets.getSelectedItem();
        recyclerSets.add(insertIndex, item);

        item = (String) spinner_reps.getSelectedItem();
        recyclerReps.add(insertIndex, item);

        adapter.notifyItemInserted(insertIndex);
    }


}