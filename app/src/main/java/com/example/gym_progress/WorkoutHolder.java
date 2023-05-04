package com.example.gym_progress;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class WorkoutHolder extends RecyclerView.ViewHolder{
    public TextView nameTextView;
    public Button addButton;

    public WorkoutHolder(View itemView, MainWorkoutAdapter.OnItemClickListener listener) {
        super(itemView);
        nameTextView = itemView.findViewById(R.id.main_recycler_workoutName);
        addButton = itemView.findViewById(R.id.add_button_workout);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                listener.onItemClick(position);
            }
        });
    }

    public String getNameAtIndex(){
        return (String) nameTextView.getText();
    }

}