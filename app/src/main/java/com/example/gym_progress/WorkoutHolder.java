package com.example.gym_progress;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class WorkoutHolder extends RecyclerView.ViewHolder{
    public TextView nameTextView;
    public Button addButton;

    public WorkoutHolder(View itemView) {
        super(itemView);
        nameTextView = itemView.findViewById(R.id.main_recycler_workoutName);
    }

}